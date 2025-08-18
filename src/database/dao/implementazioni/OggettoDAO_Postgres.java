package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		
		connessioneDB.setAutoCommit(false);

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
			
			connessioneDB.commit();
			
			return idOggettoInserito;
		}
		catch(SQLException exc) {
			System.out.println(exc.getMessage());
			System.out.println(exc.getSQLState());
			System.out.println(exc.getErrorCode());
		}
		finally {
			connessioneDB.setAutoCommit(true);
		}
		return null;
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
	

	// Metodi privati
	private boolean isOggettoDisponibile(int idOggetto) throws SQLException {
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
}
