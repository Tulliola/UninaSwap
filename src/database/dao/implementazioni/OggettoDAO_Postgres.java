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
	
	@Override
	public Integer inserisciOggetto(Connection connessioneDB, Oggetto oggettoToAdd, String emailUtenteProprietario) throws SQLException {
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
			
			oggettoToAdd.setIdOggetto(idOggettoInserito);		
		}
		
		return idOggettoInserito;

	}
	

	@Override
	public Oggetto recuperaOggettoConId(Connection connessioneDB, int idOggetto) throws SQLException{
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, idOggetto);
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				rsOggetto.next();
				
				ImmagineDAO_Postgres immagineDAO = new ImmagineDAO_Postgres();
				byte[][] immaginiOggetto = immagineDAO.recuperaImmagini(connessioneDB, idOggetto);
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
	public ArrayList<Oggetto> recuperaOggettiOffertiConIdOfferta(Connection connessioneDB, int idOfferta) throws SQLException {
		ArrayList<Oggetto> toReturn = new ArrayList<Oggetto>();
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Oggetto_offerto NATURAL JOIN Offerta_scambio NATURAL JOIN Oggetto WHERE idOfferta = ? ORDER BY Momento_proposta DESC")){
			ps.setInt(1, idOfferta);
			
			try(ResultSet rs = ps.executeQuery()){
				ImmagineDAO_Postgres immagineDAO = new ImmagineDAO_Postgres();
				
				while(rs.next()) {
					byte[][] immaginiOggetto = immagineDAO.recuperaImmagini(connessioneDB, rs.getInt("idOggetto"));
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
	public void deleteOggetto(Connection connessioneDB, int idOggetto) throws SQLException {
		try(PreparedStatement psEliminaOggetto = connessioneDB.prepareStatement("DELETE FROM Oggetto WHERE idOggetto = ?")){
						
			psEliminaOggetto.setInt(1, idOggetto);
			
			psEliminaOggetto.executeUpdate();
		}
	}

	@Override
	public void updateOggetto(Connection connessioneDB, Oggetto oggettoDaModificare) throws SQLException {
		
		
		try(PreparedStatement psAggiornaOggetto = connessioneDB.prepareStatement("UPDATE Oggetto SET"
				+ " Descrizione = ?, Categoria = ?, Condizioni = ? WHERE idOggetto = ?")){
			psAggiornaOggetto.setString(1, oggettoDaModificare.getDescrizione());
			psAggiornaOggetto.setString(2, oggettoDaModificare.getCategoria());
			psAggiornaOggetto.setString(3, oggettoDaModificare.getCondizioni());
			psAggiornaOggetto.setInt(4, oggettoDaModificare.getIdOggetto());
			
			psAggiornaOggetto.executeUpdate();

			ImmagineDAO_Postgres immagineDAO = new ImmagineDAO_Postgres();
			
			immagineDAO.deleteImmaginiDiOggetto(connessioneDB, oggettoDaModificare.getIdOggetto());
			immagineDAO.inserisciImmagini(connessioneDB, oggettoDaModificare);
			
		}
	}
}