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
		int idOggettoInserito; 
		
		String inserimentoOggetto = "INSERT INTO Oggetto (Email, Descrizione, Categoria, Condizioni) VALUES (?, ?, ?, ?) RETURNING idOggetto";
		
		try (PreparedStatement psInserimentoOggetto = connessioneDB.prepareStatement(inserimentoOggetto)){

			psInserimentoOggetto.setString(1, emailUtenteProprietario);
			psInserimentoOggetto.setString(2, descrizioneOggetto);
			psInserimentoOggetto.setString(3, categoriaOggetto);
			psInserimentoOggetto.setString(4, condizioniOggetto);
			

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
		}

		
		return idOggettoInserito;
	}
	

	@Override
	public Oggetto recuperaOggettoConId(int idOggetto) throws SQLException{
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, idOggetto);
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				rsOggetto.next();
				
				byte[][] immaginiOggetto = this.recuperaImmagini(idOggetto);
				Oggetto oggettoInAnnuncio = new Oggetto(
						rsOggetto.getInt("idOggetto"),
						CategoriaEnum.confrontaConStringa(rsOggetto.getString("Categoria")),
						CondizioneEnum.confrontaConStringa(rsOggetto.getString("Condizioni")),
						immaginiOggetto[0]
				);
				
				if(rsOggetto.getString("Descrizione") != null) {
					oggettoInAnnuncio.setDescrizione(rsOggetto.getString("Descrizione"));
				}
				
				for(int i = 1; i < immaginiOggetto.length; i++)
					if(immaginiOggetto[i] != null)
						oggettoInAnnuncio.aggiungiImmagine(i, immaginiOggetto[i]);
					
				return oggettoInAnnuncio;
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
							CondizioneEnum.confrontaConStringa(rs.getString("condizioni")), immaginiOggetto[0]);
					
					if(rs.getString("Descrizione") != null) {
						oggettoDaAggiungere.setDescrizione(rs.getString("Descrizione"));
						
						for(int i = 0; i < immaginiOggetto.length; i++)
							if(immaginiOggetto[i] != null)
								oggettoDaAggiungere.aggiungiImmagine(i, immaginiOggetto[i]);
					
						toReturn.add(oggettoDaAggiungere);
					}
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

	@Override
	public void deleteOggetto(int idOggetto) throws SQLException {
		try(PreparedStatement psEliminaOggetto = connessioneDB.prepareStatement("DELETE FROM Oggetto WHERE idOggetto = ?")){
						
			psEliminaOggetto.setInt(1, idOggetto);
			
			psEliminaOggetto.executeUpdate();
		}
	}

	@Override
	public void updateOggetto(Oggetto oggettoDaModificare) throws SQLException {
		
		
		try(PreparedStatement psAggiornaOggetto = connessioneDB.prepareStatement("UPDATE Oggetto SET"
				+ " Descrizione = ?, Categoria = ?, Condizioni = ? WHERE idOggetto = ?")){
			psAggiornaOggetto.setString(1, oggettoDaModificare.getDescrizione());
			psAggiornaOggetto.setString(2, oggettoDaModificare.getCategoria());
			psAggiornaOggetto.setString(3, oggettoDaModificare.getCondizioni());
			psAggiornaOggetto.setInt(4, oggettoDaModificare.getIdOggetto());
			
			psAggiornaOggetto.executeUpdate();

			try(PreparedStatement psEliminaFotoOggetto = connessioneDB.prepareStatement("DELETE FROM Immagine WHERE idOggetto = ?")){
				psEliminaFotoOggetto.setInt(1, oggettoDaModificare.getIdOggetto());
				
				psEliminaFotoOggetto.executeUpdate();
				
				for(int i = 0; i < 3; i++) {
					if(oggettoDaModificare.getImmagine(i) != null)
						try(PreparedStatement psInserisciFotoOggetto = connessioneDB.prepareStatement("INSERT INTO Immagine (File_immagine, idOggetto) VALUES (?, ?)")){
							psInserisciFotoOggetto.setBytes(1, oggettoDaModificare.getImmagine(i));
							psInserisciFotoOggetto.setInt(2, oggettoDaModificare.getIdOggetto());
							
							psInserisciFotoOggetto.executeUpdate();
						}
				}
			}
		}
	}
}