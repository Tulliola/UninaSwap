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
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE Email = ? AND Prezzo_offerto = 0")){
		
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
					
					do {
						
						Offerta offertaToAdd = null;
						Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
						ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta"));
						StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
						Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(rs.getInt("idAnnuncio"));

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
							SedeUniversita sedeScelta = sedeDAO.recuperaSedeDaNome(rs.getString("Sede_incontro"));
							offertaToAdd.setSedeDIncontroScelta(sedeScelta);
						}
						
						offertaToAdd.setNota(rs.getString("Nota"));
						
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
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE idAnnuncio = ? AND prezzo_offerto = 0")){
			ps.setInt(1, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ProfiloUtente offerente = utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("Email"));
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncio;
					
					Offerta offertaToAdd;
					offertaToAdd = new OffertaRegalo(offerente, momentoProposta, modConsegnaScelta, stato,
							annuncioRiferito);
					
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
						SedeUniversita sedeScelta = sedeDAO.recuperaSedeDaNome(rs.getString("Sede_incontro"));
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
			psInserisciOffertaRegalo.setString(12, messaggioMotivazionale);
			
			psInserisciOffertaRegalo.executeUpdate();
		}
	}

	@Override
	public void updateStatoOfferta(Offerta offerta) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			
			ps.setString(1, offerta.getStato());
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

	@Override
	public void updateOfferta(Offerta offertaDaModificare) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET"
				+ " Nota = ?, Indirizzo_spedizione = ?, Ora_inizio_incontro = ?, Ora_fine_incontro = ?, Giorno_incontro = ?, "
				+ " Sede_incontro = ?, Modalita_consegna_scelta = ?, Messaggio_motivazionale = ?, idUfficio = ?"
				+ " WHERE email = ? AND Momento_proposta = ?")){
			String modalitaConsegna = offertaDaModificare.getModalitaConsegnaScelta();
			String nota = offertaDaModificare.getNota();
			String indirizzoSpedizione = offertaDaModificare.getIndirizzoSpedizione();
			String oraInizioIncontro = null;
			String oraFineIncontro = null;
			String giornoIncontro = null;
			SedeUniversita sede = null;
			if(modalitaConsegna.equals("Incontro")) {
				oraInizioIncontro = offertaDaModificare.getOraInizioIncontro();
				oraFineIncontro = offertaDaModificare.getOraFineIncontro();
				giornoIncontro = offertaDaModificare.getGiornoIncontro();
				sede = offertaDaModificare.getSedeDIncontroScelta();
			}
			String messaggioMotivazionale = offertaDaModificare.getMessaggioMotivazionale();
			
			if(offertaDaModificare.getNota() != null) {
				ps.setString(1, nota);
			}
			else
				ps.setNull(1, Types.VARCHAR);
			
			if(indirizzoSpedizione != null) {
				ps.setString(2, indirizzoSpedizione);
			}
			else
				ps.setNull(2, Types.VARCHAR);
			
			if(oraInizioIncontro != null) {
				ps.setString(3, oraInizioIncontro);
			}
			else
				ps.setNull(3, Types.VARCHAR);
			
			if(oraFineIncontro != null) {
				ps.setString(4, oraFineIncontro);
			}
			else
				ps.setNull(4, Types.VARCHAR);
			
			if(giornoIncontro != null) {
				ps.setString(5, giornoIncontro);
			}
			else
				ps.setNull(5, Types.VARCHAR);
			
			if(sede != null) {
				ps.setString(6, sede.getNome());
			}
			else
				ps.setNull(6, Types.VARCHAR);
			
			ps.setString(7, modalitaConsegna);
			
			if(messaggioMotivazionale != null) {
				ps.setString(8, messaggioMotivazionale);
			}
			else
				ps.setNull(8, Types.VARCHAR);
			
			
			
			if(modalitaConsegna.equals("Ritiro in posta")) {
				UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
				ps.setInt(9, ufficioDAO.recuperaIdUfficio(offertaDaModificare.getUfficioRitiro().getNome()));
				ps.setNull(2, Types.VARCHAR);
			}
			else
				ps.setNull(9, Types.INTEGER);
			
			ps.setString(10, offertaDaModificare.getUtenteProprietario().getEmail());
			ps.setTimestamp(11, offertaDaModificare.getMomentoProposta());
			
			ps.executeUpdate();
			
		}
	}
		
}