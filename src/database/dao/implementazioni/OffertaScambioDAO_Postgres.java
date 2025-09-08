package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import database.dao.interfacce.OffertaDAO;
import database.dao.interfacce.OffertaScambioDAO;
import dto.Annuncio;
import dto.Offerta;
import dto.OffertaScambio;
import dto.Oggetto;
import dto.ProfiloUtente;
import dto.SedeUniversita;
import dto.UfficioPostale;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaScambioDAO_Postgres implements OffertaDAO, OffertaScambioDAO{
	
	@Override
	public ArrayList<Offerta> recuperaOfferteDiUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList<Offerta>();
		OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres();
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_scambio WHERE Email = ?")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					OffertaScambio offertaToAdd;
					
					ProfiloUtente offerente = utenteLoggato;
					int idOfferta = rs.getInt("idOfferta");
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(connessioneDB, rs.getInt("idAnnuncio"));
					ArrayList<Oggetto> oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(connessioneDB, rs.getInt("idOfferta"));
					
					offertaToAdd = new OffertaScambio(offerente, idOfferta, momentoProposta, modConsegnaScelta,
							stato, annuncioRiferito, oggettiOfferti);
					

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
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Connection connessioneDB, Annuncio annuncio) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList<Offerta>();
		
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres();
		OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_scambio WHERE idAnnuncio = ?")){
			ps.setInt(1, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					OffertaScambio offertaToAdd;
					
					ProfiloUtente offerente = utenteDAO.recuperaUtenteNonLoggatoConEmail(connessioneDB, rs.getString("Email"));
					int idOfferta = rs.getInt("idOfferta");
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncio;
					ArrayList<Oggetto> oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(connessioneDB, rs.getInt("idOfferta"));
					
					offertaToAdd = new OffertaScambio(offerente, idOfferta, momentoProposta, modConsegnaScelta,
							stato, annuncioRiferito, oggettiOfferti);
					
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
		String inserisciOffertaScambio = "INSERT INTO Offerta_scambio(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
				+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, Messaggio_Motivazionale) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idOfferta, Momento_proposta";
		
		
		try(PreparedStatement psInserisciOffertaScambio = connessioneDB.prepareStatement(inserisciOffertaScambio)){
			
			String emailOfferente = offertaDaInserire.getUtenteProprietario().getEmail();
			int idAnnuncioRiferito = offertaDaInserire.getAnnuncioRiferito().getIdAnnuncio();
			String modalitaConsegnaScelta = offertaDaInserire.getModalitaConsegnaScelta();
			String nota = offertaDaInserire.getNota();
			
			psInserisciOffertaScambio.setString(1, emailOfferente);
			psInserisciOffertaScambio.setInt(2, idAnnuncioRiferito);
			
			if(modalitaConsegnaScelta.equals("Ritiro in posta"))
				psInserisciOffertaScambio.setInt(3, offertaDaInserire.getUfficioRitiro().getIdUfficio());
			else
				psInserisciOffertaScambio.setNull(3, Types.INTEGER);
			
			psInserisciOffertaScambio.setString(4, nota);
			
			if(modalitaConsegnaScelta.equals("Spedizione"))
				psInserisciOffertaScambio.setString(5, offertaDaInserire.getIndirizzoSpedizione());
			else
				psInserisciOffertaScambio.setNull(5, Types.VARCHAR);
			
			if(modalitaConsegnaScelta.equals("Incontro")) {
				psInserisciOffertaScambio.setString(6, offertaDaInserire.getOraInizioIncontro());
				psInserisciOffertaScambio.setString(7, offertaDaInserire.getOraFineIncontro());
				psInserisciOffertaScambio.setString(8, offertaDaInserire.getGiornoIncontro());
				psInserisciOffertaScambio.setString(9, offertaDaInserire.getSedeDIncontroScelta().toString());
			}
			else {
				psInserisciOffertaScambio.setNull(6, Types.VARCHAR);
				psInserisciOffertaScambio.setNull(7, Types.VARCHAR);
				psInserisciOffertaScambio.setNull(8, Types.VARCHAR);
				psInserisciOffertaScambio.setNull(9, Types.VARCHAR);
			}
				
			psInserisciOffertaScambio.setString(10, modalitaConsegnaScelta);
			
			if(offertaDaInserire.getMessaggioMotivazionale() == null || offertaDaInserire.getMessaggioMotivazionale().isBlank())
				psInserisciOffertaScambio.setNull(11, Types.VARCHAR);
			else
				psInserisciOffertaScambio.setString(11, offertaDaInserire.getMessaggioMotivazionale());
			
			int idOffertaInserita;
			
			try(ResultSet rsInserisciOffertaScambio = psInserisciOffertaScambio.executeQuery()){
				rsInserisciOffertaScambio.next();
				
				offertaDaInserire.setMomentoProposta(rsInserisciOffertaScambio.getTimestamp("Momento_proposta"));
				
				idOffertaInserita = rsInserisciOffertaScambio.getInt("idOfferta");
				
				((OffertaScambio) offertaDaInserire).setIdOfferta(idOffertaInserita);
			}
			
			for(Oggetto oggettoCorrente : offertaDaInserire.getOggettiOfferti()) {
				OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres();
				ImmagineDAO_Postgres immagineDAO = new ImmagineDAO_Postgres();
				
				Integer idOggettoInserito = oggettoDAO.inserisciOggetto(connessioneDB, oggettoCorrente, emailOfferente);
				immagineDAO.inserisciImmagini(connessioneDB, oggettoCorrente);
				
				String inserisciOggettoOfferto = "INSERT INTO Oggetto_offerto (idOggetto, idOfferta) VALUES (?, ?)";
				
				try(PreparedStatement psInserisciOggettoOfferto = connessioneDB.prepareStatement(inserisciOggettoOfferto)){
					psInserisciOggettoOfferto.setInt(1, idOggettoInserito);
					psInserisciOggettoOfferto.setInt(2, idOffertaInserita);
					
					psInserisciOggettoOfferto.executeUpdate();
				}
			}
			
		}
	}

	@Override
	public void updateStatoOfferta(Connection connessioneDB, Offerta offerta) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_scambio SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			ps.setString(1, offerta.getStato());
			ps.setString(2, offerta.getUtenteProprietario().getEmail());
			ps.setTimestamp(3, offerta.getMomentoProposta());
			
			ps.executeUpdate();
		}
	}

	
	@Override
	public void updateOfferta(Connection connessioneDB, Offerta offertaModificata, ArrayList<String> operazioniDaEseguire) throws SQLException {
		OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres();
		
		for(int i = 0; i < offertaModificata.getOggettiOfferti().size(); i++) {
			
			if(operazioniDaEseguire.get(i).equals("UPDATE"))
				oggettoDAO.updateOggetto(connessioneDB, offertaModificata.getOggettiOfferti().get(i));
			else if(operazioniDaEseguire.get(i).equals("INSERT")) {
				ImmagineDAO_Postgres immagineDAO = new ImmagineDAO_Postgres();
				
				Integer idOggetto = oggettoDAO.inserisciOggetto(connessioneDB, offertaModificata.getOggettiOfferti().get(i), offertaModificata.getUtenteProprietario().getEmail());
				immagineDAO.inserisciImmagini(connessioneDB, offertaModificata.getOggettiOfferti().get(i));
				
				try(PreparedStatement psInserisciOggettoOfferto = connessioneDB.prepareStatement("INSERT INTO Oggetto_offerto VALUES (?, ?)")){
					psInserisciOggettoOfferto.setInt(1, idOggetto);
					psInserisciOggettoOfferto.setInt(2, offertaModificata.getIdOfferta());
					psInserisciOggettoOfferto.executeUpdate();
				}
				
				offertaModificata.getOggettiOfferti().get(i).setIdOggetto(idOggetto);
			}
			else
				oggettoDAO.deleteOggetto(connessioneDB, offertaModificata.getOggettiOfferti().get(i).getIdOggetto());
		}
		
		for(int i = offertaModificata.getOggettiOfferti().size()-1; i >= 0; i--)
			if(operazioniDaEseguire.get(i).equals("DELETE"))
				offertaModificata.getOggettiOfferti().remove(i);
		
		String newNota = offertaModificata.getNota();
		String newModalitaConsegnaScelta = offertaModificata.getModalitaConsegnaScelta();
		String newMessaggioMotivazionale = offertaModificata.getMessaggioMotivazionale();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_scambio SET "
				+ " idUfficio = ?, Nota = ?, Indirizzo_spedizione = ?, Ora_inizio_incontro = ?, Ora_fine_incontro = ?,"
				+ " Giorno_incontro = ?, Sede_incontro = ?, Modalita_consegna_scelta = ?, Messaggio_motivazionale = ?"
				+ " WHERE idOfferta = ?")){
			
			if(newModalitaConsegnaScelta.equals("Ritiro in posta")) {
				UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres();
				ps.setInt(1, ufficioDAO.recuperaIdUfficio(connessioneDB, offertaModificata.getUfficioRitiro().getNome()));
			}
			else 
				ps.setNull(1, Types.INTEGER);
			
			if(newNota != null)
				ps.setString(2, newNota);
			else
				ps.setNull(2, Types.VARCHAR);
			
			if(newModalitaConsegnaScelta.equals("Spedizione"))
				ps.setString(3, offertaModificata.getIndirizzoSpedizione());
			else
				ps.setNull(3, Types.VARCHAR);
			
			if(newModalitaConsegnaScelta.equals("Incontro")) {
				ps.setString(4, offertaModificata.getOraInizioIncontro());
				ps.setString(5, offertaModificata.getOraFineIncontro());
				ps.setString(6, offertaModificata.getGiornoIncontro());
				ps.setString(7, offertaModificata.getSedeDIncontroScelta().getNome());
			}
			else {
				ps.setNull(4, Types.VARCHAR);
				ps.setNull(5, Types.VARCHAR);
				ps.setNull(6, Types.VARCHAR);
				ps.setNull(7, Types.VARCHAR);
			}
			
			ps.setString(8, newModalitaConsegnaScelta);
			
			if(newMessaggioMotivazionale != null)
				ps.setString(9, newMessaggioMotivazionale);
			else
				ps.setNull(9, Types.VARCHAR);
			
			ps.setInt(10, offertaModificata.getIdOfferta());
			
			ps.executeUpdate();

		}
	}

}