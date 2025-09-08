package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import database.dao.interfacce.OffertaDAO;
import database.dao.interfacce.OffertaRegaloDAO;
import dto.Annuncio;
import dto.Offerta;
import dto.OffertaRegalo;
import dto.ProfiloUtente;
import dto.SedeUniversita;
import dto.UfficioPostale;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaRegaloDAO_Postgres implements OffertaDAO, OffertaRegaloDAO{
	
	@Override
	public ArrayList<Offerta> recuperaOfferteDiUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<Offerta> offerteUtente = new ArrayList<Offerta>();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE Email = ? AND Prezzo_offerto = 0")){
		
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres();
					
					do {
						
						Offerta offertaToAdd = null;
						Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
						ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta"));
						StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
						Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(connessioneDB, rs.getInt("idAnnuncio"));

						offertaToAdd = new OffertaRegalo(utenteLoggato, momentoProposta, modConsegna, stato, annuncioRiferito);
						
						offertaToAdd.setMessaggioMotivazionale(rs.getString("Messaggio_motivazionale"));
						
						if(modConsegna.equals(ModConsegnaEnum.Ritiro_in_posta)) {
							UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres();
							UfficioPostale ufficioScelto = ufficioDAO.recuperaUfficioPostaleConId(connessioneDB, rs.getInt("idUfficio"));
							offertaToAdd.setUfficioRitiro(ufficioScelto);
							
						}
						else if(modConsegna.equals(ModConsegnaEnum.Spedizione))
							offertaToAdd.setIndirizzoSpedizione(rs.getString("Indirizzo_spedizione"));
						else {
							SedeUniversitaDAO_Postgres sedeDAO = new SedeUniversitaDAO_Postgres();
							offertaToAdd.setGiornoIncontro(GiornoEnum.confrontaConStringa(rs.getString("Giorno_incontro")));
							offertaToAdd.setOraInizioIncontro(rs.getString("Ora_inizio_incontro"));
							offertaToAdd.setOraFineIncontro(rs.getString("Ora_fine_incontro"));
							SedeUniversita sedeScelta = sedeDAO.recuperaSedeDaNome(connessioneDB, rs.getString("Sede_incontro"));
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
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Connection connessioneDB, Annuncio annuncio) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList<Offerta>();
		
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres();
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE idAnnuncio = ? AND prezzo_offerto = 0")){
			ps.setInt(1, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ProfiloUtente offerente = utenteDAO.recuperaUtenteNonLoggatoConEmail(connessioneDB, rs.getString("Email"));
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncio;
					
					Offerta offertaToAdd;
					offertaToAdd = new OffertaRegalo(offerente, momentoProposta, modConsegnaScelta, stato,
							annuncioRiferito);
					
					if(modConsegnaScelta.equals(ModConsegnaEnum.Ritiro_in_posta)) {
						UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres();
						UfficioPostale ufficioScelto = ufficioDAO.recuperaUfficioPostaleConId(connessioneDB, rs.getInt("idUfficio"));
						offertaToAdd.setUfficioRitiro(ufficioScelto);
						
					}
					else if(modConsegnaScelta.equals(ModConsegnaEnum.Spedizione))
						offertaToAdd.setIndirizzoSpedizione(rs.getString("Indirizzo_spedizione"));
					else {
						SedeUniversitaDAO_Postgres sedeDAO = new SedeUniversitaDAO_Postgres();
						offertaToAdd.setGiornoIncontro(GiornoEnum.confrontaConStringa(rs.getString("Giorno_incontro")));
						offertaToAdd.setOraInizioIncontro(rs.getString("Ora_inizio_incontro"));
						offertaToAdd.setOraFineIncontro(rs.getString("Ora_fine_incontro"));
						SedeUniversita sedeScelta = sedeDAO.recuperaSedeDaNome(connessioneDB, rs.getString("Sede_incontro"));
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
	public void inserisciOfferta(Connection connessioneDB, Offerta offertaDaInserire) throws SQLException {
		String inserisciOffertaRegalo = "INSERT INTO Offerta_acquisto(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
				+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, "
				+ "Prezzo_offerto, Messaggio_motivazionale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING Momento_proposta";
		
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
			
			if(offertaDaInserire.getMessaggioMotivazionale() == null || offertaDaInserire.getMessaggioMotivazionale().isBlank())
				psInserisciOffertaRegalo.setNull(12, Types.VARCHAR);
			else
				psInserisciOffertaRegalo.setString(12, messaggioMotivazionale);
			
			try(ResultSet rsInserimentoOfferta = psInserisciOffertaRegalo.executeQuery()){
				rsInserimentoOfferta.next();
				
				offertaDaInserire.setMomentoProposta(rsInserimentoOfferta.getTimestamp("Momento_proposta"));
			}
		}
	}

	@Override
	public void updateStatoOfferta(Connection connessioneDB, Offerta offerta) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			
			ps.setString(1, offerta.getStato());
			ps.setString(2, offerta.getUtenteProprietario().getEmail());
			ps.setTimestamp(3, offerta.getMomentoProposta());
			
			ps.executeUpdate();
		}
	}
	
	@Override
	public void updateOfferta(Connection connessioneDB, Offerta offertaDaModificare) throws SQLException {
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
			
			if(messaggioMotivazionale != null && !messaggioMotivazionale.isBlank()) {
				ps.setString(8, messaggioMotivazionale);
			}
			else
				ps.setNull(8, Types.VARCHAR);
			
			
			
			if(modalitaConsegna.equals("Ritiro in posta")) {
//				UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres();
//				ps.setInt(9, ufficioDAO.recuperaIdUfficio(connessioneDB, offertaDaModificare.getUfficioRitiro().getNome()));
//				ps.setNull(2, Types.VARCHAR);
				
				ps.setInt(9, offertaDaModificare.getUfficioRitiro().getIdUfficio());
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