package database.dao.implementazioni;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.dao.interfacce.OffertaDAO;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.OffertaScambio;
import dto.Oggetto;
import dto.ProfiloUtente;
import dto.SedeUniversita;
import dto.UfficioPostale;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;

public class OffertaDAO_Postgres implements OffertaDAO{
	
	private Connection connessioneDB = null;
	
	private Offerta offerta;
	
	public OffertaDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}

	public OffertaDAO_Postgres(Connection connessioneDB, Offerta offerta) {
		this.connessioneDB = connessioneDB;
		this.offerta = offerta;
	}
	
	@Override
	public ArrayList<Offerta> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException{
		ArrayList<Offerta> offerteUtente = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("((SELECT "
				+ "email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ "ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ "modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_ACQUISTO "
				+ "WHERE email = ?) UNION (SELECT "
				+ "email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ "ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ "modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_SCAMBIO "
				+ "WHERE email = ?) ORDER BY momento_proposta DESC)")){
			
			ps.setString(1, utenteLoggato.getEmail());
			ps.setString(2, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
					do {
						AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
						
						Offerta offertaToAdd = null;
						Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
						ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta"));
						StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
						Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(rs.getInt("idAnnuncio"));
						String tipoAnnuncio = recuperaTipoAnnuncio(rs.getInt("idAnnuncio"));
						
						if(tipoAnnuncio.equals("Vendita")) {
							double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("Email"), rs.getTimestamp("Momento_proposta"));
							offertaToAdd = new OffertaAcquisto(utenteLoggato, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto);
						}
						else if(tipoAnnuncio.equals("Scambio")) {
							int idOfferta = recuperaIdOfferta(momentoProposta, utenteLoggato.getEmail());
							OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
							ArrayList<Oggetto> oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(idOfferta);
							
							offertaToAdd = new OffertaScambio(utenteLoggato, idOfferta, momentoProposta, modConsegna, stato, annuncioRiferito, oggettiOfferti);
						}
						else {
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
						}
						
						if(modConsegna.equals(ModConsegnaEnum.Ritiro_in_posta)) {
							UfficioPostaleDAO_Postgres ufficioDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
							UfficioPostale ufficioScelto = ufficioDAO.recuperaUfficioPostaleConId(rs.getInt("idUfficio"));
							offertaToAdd.setUfficioRitiro(ufficioScelto);
							
						}
						else if(modConsegna.equals(ModConsegnaEnum.Spedizione))
							offertaToAdd.setIndirizzoSpedizione(rs.getString("Indirizzo_spedizione"));
						else {
							offertaToAdd.setGiornoIncontro(GiornoEnum.confrontaConStringa(rs.getString("Giorno_incontro")));
							offertaToAdd.setOraInizioIncontro(rs.getString("Ora_inizio_incontro"));
							offertaToAdd.setOraFineIncontro(rs.getString("Ora_fine_incontro"));
							offertaToAdd.setSedeDIncontroScelta(new SedeUniversita(rs.getString("Sede_incontro")));
						}
						
						offerteUtente.add(offertaToAdd);
					} while(rs.next());
				}
				
				return offerteUtente;
			}
		}
	}

	@Override
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException{
		ArrayList<Offerta> offerteAnnuncio = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("((SELECT"
				+ "email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ "ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ "modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_ACQUISTO"
				+ "WHERE idAnnuncio = ?) UNION (SELECT"
				+ "email, idannuncio, idufficio, momento_proposta, nota, indirizzo_spedizione,"
				+ "ora_inizio_incontro, ora_fine_incontro, giorno_incontro, sede_incontro,"
				+ "modalita_consegna_scelta, stato, messaggio_motivazionale FROM OFFERTA_SCAMBIO"
				+ "WHERE idAnnuncio = ?) ORDER BY momento_proposta DESC)")){
			
			ps.setInt(1, annuncio.getIdAnnuncio());
			ps.setInt(2, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
				ProfiloUtente offerente = utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("email"));
				AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
				while(rs.next()) {
					Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
					ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
					Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(rs.getInt("idAnnuncio"));
					
					if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Vendita")){
						double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("Email"), rs.getTimestamp("Momento_proposta"));
						offerteAnnuncio.add(new OffertaAcquisto(offerente, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto));
					}
					
					if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Scambio")) {
						ArrayList<Integer> idOfferte = recuperaIdOfferte(momentoProposta, annuncio.getIdAnnuncio());
						ArrayList<Oggetto> oggettiOfferti = new ArrayList();
						
						OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
						
						for(int i = 0; i < idOfferte.size(); i++) {
							oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(idOfferte.get(i));
							offerteAnnuncio.add(new OffertaScambio(offerente, idOfferte.get(i), momentoProposta, modConsegna, stato, annuncioRiferito, oggettiOfferti));
						}
					}
					if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Regalo")) {
						double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("Email"), rs.getTimestamp("Momento_proposta"));
						
						ArrayList<Integer> idOfferte = recuperaIdOfferte(momentoProposta, annuncio.getIdAnnuncio());
						
						if(prezzoOfferto > 0) {
							offerteAnnuncio.add(new OffertaAcquisto(offerente, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto));
						}
						
						else if(!(idOfferte.isEmpty())) {
			
							OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
							
							for(int i = 0; i < idOfferte.size(); i++) {
								ArrayList<Oggetto> oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(idOfferte.get(i));
								offerteAnnuncio.add(new OffertaScambio(offerente, idOfferte.get(i), momentoProposta, modConsegna, stato, annuncioRiferito, oggettiOfferti));
							}
						}
						else {
							offerteAnnuncio.add(new OffertaRegalo(offerente, momentoProposta, modConsegna, stato, annuncioRiferito));
						}
					}
				}
				
				return offerteAnnuncio;
			}
		}
	}

	@Override
	public void inserisciOfferta(Offerta offertaDaInserire) throws SQLException{
		String emailOfferente = offertaDaInserire.getUtenteProprietario().getEmail();
		int idAnnuncioRiferito = offertaDaInserire.getAnnuncioRiferito().getIdAnnuncio();
		String modalitaConsegnaScelta = offertaDaInserire.getModalitaConsegnaScelta();
		String nota = offertaDaInserire.getNota();
		String messaggioMotivazionale = offertaDaInserire.getMessaggioMotivazionale();
		
		if(offertaDaInserire instanceof OffertaAcquisto) {
			String inserisciOffertaAcquisto = "INSERT INTO Offerta_acquisto(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
					+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, "
					+ "Prezzo_offerto, Messaggio_motivazionale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
			try(PreparedStatement psInserisciOffertaAcquisto = connessioneDB.prepareStatement(inserisciOffertaAcquisto)){
				psInserisciOffertaAcquisto.setString(1, emailOfferente);
				psInserisciOffertaAcquisto.setInt(2, idAnnuncioRiferito);
				
				if(modalitaConsegnaScelta.equals("Ritiro in posta"))
					psInserisciOffertaAcquisto.setInt(3, offertaDaInserire.getUfficioRitiro().getIdUfficio());
				else
					psInserisciOffertaAcquisto.setNull(3, Types.INTEGER);
				
				psInserisciOffertaAcquisto.setString(4, nota);
				
				if(modalitaConsegnaScelta.equals("Spedizione"))
					psInserisciOffertaAcquisto.setString(5, offertaDaInserire.getIndirizzoSpedizione());
				else
					psInserisciOffertaAcquisto.setNull(5, Types.VARCHAR);
				
				if(modalitaConsegnaScelta.equals("Incontro")) {
					psInserisciOffertaAcquisto.setString(6, offertaDaInserire.getOraInizioIncontro());
					psInserisciOffertaAcquisto.setString(7, offertaDaInserire.getOraFineIncontro());
					psInserisciOffertaAcquisto.setString(8, offertaDaInserire.getGiornoIncontro());
					psInserisciOffertaAcquisto.setString(9, offertaDaInserire.getSedeDIncontroScelta().toString());
				}
				else {
					psInserisciOffertaAcquisto.setNull(6, Types.VARCHAR);
					psInserisciOffertaAcquisto.setNull(7, Types.VARCHAR);
					psInserisciOffertaAcquisto.setNull(8, Types.VARCHAR);
					psInserisciOffertaAcquisto.setNull(9, Types.VARCHAR);
				}
									
				psInserisciOffertaAcquisto.setString(10, modalitaConsegnaScelta);;
				psInserisciOffertaAcquisto.setDouble(11, offertaDaInserire.getPrezzoOfferto());
				
				if(messaggioMotivazionale != null)
					psInserisciOffertaAcquisto.setString(12, messaggioMotivazionale);
				else
					psInserisciOffertaAcquisto.setNull(12, Types.VARCHAR);
					
				psInserisciOffertaAcquisto.executeUpdate();
				
			}
		}
		
		if(offertaDaInserire instanceof OffertaScambio) {
			connessioneDB.setAutoCommit(false);
			
			String inserisciOffertaScambio = "INSERT INTO Offerta_scambio(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
					+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, Messaggio_motivazionale) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idOfferta";
						
			try(PreparedStatement psInserisciOffertaScambio = connessioneDB.prepareStatement(inserisciOffertaScambio)){
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
					
				psInserisciOffertaScambio.setString(10, modalitaConsegnaScelta);;
				
				if(messaggioMotivazionale != null)
					psInserisciOffertaScambio.setString(11, messaggioMotivazionale);
				else
					psInserisciOffertaScambio.setNull(11, Types.VARCHAR);
				
				int idOffertaInserita;
				
				try(ResultSet rsInserisciOffertaScambio = psInserisciOffertaScambio.executeQuery()){
					rsInserisciOffertaScambio.next();
					idOffertaInserita = rsInserisciOffertaScambio.getInt("idOfferta");
				}
				
				for(Oggetto oggettoCorrente : offertaDaInserire.getOggettiOfferti()) {
					OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
					Integer idOggettoInserito = oggettoDAO.inserisciOggetto(oggettoCorrente, emailOfferente);
					
					String inserisciOggettoOfferto = "INSERT INTO Oggetto_offerto (idOggetto, idOfferta) VALUES (?, ?)";
					
					try(PreparedStatement psInserisciOggettoOfferto = connessioneDB.prepareStatement(inserisciOggettoOfferto)){
						psInserisciOggettoOfferto.setInt(1, idOggettoInserito);
						psInserisciOggettoOfferto.setInt(2, idOffertaInserita);
						
						psInserisciOggettoOfferto.executeUpdate();
					}
				}
				
				connessioneDB.commit();
			}
			catch(SQLException exc) {
				connessioneDB.rollback();
				exc.printStackTrace();
				System.out.println(exc.getErrorCode());
				System.out.println(exc.getMessage());
				System.out.println(exc.getSQLState());
				throw exc;
			}
			finally{
				connessioneDB.setAutoCommit(true);
			}
		}
		
		if(offertaDaInserire instanceof OffertaRegalo) {
			String inserisciOffertaAcquisto = "INSERT INTO Offerta_acquisto(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
					+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, "
					+ "Prezzo_offerto, Messaggio_motivazionale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
			try(PreparedStatement psInserisciOffertaAcquisto = connessioneDB.prepareStatement(inserisciOffertaAcquisto)){
				psInserisciOffertaAcquisto.setString(1, emailOfferente);
				psInserisciOffertaAcquisto.setInt(2, idAnnuncioRiferito);
				
				if(modalitaConsegnaScelta.equals("Ritiro in posta"))
					psInserisciOffertaAcquisto.setInt(3, offertaDaInserire.getUfficioRitiro().getIdUfficio());
				else
					psInserisciOffertaAcquisto.setNull(3, Types.INTEGER);
				
				psInserisciOffertaAcquisto.setString(4, nota);
				
				if(modalitaConsegnaScelta.equals("Spedizione"))
					psInserisciOffertaAcquisto.setString(5, offertaDaInserire.getIndirizzoSpedizione());
				else
					psInserisciOffertaAcquisto.setNull(5, Types.VARCHAR);
				
				if(modalitaConsegnaScelta.equals("Incontro")) {
					psInserisciOffertaAcquisto.setString(6, offertaDaInserire.getOraInizioIncontro());
					psInserisciOffertaAcquisto.setString(7, offertaDaInserire.getOraFineIncontro());
					psInserisciOffertaAcquisto.setString(8, offertaDaInserire.getGiornoIncontro());
					psInserisciOffertaAcquisto.setString(9, offertaDaInserire.getSedeDIncontroScelta().toString());
				}
				else {
					psInserisciOffertaAcquisto.setNull(6, Types.VARCHAR);
					psInserisciOffertaAcquisto.setNull(7, Types.VARCHAR);
					psInserisciOffertaAcquisto.setNull(8, Types.VARCHAR);
					psInserisciOffertaAcquisto.setNull(9, Types.VARCHAR);
				}
					
			
				psInserisciOffertaAcquisto.setString(10, modalitaConsegnaScelta);;
				psInserisciOffertaAcquisto.setDouble(11, 0);
				psInserisciOffertaAcquisto.setString(12, offertaDaInserire.getMessaggioMotivazionale());
				
				psInserisciOffertaAcquisto.executeUpdate();
				
			}
		}
	}
	
	//Metodi ausiliari
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
	
	private ArrayList<Integer> recuperaIdOfferte(Timestamp momentoProposta, int idAnnuncio) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT idOfferta FROM OFFERTA_SCAMBIO WHERE "
				+ "momento_proposta = ? AND idAnnuncio = ?")){
			ps.setTimestamp(1, momentoProposta);
			ps.setInt(2, idAnnuncio);
			
			try(ResultSet rs = ps.executeQuery()){
				ArrayList<Integer> idOfferteStessoMomento = new ArrayList();
				while(rs.next()) {
					idOfferteStessoMomento.add(rs.getInt("idOfferta"));
				}
				
				return idOfferteStessoMomento;
			}
		}
	}

	private String recuperaTipoAnnuncio(int idAnnuncio) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT tipo_annuncio FROM Annuncio WHERE idAnnuncio = ?")){
			ps.setInt(1, idAnnuncio);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				return rs.getString("tipo_annuncio");
			}
		}
	}


	private Double recuperaPrezzoOfferta(String email, Timestamp momentoProposta) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT prezzo_offerto from OFFERTA_ACQUISTO WHERE Email = ? AND Momento_proposta = ?")){
			ps.setString(1, email);
			ps.setTimestamp(2, momentoProposta);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next())
					return rs.getDouble("prezzo_offerto");
			}
		}
		
		return null;
	}
	
	@Override
	public ArrayList<Offerta> recuperaOfferteAnnuncioRegalo(Annuncio annuncioRecuperato) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList();

		toReturn.addAll(recuperaOfferteAnnuncioVendita(annuncioRecuperato));
		toReturn.addAll(recuperaOfferteAnnuncioScambio(annuncioRecuperato));
		
		return toReturn;
	}

	@Override
	public ArrayList<Offerta> recuperaOfferteAnnuncioScambio(Annuncio annuncioRecuperato) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList();
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM OFFERTA_SCAMBIO WHERE idAnnuncio = ? ORDER BY Momento_proposta DESC")){
			ps.setInt(1, annuncioRecuperato.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);

					ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
					toReturn.add(new OffertaScambio(utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("email")),  rs.getInt("idOfferta"),
							rs.getTimestamp("momento_proposta"), ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta")),
							StatoOffertaEnum.confrontaConDB(rs.getString("stato")), annuncioRecuperato, oggettoDAO.recuperaOggettiOffertiConIdOfferta(rs.getInt("idOfferta"))));
				}
			}
		}
		
		return toReturn;
	}

	@Override
	public ArrayList<Offerta> recuperaOfferteAnnuncioVendita(Annuncio annuncioRecuperato) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList();
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM OFFERTA_ACQUISTO WHERE idAnnuncio = ?")){
			ps.setInt(1, annuncioRecuperato.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
				while(rs.next()) {
					toReturn.add(new OffertaAcquisto(profiloDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("email")), 
							rs.getTimestamp("momento_proposta"), ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta")),
							StatoOffertaEnum.confrontaConDB(rs.getString("stato")), annuncioRecuperato, rs.getDouble("prezzo_offerto")));
				}
			}
		}
		return toReturn;
	}
	
	private void settaParametriComuniPerInserimento() throws SQLException {
		
	}
}
