package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.dao.interfacce.OggettoDAO;
import dto.Oggetto;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;

public class OggettoDAO_Postgres implements OggettoDAO {
	private Connection connessioneDB;
	
	public OggettoDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}

	@Override
	public Integer inserisciOggetto(Oggetto oggettoToAdd, String emailUtenteProprietario) throws SQLException {
		String descrizioneOggetto = oggettoToAdd.getDescrizione();
		String categoriaOggetto = oggettoToAdd.getCategoria();
		String condizioniOggetto = oggettoToAdd.getCondizioni();
		

		String inserimentoOggetto = "INSERT INTO Oggetto (Email, Descrizione, Categoria, Condizioni) VALUES (?, ?, ?, ?) RETURNING idOggetto";
		
		try (PreparedStatement psInserimentoOggetto = connessioneDB.prepareStatement(inserimentoOggetto)){

			psInserimentoOggetto.setString(1, emailUtenteProprietario);
			psInserimentoOggetto.setString(2, descrizioneOggetto);
			psInserimentoOggetto.setString(3, categoriaOggetto);
			psInserimentoOggetto.setString(4, condizioniOggetto);

			int idOggettoInserito; 
			
			try(ResultSet rsInserimentoOggetto = psInserimentoOggetto.executeQuery()){
				rsInserimentoOggetto.next();
				idOggettoInserito = rsInserimentoOggetto.getInt("idOggetto");
			}
			
			for(int i = 0; i < 3; i++) {
				if(oggettoToAdd.getImmagine(i) != null) {
					String inserimentoImmagini = "INSERT INTO Immagine (File_immagine, idOggetto) VALUES (?, ?)";
					
					try(PreparedStatement psInserimentoImmagini = connessioneDB.prepareStatement(inserimentoImmagini)){
						psInserimentoImmagini.setBytes(1, oggettoToAdd.getImmagine(i));
						psInserimentoImmagini.setInt(2, idOggettoInserito);
						
						psInserimentoImmagini.executeUpdate();
					}
				}
			}
						
			return idOggettoInserito;
		}
	}
	
	@Override
	public byte[] recuperaPrimaImmagineDiOggetto(int idOggetto) throws SQLException{
		String recuperaImmagine = "SELECT File_immagine FROM Immagine WHERE idOggetto = ?";
		
		try(PreparedStatement psRecuperaImmagine = connessioneDB.prepareStatement(recuperaImmagine)){
			psRecuperaImmagine.setInt(1, idOggetto);
			
			try(ResultSet rsRecuperaImmagine = psRecuperaImmagine.executeQuery()){
				if(rsRecuperaImmagine.next())
					return rsRecuperaImmagine.getBytes("File_immagine");
				else
					return null;
			}
		}
	}
	
	@Override
	public Oggetto recuperaOggettoConId(int idOggetto) throws SQLException{
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, idOggetto);
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				rsOggetto.next();
				Oggetto oggettoInAnnuncio = new Oggetto(
						rsOggetto.getInt("idOggetto"),
						CategoriaEnum.confrontaConStringa(rsOggetto.getString("Categoria")),
						CondizioneEnum.confrontaConStringa(rsOggetto.getString("Condizioni")),
						this.recuperaPrimaImmagineDiOggetto(rsOggetto.getInt("idOggetto")),
						isOggettoDisponibile(idOggetto)
				);
				
				if(rsOggetto.getString("Descrizione") != null) {
					oggettoInAnnuncio.setDescrizione(rsOggetto.getString("Descrizione"));
				}
				
				return oggettoInAnnuncio;
			}
		}
	}
	
	@Override
	public boolean isOggettoDisponibile(int idOggetto) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("(SELECT idOggetto FROM OGGETTO NATURAL JOIN ANNUNCIO WHERE idOggetto = ? AND "
				+ "(NOT(Stato = 'Venduto' OR Stato = 'Regalato' OR Stato = 'Scambiato' OR Stato = 'Indisponibile')))"
				+ "UNION (SELECT idOggetto FROM OGGETTO NATURAL JOIN OFFERTA_SCAMBIO WHERE idOggetto = ? AND"
				+ "(NOT(Stato = 'Accettata')))")){
			ps.setInt(1, idOggetto);
			ps.setInt(2, idOggetto);
			
			try(ResultSet rs = ps.executeQuery()){
				return rs.next();
			}
		}
	}
	
	@Override
	public ArrayList<Oggetto> recuperaOggettiOffertiConIdOfferta(int idOfferta) throws SQLException {
		ArrayList<Oggetto> toReturn = new ArrayList();
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Oggetto_offerto NATURAL JOIN Offerta_scambio NATURAL JOIN Oggetto WHERE idOfferta = ? ORDER BY Momento_proposta DESC")){
			ps.setInt(1, idOfferta);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					byte[][] immaginiOggetto = recuperaImmagini(rs.getInt("idOggetto"));
					Oggetto oggettoDaAggiungere = new Oggetto(rs.getInt("idOggetto"), CategoriaEnum.confrontaConStringa(rs.getString("categoria")),
							CondizioneEnum.confrontaConStringa(rs.getString("condizioni")), immaginiOggetto[0], 
							isOggettoDisponibile(rs.getInt("idOggetto")));
					
					toReturn.add(oggettoDaAggiungere);
					
					if(immaginiOggetto[1] != null)
						oggettoDaAggiungere.aggiungiImmagine(1, immaginiOggetto[1]);

					if(immaginiOggetto[2] != null)
						oggettoDaAggiungere.aggiungiImmagine(2, immaginiOggetto[2]);
				}
			}
		}
		return toReturn;
	}
	
	@Override
	public byte[][] recuperaImmagini(int idOggetto) throws SQLException {
		byte[][] toReturn = new byte[3][];
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT file_immagine FROM IMMAGINE WHERE idOggetto = ?")){
			ps.setInt(1, idOggetto);
			
			try(ResultSet rs = ps.executeQuery()){
				int i = 0;
				
				while(rs.next()) {
					toReturn[i] = rs.getBytes("file_immagine");
					i++;
				}
				
				return toReturn;
			}
		}
	}

}