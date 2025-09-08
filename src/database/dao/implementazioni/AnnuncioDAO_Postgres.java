package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import database.dao.interfacce.AnnuncioDAO;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.Oggetto;
import dto.ProfiloUtente;
import dto.SedeUniversita;
import utilities.GiornoEnum;
import utilities.StatoAnnuncioEnum;

public class AnnuncioDAO_Postgres implements AnnuncioDAO{
	
	@Override
	public ArrayList<Annuncio> recuperaAnnunciDiUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException{
		ArrayList<Annuncio> toReturn = new ArrayList<>();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email = ? ORDER BY Momento_pubblicazione ASC")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					toReturn.add(annuncioCorrenteRecuperato(connessioneDB, rs, utenteLoggato));
				}
				return toReturn;
			}
		}
	}

	@Override
	public ArrayList<Annuncio> recuperaAnnunciInBacheca(Connection connessioneDB, String emailUtenteLoggato) throws SQLException{
		ArrayList<Annuncio> toReturn = new ArrayList<>();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO NATURAL JOIN Profilo_utente WHERE Email <> ? AND Stato = 'Disponibile' ORDER BY Momento_pubblicazione ASC")){
			ps.setString(1, emailUtenteLoggato);
			
			try(ResultSet rs = ps.executeQuery()){

				while(rs.next()) {
					ProfiloUtente utenteCorrente = new ProfiloUtente(rs.getString("username"), rs.getString("Email"),
							rs.getDouble("Saldo"), rs.getBytes("immagine_profilo"), rs.getString("Residenza"),
							rs.getString("PW"), rs.getBoolean("sospeso"));
					toReturn.add(annuncioCorrenteRecuperato(connessioneDB, rs, utenteCorrente));
				}
				return toReturn;
			}
		}
	}
	

	@Override
	public void inserisciAnnuncio(Connection connessioneDB, Annuncio annuncioDaInserire) throws SQLException {
		
		String inserimentoAnnuncio = "INSERT INTO Annuncio (Email, idOggetto, Spedizione, Incontro, Ritiro_in_posta, Nome, Tipo_annuncio,"
				+ "Nota_scambio, Prezzo_iniziale, Data_scadenza)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idAnnuncio";
		
		try(PreparedStatement psInserimentoAnnuncio = connessioneDB.prepareStatement(inserimentoAnnuncio)){
			psInserimentoAnnuncio.setString(1, annuncioDaInserire.getUtenteProprietario().getEmail());
			psInserimentoAnnuncio.setInt(2, annuncioDaInserire.getOggettoInAnnuncio().getIdOggetto());
			psInserimentoAnnuncio.setBoolean(3, annuncioDaInserire.isSpedizione());
			psInserimentoAnnuncio.setBoolean(4, annuncioDaInserire.isIncontro());
			psInserimentoAnnuncio.setBoolean(5, annuncioDaInserire.isRitiroInPosta());
			psInserimentoAnnuncio.setString(6, annuncioDaInserire.getNome());
							
			if(annuncioDaInserire.getNotaScambio() != null) {
				psInserimentoAnnuncio.setString(7, "Scambio");
				psInserimentoAnnuncio.setString(8, annuncioDaInserire.getNotaScambio());
				psInserimentoAnnuncio.setNull(9, Types.REAL);
			}
			else if(annuncioDaInserire.getPrezzoIniziale() == null) {
				psInserimentoAnnuncio.setString(7, "Regalo");
				psInserimentoAnnuncio.setNull(8, Types.VARCHAR);
				psInserimentoAnnuncio.setNull(9, Types.REAL);
			}
			else {
				psInserimentoAnnuncio.setString(7, "Vendita");
				psInserimentoAnnuncio.setNull(8, Types.VARCHAR);
				psInserimentoAnnuncio.setDouble(9, annuncioDaInserire.getPrezzoIniziale());
			}
			
			if(annuncioDaInserire.getDataScadenza() != null)
				psInserimentoAnnuncio.setDate(10,  annuncioDaInserire.getDataScadenza());
			else
				psInserimentoAnnuncio.setNull(10, Types.DATE);
			
			int idAnnuncioInserito;
							
			try(ResultSet rsInserimentoAnnuncio = psInserimentoAnnuncio.executeQuery()){
				rsInserimentoAnnuncio.next();
				idAnnuncioInserito = rsInserimentoAnnuncio.getInt("idAnnuncio");
			}
			
			annuncioDaInserire.setIdAnnuncio(idAnnuncioInserito);
		}
	}	

	
	@Override
	public Annuncio recuperaAnnuncioConId(Connection connessioneDB, int idAnnuncio) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO NATURAL JOIN Profilo_utente WHERE idAnnuncio = ?")){
			ps.setInt(1, idAnnuncio);
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				ProfiloUtente utenteCorrente = new ProfiloUtente(rs.getString("username"), rs.getString("Email"),
						rs.getDouble("Saldo"), rs.getBytes("immagine_profilo"), rs.getString("Residenza"),
						rs.getString("PW"), rs.getBoolean("sospeso"));
				return annuncioCorrenteRecuperato(connessioneDB, rs, utenteCorrente);
			}
		}
	}
	

	@Override
	public void aggiornaStatoAnnuncio(Connection connessioneDB, Annuncio annuncio) throws SQLException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Annuncio SET Stato = ? WHERE idAnnuncio = ?")){
			ps.setString(1, annuncio.getStato());
			ps.setInt(2, annuncio.getIdAnnuncio());
			
			ps.executeUpdate();
		}
	}
	
/******************** METODI AUSILIARI NON EREDITATI DALL'INTERFACCIA *****************/
	
	private Annuncio annuncioCorrenteRecuperato(Connection connessioneDB, ResultSet rs, ProfiloUtente utente) throws SQLException{
		Annuncio annuncioRecuperato;
		
		OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres();
		
		int idAnnuncioRecuperato = rs.getInt("idAnnuncio");
		boolean spedizione = rs.getBoolean("Spedizione");
		boolean ritiroInPosta = rs.getBoolean("Ritiro_in_posta");
		boolean incontro = rs.getBoolean("Incontro");
		StatoAnnuncioEnum stato = StatoAnnuncioEnum.confrontaConDB(rs.getString("Stato"));
		Timestamp momentoPubblicazione = rs.getTimestamp("Momento_pubblicazione");
		String nome = rs.getString("Nome");
		Oggetto oggettoInAnnuncio = oggettoDAO.recuperaOggettoConId(connessioneDB, rs.getInt("idOggetto"));

		if(rs.getString("Tipo_annuncio").equals("Vendita")) {
			annuncioRecuperato = new AnnuncioVendita(idAnnuncioRecuperato, spedizione, ritiroInPosta, incontro, 
				stato, momentoPubblicazione, nome, utente, 
				oggettoInAnnuncio, rs.getDouble("Prezzo_iniziale"));
			
		}
		else if(rs.getString("Tipo_annuncio").equals("Scambio")) {
			annuncioRecuperato = new AnnuncioScambio(
				idAnnuncioRecuperato, spedizione, ritiroInPosta, incontro, 
				stato, momentoPubblicazione, nome, utente, 
				oggettoInAnnuncio, rs.getString("Nota_scambio")	
			);
			
		}
		else {
			annuncioRecuperato = new AnnuncioRegalo(idAnnuncioRecuperato, spedizione, 
				ritiroInPosta, incontro, stato, momentoPubblicazione, nome, 
				utente, oggettoInAnnuncio
			);	
		}
		
		if(rs.getBoolean("Incontro")) {
			String recuperaIncontri = "SELECT * FROM Incontro WHERE idAnnuncio = ?";
			
			try(PreparedStatement psRecuperaIncontri = connessioneDB.prepareStatement(recuperaIncontri)){
				psRecuperaIncontri.setInt(1, idAnnuncioRecuperato);
				
				try(ResultSet rsRecuperaIncontri = psRecuperaIncontri.executeQuery()){
					while(rsRecuperaIncontri.next()) {
						SedeUniversita sedeDiIncontro;
						
						SedeUniversitaDAO_Postgres sedeUniversitaDAO = new SedeUniversitaDAO_Postgres();
						sedeDiIncontro = sedeUniversitaDAO.recuperaSedeDaId(connessioneDB, rsRecuperaIncontri.getInt("idSede"));
						
						annuncioRecuperato.aggiungiPropostaIncontro(
								sedeDiIncontro, 
								rsRecuperaIncontri.getString("Ora_inizio_incontro"), 
								rsRecuperaIncontri.getString("Ora_fine_incontro"), 
								GiornoEnum.confrontaConStringa(rsRecuperaIncontri.getString("Giorno_incontro"))
						);
					}
				}
			}
		}
		
		if(rs.getDate("Data_scadenza") != null)
			annuncioRecuperato.setDataScadenza(rs.getDate("Data_scadenza"));
		
		return annuncioRecuperato;
	}

}

