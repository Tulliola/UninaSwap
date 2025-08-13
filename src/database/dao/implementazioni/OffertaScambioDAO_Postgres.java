package database.dao.implementazioni;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import database.dao.interfacce.OffertaScambioDAO;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.OffertaScambio;
import dto.Oggetto;
import dto.ProfiloUtente;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;

public class OffertaScambioDAO_Postgres implements OffertaScambioDAO{
	private Connection connessioneDB = null;

	public OffertaScambioDAO_Postgres(Connection connessioneDB){
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public OffertaScambio recuperaOffertaScambioDaId(int idOfferta) throws SQLException, IOException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM OFFERTA_SCAMBIO WHERE idOfferta = ?")){
			ps.setInt(1, idOfferta);
			
			try(ResultSet rs = ps.executeQuery()){
				int idOffertaOttenuta = rs.getInt("idOfferta");
				Timestamp momentoProposta = rs.getTimestamp("momento_proposta");
				ModConsegnaEnum modConsegna = ModConsegnaEnum.confrontaConDB(rs.getString("modalita_consegna_scelta"));
				StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("stato"));
				Annuncio annuncioRiferito = recuperaAnnuncioOfferta(rs.getInt("idAnnuncio"));
				ArrayList<Oggetto> oggettiOfferti = recuperaOggettiOfferti(rs);
				
				return new OffertaScambio(idOffertaOttenuta, momentoProposta, modConsegna, 
						stato, annuncioRiferito, oggettiOfferti);
			}
		}
	}



	@Override
	public ArrayList<Offerta> recuperaOfferteScambioDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException {
		ArrayList<Offerta> offerteDiUtente = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_scambio WHERE Email = ?")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					offerteDiUtente.add(recuperaOffertaScambioDaId(rs.getInt("idOfferta")));
				}
				
				return offerteDiUtente;
			}
		}
	}

	@Override
	public ArrayList<Offerta> recuperaOfferteScambioNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException {
		ArrayList<Offerta> offerteDiUtente = new ArrayList();
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_scambio WHERE Email <> ?")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					offerteDiUtente.add(recuperaOffertaScambioDaId(rs.getInt("idOfferta")));
				}
				
				return offerteDiUtente;
			}
		}
	}
	
	//metodi ausiliari
	private ArrayList<Oggetto> recuperaOggettiOfferti(ResultSet rs) throws SQLException, IOException {
		ArrayList<Oggetto> oggettiOfferti = new ArrayList();
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM Oggetto WHERE idOggetto = ?")){
			psOggetto.setInt(1, rs.getInt("idOggetto"));
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){            
	            while(rsOggetto.next()) {
	            	int idOggetto = rsOggetto.getInt("idOggetto");
					CategoriaEnum categoria = CategoriaEnum.confrontaConDB(rsOggetto.getString("categoria"));
					CondizioneEnum condizioni = CondizioneEnum.confrontaConDB(rsOggetto.getString("condizioni"));
		            Path imagePath = Paths.get("images", "iconaCestino.png"); 
		            byte[] imageBytes = Files.readAllBytes(imagePath);
		            boolean disponibilita = isOggettoDisponibile(idOggetto);
		            
	            	oggettiOfferti.add(new Oggetto(idOggetto, categoria, condizioni,
	            			imageBytes, disponibilita));
	            }
	            
	            return oggettiOfferti;
			}
		}
	}

	private boolean isOggettoDisponibile(int idOggetto) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("(SELECT * FROM OGGETTO NATURAL JOIN ANNUNCIO WHERE idOggetto = ? AND"
				+ "(NOT(Stato = 'Venduto' OR Stato = 'Regalato' OR Stato = 'Scambiato' OR Stato = 'Indisponibile')))"
				+ "INTERSECT (SELECT * FROM OGGETTO NATURAL JOIN OFFERTA_SCAMBIO WHERE idOggetto = ? AND"
				+ "(NOT(Stato = 'Accettata')))")){
			ps.setInt(1, idOggetto);
			ps.setInt(2, idOggetto);
			
			try(ResultSet rs = ps.executeQuery()){
				return rs.next();
			}
		}
	}
	
	private Annuncio recuperaAnnuncioOfferta(int idAnnuncio) throws SQLException, IOException {
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
				Oggetto oggettoInAnnuncio = recuperaOggettoAnnuncio(rsAnnuncio.getInt("idOggetto"));
				
			
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

	private Oggetto recuperaOggettoAnnuncio(int idOggetto) throws SQLException, IOException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			ps.setInt(1, idOggetto);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				CategoriaEnum categoria = CategoriaEnum.confrontaConDB(rs.getString("categoria"));
				CondizioneEnum condizioni = CondizioneEnum.confrontaConDB(rs.getString("condizioni"));
				Path imagePath = Paths.get("images", "iconaCestino.png"); 
	            byte[] imageBytes = Files.readAllBytes(imagePath);
	            boolean disponibile = isOggettoDisponibile(idOggetto);
	            
	            return new Oggetto(idOggetto, categoria, condizioni, imageBytes, disponibile);
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
