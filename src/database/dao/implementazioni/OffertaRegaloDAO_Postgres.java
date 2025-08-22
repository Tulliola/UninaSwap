package database.dao.implementazioni;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.dao.interfacce.OffertaDAO;
import database.dao.interfacce.OffertaRegaloDAO;
import dto.Annuncio;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.OffertaScambio;
import dto.Oggetto;
import dto.ProfiloUtente;
import dto.SedeUniversita;
import dto.UfficioPostale;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaRegaloDAO_Postgres implements OffertaDAO, OffertaRegaloDAO{

	private Connection connessioneDB;
	
	public OffertaRegaloDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public ArrayList<Offerta> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<Offerta> offerteUtente = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("((SELECT "
				+ " email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ " ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ " modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_ACQUISTO "
				+ " NATURAL JOIN ANNUNCIO WHERE email = ? AND Tipo_annuncio = 'Regalo') UNION (SELECT "
				+ " email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ " ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ " modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_SCAMBIO "
				+ " NATURAL JOIN ANNUNCIO WHERE email = ? AND Tipo_annuncio = 'Regalo') ORDER BY momento_proposta DESC)")){
		
			ps.setString(1, utenteLoggato.getEmail());
			ps.setString(2, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
					
					do {
						
						Offerta offertaToAdd = null;
						Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
						ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta"));
						StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
						Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(rs.getInt("idAnnuncio"));
						
						Double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("email"), rs.getTimestamp("Momento_proposta"));
						
						Integer idOfferta = recuperaIdOfferta(momentoProposta, utenteLoggato.getEmail());
									
						if(prezzoOfferto != null && prezzoOfferto > 0) {
							offertaToAdd = new OffertaAcquisto(utenteLoggato, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto);
						}							
						else if(idOfferta != null) {
							OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
							ArrayList<Oggetto> oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(idOfferta);
							
							offertaToAdd = new OffertaScambio(utenteLoggato, idOfferta, momentoProposta, modConsegna, stato, annuncioRiferito, oggettiOfferti);
						}
						else 
							offertaToAdd = new OffertaRegalo(utenteLoggato, momentoProposta, modConsegna, stato, annuncioRiferito);
						
						if(rs.getString("Messaggio_motivazionale") != null) {
							offertaToAdd.setMessaggioMotivazionale(rs.getString("Messaggio_motivazionale"));
						}
						
						if(modConsegna.equals(ModConsegnaEnum.Ritiro_in_posta)) {
							UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
							UfficioPostale ufficioScelto = ufficioDAO.recuperaUfficioPostaleConId(rs.getInt("idUfficio"));
							offertaToAdd.setUfficioRitiro(ufficioScelto);
							
						}
						else if(modConsegna.equals(ModConsegnaEnum.Spedizione))
							offertaToAdd.setIndirizzoSpedizione(rs.getString("Indirizzo_spedizione"));
						else {
							SedeUniversitaDAO_Postgres sedeDAO = new SedeUniversitaDAO_Postgres(connessioneDB);
							offertaToAdd.setGiornoIncontro(GiornoEnum.confrontaConStringa(rs.getString("Giorno_incontro")));
							offertaToAdd.setOraInizioIncontro(rs.getString("Ora_inizio_incontro"));
							offertaToAdd.setOraFineIncontro(rs.getString("Ora_fine_incontro"));
							SedeUniversita sedeScelta = sedeDAO.recuperaSedeNome(rs.getString("Sede_incontro"));
							offertaToAdd.setSedeDIncontroScelta(sedeScelta);
						}
						
						offertaToAdd.setNota(rs.getString("Nota"));
						offertaToAdd.setMessaggioMotivazionale(rs.getString("Messaggio_motivazionale"));
						
						offerteUtente.add(offertaToAdd);
					} while(rs.next());
				}
				
				return offerteUtente;
			}
		}
	}


	@Override
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList<Offerta>();
		
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("((SELECT "
				+ " email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ " ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ " modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_ACQUISTO "
				+ " NATURAL JOIN ANNUNCIO WHERE idAnnuncio = ? AND Tipo_annuncio = 'Regalo') UNION (SELECT "
				+ " email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ " ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ " modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_SCAMBIO "
				+ " NATURAL JOIN ANNUNCIO WHERE idAnnuncio = ? AND Tipo_annuncio = 'Regalo') ORDER BY momento_proposta DESC)")){
			
			ps.setInt(1, annuncio.getIdAnnuncio());
			ps.setInt(2, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					Offerta offertaToAdd;
					
					ProfiloUtente utenteOfferente = utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("Email"));
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncio;
					
					offertaToAdd = new OffertaRegalo(utenteOfferente, momentoProposta, modConsegnaScelta, stato,
							annuncioRiferito);
					
					if(rs.getString("Messaggio_motivazionale") != null) {
						offertaToAdd.setMessaggioMotivazionale(rs.getString("Messaggio_motivazionale"));
					}
					
					if(modConsegnaScelta.equals(ModConsegnaEnum.Ritiro_in_posta)) {
						UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
						UfficioPostale ufficioScelto = ufficioDAO.recuperaUfficioPostaleConId(rs.getInt("idUfficio"));
						offertaToAdd.setUfficioRitiro(ufficioScelto);
						
					}
					else if(modConsegnaScelta.equals(ModConsegnaEnum.Spedizione))
						offertaToAdd.setIndirizzoSpedizione(rs.getString("Indirizzo_spedizione"));
					else {
						SedeUniversitaDAO_Postgres sedeDAO = new SedeUniversitaDAO_Postgres(connessioneDB);
						offertaToAdd.setGiornoIncontro(GiornoEnum.confrontaConStringa(rs.getString("Giorno_incontro")));
						offertaToAdd.setOraInizioIncontro(rs.getString("Ora_inizio_incontro"));
						offertaToAdd.setOraFineIncontro(rs.getString("Ora_fine_incontro"));
						SedeUniversita sedeScelta = sedeDAO.recuperaSedeNome(rs.getString("Sede_incontro"));
						offertaToAdd.setSedeDIncontroScelta(sedeScelta);
					}
					
					offertaToAdd.setNota(rs.getString("Nota"));
					offertaToAdd.setMessaggioMotivazionale(rs.getString("Messaggio_motivazionale"));
					
					toReturn.add(offertaToAdd);
				}
				return toReturn;				
			}
		}
	}

	@Override
	public void inserisciOfferta(Offerta offertaDaInserire) throws SQLException {
		String inserisciOffertaRegalo = "INSERT INTO Offerta_acquisto(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
				+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, "
				+ "Prezzo_offerto, Messaggio_motivazionale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		String emailOfferente = offertaDaInserire.getUtenteProprietario().getEmail();
		int idAnnuncioRiferito = offertaDaInserire.getAnnuncioRiferito().getIdAnnuncio();
		String modalitaConsegnaScelta = offertaDaInserire.getModalitaConsegnaScelta();
		String nota = offertaDaInserire.getNota();
		String messaggioMotivazionale = offertaDaInserire.getMessaggioMotivazionale();
		
		try(PreparedStatement psInserisciOffertaRegalo = connessioneDB.prepareStatement(inserisciOffertaRegalo)){
			psInserisciOffertaRegalo.setString(1, emailOfferente);
			psInserisciOffertaRegalo.setInt(2, idAnnuncioRiferito);
			
			if(modalitaConsegnaScelta.equals("Ritiro in posta"))
				psInserisciOffertaRegalo.setInt(3, offertaDaInserire.getUfficioRitiro().getIdUfficio());
			else
				psInserisciOffertaRegalo.setNull(3, Types.INTEGER);
			
			psInserisciOffertaRegalo.setString(4, nota);
			
			if(modalitaConsegnaScelta.equals("Spedizione"))
				psInserisciOffertaRegalo.setString(5, offertaDaInserire.getIndirizzoSpedizione());
			else
				psInserisciOffertaRegalo.setNull(5, Types.VARCHAR);
			
			if(modalitaConsegnaScelta.equals("Incontro")) {
				psInserisciOffertaRegalo.setString(6, offertaDaInserire.getOraInizioIncontro());
				psInserisciOffertaRegalo.setString(7, offertaDaInserire.getOraFineIncontro());
				psInserisciOffertaRegalo.setString(8, offertaDaInserire.getGiornoIncontro());
				psInserisciOffertaRegalo.setString(9, offertaDaInserire.getSedeDIncontroScelta().toString());
			}
			else {
				psInserisciOffertaRegalo.setNull(6, Types.VARCHAR);
				psInserisciOffertaRegalo.setNull(7, Types.VARCHAR);
				psInserisciOffertaRegalo.setNull(8, Types.VARCHAR);
				psInserisciOffertaRegalo.setNull(9, Types.VARCHAR);
			}
				
		
			psInserisciOffertaRegalo.setString(10, modalitaConsegnaScelta);;
			psInserisciOffertaRegalo.setDouble(11, 0);
			psInserisciOffertaRegalo.setString(12, offertaDaInserire.getMessaggioMotivazionale());
			
			psInserisciOffertaRegalo.executeUpdate();
		}
	}

	@Override
	public void updateStatoOfferta(Offerta offerta, StatoOffertaEnum stato) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			ps.setString(1, stato.toString());
			ps.setString(2, offerta.getUtenteProprietario().getEmail());
			ps.setTimestamp(3, offerta.getMomentoProposta());
			
			ps.executeUpdate();
		}
	}
	
	
	private Double recuperaPrezzoOfferta(String email, Timestamp momentoProposta) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT prezzo_offerto from OFFERTA_ACQUISTO WHERE Email = ? AND Momento_proposta = ?")){
			ps.setString(1, email);
			ps.setTimestamp(2, momentoProposta);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next())
					return rs.getDouble("prezzo_offerto");

				else
					return null;
			}
		}
	}
	
	private Integer recuperaIdOfferta(Timestamp momentoProposta, String email) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT idOfferta FROM OFFERTA_SCAMBIO WHERE "
				+ "momento_proposta = ? AND email = ?")){
			ps.setTimestamp(1, momentoProposta);
			ps.setString(2, email);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next())
					return rs.getInt("idOfferta");
			}
		}
		
		return null;
	}
}