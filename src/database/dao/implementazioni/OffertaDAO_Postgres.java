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
	public ArrayList<Offerta> recuperaOfferteDiUtente(String email) throws SQLException{
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
			
			ps.setString(1, email);
			ps.setString(2, email);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					ProfiloUtente offerente = this.recuperaUtente(rs.getString("email"));
					do {
						Offerta offertaToAdd = null;
						Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
						ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta"));
						StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
						Annuncio annuncioRiferito = recuperaAnnuncio(rs.getInt("idAnnuncio"));
						
						if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Vendita")) {
							double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("Email"), rs.getTimestamp("Momento_proposta"));
							offertaToAdd = new OffertaAcquisto(offerente, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto);
						}
						
						if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Scambio")) {
							int idOfferta = recuperaIdOfferta(momentoProposta, email);
							ArrayList<Oggetto> oggettiOfferti = recuperaOggettiOfferti(idOfferta);
							
							offertaToAdd = new OffertaScambio(offerente, idOfferta, momentoProposta, modConsegna, stato, annuncioRiferito, oggettiOfferti);
						}
						if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Regalo")) {
							double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("email"), rs.getTimestamp("Momento_proposta"));
							
							int idOfferta = recuperaIdOfferta(momentoProposta, email);
							ArrayList<Oggetto> oggettiOfferti = recuperaOggettiOfferti(idOfferta);
							
							if(prezzoOfferto > 0) {
								offertaToAdd = new OffertaAcquisto(offerente, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto);
							}
							
							else if(!(oggettiOfferti.isEmpty())) {
								offertaToAdd = new OffertaScambio(offerente, idOfferta, momentoProposta, modConsegna, stato, annuncioRiferito, oggettiOfferti);
							}
							else {
								offertaToAdd = new OffertaRegalo(offerente, momentoProposta, modConsegna, stato, annuncioRiferito);
							}
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
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(int idAnnuncio) throws SQLException, IOException{
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
			
			ps.setInt(1, idAnnuncio);
			ps.setInt(2, idAnnuncio);
			
			try(ResultSet rs = ps.executeQuery()){
				ProfiloUtente offerente = this.recuperaUtente(rs.getString("email"));
				
				while(rs.next()) {
					Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
					ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConStringa(rs.getString("modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
					Annuncio annuncioRiferito = recuperaAnnuncio(rs.getInt("idAnnuncio"));
					
					if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Vendita")){
						double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("Email"), rs.getTimestamp("Momento_proposta"));
						offerteAnnuncio.add(new OffertaAcquisto(offerente, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto));
					}
					
					if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Scambio")) {
						ArrayList<Integer> idOfferte = recuperaIdOfferte(momentoProposta, idAnnuncio);
						ArrayList<Oggetto> oggettiOfferti = new ArrayList();
						for(int i = 0; i < idOfferte.size(); i++) {
							oggettiOfferti = recuperaOggettiOfferti(idOfferte.get(i));
							offerteAnnuncio.add(new OffertaScambio(offerente, idOfferte.get(i), momentoProposta, modConsegna, stato, annuncioRiferito, oggettiOfferti));
						}
					}
					if(recuperaTipoAnnuncio(rs.getInt("idAnnuncio")).equals("Regalo")) {
						double prezzoOfferto = recuperaPrezzoOfferta(rs.getString("Email"), rs.getTimestamp("Momento_proposta"));
						
						ArrayList<Integer> idOfferte = recuperaIdOfferte(momentoProposta, idAnnuncio);
						
						if(prezzoOfferto > 0) {
							offerteAnnuncio.add(new OffertaAcquisto(offerente, momentoProposta, modConsegna, stato, annuncioRiferito, prezzoOfferto));
						}
						
						else if(!(idOfferte.isEmpty())) {
							for(int i = 0; i < idOfferte.size(); i++) {
								ArrayList<Oggetto> oggettiOfferti = recuperaOggettiOfferti(idOfferte.get(i));
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
		
		if(offertaDaInserire instanceof OffertaAcquisto) {
			String inserisciOffertaAcquisto = "INSERT INTO Offerta_acquisto(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
					+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, "
					+ "Prezzo_offerto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						
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
				
				psInserisciOffertaAcquisto.executeUpdate();
				
			}
		}
	}
	
	//Metodi ausiliari
	private ArrayList<Oggetto> recuperaOggettiOfferti(int idOfferta) throws SQLException {
		ArrayList<Oggetto> oggettiOfferti = new ArrayList();
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT idOggetto FROM OGGETTO_OFFERTO WHERE idOfferta = ?")){
			ps.setInt(1, idOfferta);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					oggettiOfferti.add(recuperaOggetto(rs.getInt("idOggetto")));
					
				}
				
				return oggettiOfferti;
			}
		}
		
	}

	private int recuperaIdOfferta(Timestamp momentoProposta, String email) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT idOfferta FROM OFFERTA_SCAMBIO WHERE "
				+ "momento_proposta = ? AND email = ?")){
			ps.setTimestamp(1, momentoProposta);
			ps.setString(2, email);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				return rs.getInt("idOfferta");
			}
		}
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


	private double recuperaPrezzoOfferta(String email, Timestamp momentoProposta) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT prezzo_offerto from OFFERTA_ACQUISTO WHERE Email = ? AND Momento_proposta = ?")){
			ps.setString(1, email);
			ps.setTimestamp(2, momentoProposta);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				
				return rs.getDouble("prezzo_offerto");
			}
		}
	}
	
	private Annuncio recuperaAnnuncio(int idAnnuncio) throws SQLException {
		try(PreparedStatement psAnnuncio = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE idAnnuncio = ?")){
			psAnnuncio.setInt(1, idAnnuncio);
			
			
			try(ResultSet rsAnnuncio = psAnnuncio.executeQuery()){
				rsAnnuncio.next();
				
				boolean spedizione = rsAnnuncio.getBoolean("spedizione");
				boolean ritiroInPosta = rsAnnuncio.getBoolean("ritiro_in_posta");
				boolean incontro = rsAnnuncio.getBoolean("incontro");
				StatoAnnuncioEnum stato = StatoAnnuncioEnum.confrontaConDB(rsAnnuncio.getString("stato"));
				Timestamp momentoPubblicazione = rsAnnuncio.getTimestamp("momento_pubblicazione");
				String nome = rsAnnuncio.getString("nome");
				ProfiloUtente utenteProprietario = recuperaUtente(rsAnnuncio.getString("Email"));
				Oggetto oggettoInAnnuncio = recuperaOggetto(rsAnnuncio.getInt("idOggetto"));
				
			
				if(rsAnnuncio.getString("Tipo_annuncio") == "Vendita") {
					double prezzoIniziale = rsAnnuncio.getDouble("prezzo_iniziale");
					
					return new AnnuncioVendita(idAnnuncio, spedizione, ritiroInPosta, incontro, stato,
							momentoPubblicazione, nome, utenteProprietario, oggettoInAnnuncio, prezzoIniziale);
				}
				else if(rsAnnuncio.getString("Tipo_annuncio") == "Scambio") {
					String notaScambio = rsAnnuncio.getString("nota_scambio");
					return new AnnuncioScambio(idAnnuncio, spedizione, ritiroInPosta, incontro, stato,
							momentoPubblicazione, nome, utenteProprietario, oggettoInAnnuncio, notaScambio);
				}
				else {
					return new AnnuncioRegalo(idAnnuncio, spedizione, ritiroInPosta, incontro, stato,
							momentoPubblicazione, nome, utenteProprietario, oggettoInAnnuncio);
				}
			}
		}
	}

	private Oggetto recuperaOggetto(int idOggetto) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Oggetto NATURAL JOIN Immagine WHERE idOggetto = ?")){
			ps.setInt(1, idOggetto);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				CategoriaEnum categoria = CategoriaEnum.confrontaConStringa(rs.getString("categoria"));
				CondizioneEnum condizioni = CondizioneEnum.confrontaConStringa(rs.getString("condizioni"));
	            boolean disponibile = isOggettoDisponibile(idOggetto);
	           	byte[] immagineOggetto = rs.getBytes("File_immagine");	
	            
	            return new Oggetto(idOggetto, categoria, condizioni, immagineOggetto, disponibile);
			}
		}
	}

	private boolean isOggettoDisponibile(int idOggetto) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("(SELECT idOggetto FROM OGGETTO NATURAL JOIN ANNUNCIO WHERE idOggetto = ? AND"
				+ "(NOT(Stato = 'Venduto' OR Stato = 'Regalato' OR Stato = 'Scambiato' OR Stato = 'Indisponibile')))"
				+ "INTERSECT (SELECT idOggetto FROM OGGETTO NATURAL JOIN OFFERTA_SCAMBIO WHERE idOggetto = ? AND"
				+ "(NOT(Stato = 'Accettata')))")){
			ps.setInt(1, idOggetto);
			ps.setInt(2, idOggetto);
			
			try(ResultSet rs = ps.executeQuery()){
				return rs.next();
			}
		}
	}

	private ProfiloUtente recuperaUtente(String email) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM PROFILO_UTENTE WHERE Email = ?")){
			ps.setString(1, email);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				String username = rs.getString("username");
				double saldo = rs.getDouble("saldo");
				byte[] immagineProfilo = rs.getBytes("immagine_profilo");
				String residenza = rs.getString("residenza");
				String password = rs.getString("PW");
				boolean sospeso = rs.getBoolean("sospeso");
				
				return new ProfiloUtente(username, email, saldo, immagineProfilo, residenza, password, sospeso);
			}
		}
	}
}
