package database.dao.implementazioni;

import java.io.File;
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

import database.dao.interfacce.AnnuncioDAO;
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
import utilities.StatoAnnuncioEnum;

public class AnnuncioDAO_Postgres implements AnnuncioDAO{
	Connection connessioneDB;
	
	public AnnuncioDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}

	@Override
	public Annuncio recuperaAnnuncioDaID(int idAnnuncio) throws SQLException, IOException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE idAnnuncio = ? AND Stato = 'Disponibile'")){
			ps.setInt(1, idAnnuncio);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				return annuncioCorrenteRecuperato(rs);
			}
		}
	}



	@Override
	public ArrayList<Annuncio> recuperaAnnunciDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException{
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email = ? ORDER BY Momento_pubblicazione DESC")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				System.out.println(rs.next());
				while(rs.next()) {
					toReturn.add(annuncioCorrenteRecuperato(rs));
				}
				return toReturn;
			}
		}
	}

	@Override
	public ArrayList<Annuncio> recuperaAnnunciNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException{
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email <> ? ORDER BY Momento_pubblicazione")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					toReturn.add(annuncioCorrenteRecuperato(rs));
				}
				return toReturn;
			}
		}
	}
	
	private Oggetto recuperaOggettoInAnnuncio(ResultSet rs) throws SQLException, IOException{
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, rs.getInt("idOggetto"));
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
	            Path imagePath = Paths.get("images", "iconaCestino.png"); 
	            byte[] imageBytes = Files.readAllBytes(imagePath);
				rsOggetto.next();
				return new Oggetto(
						rsOggetto.getInt("idOggetto"),
						CategoriaEnum.confrontaConDB(rsOggetto.getString("Categoria")),
						CondizioneEnum.confrontaConDB(rsOggetto.getString("Condizioni")),
						imageBytes,
						isOggettoDisponibile(rs.getInt("idOggetto"))
					);
			}
		}
	}

	private boolean isOggettoDisponibile(int idOggetto) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("(SELECT * FROM OGGETTO NATURAL JOIN ANNUNCIO WHERE idOggetto = ? AND "
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
	
	private ProfiloUtente recuperaUtenteProprietario(ResultSet rs) throws SQLException {
		try(PreparedStatement psUtente = connessioneDB.prepareStatement("SELECT * FROM PROFILO_UTENTE WHERE Email = ?")){
			psUtente.setString(1, rs.getString("Email"));
			
			try(ResultSet rsUtente = psUtente.executeQuery()){
				rsUtente.next();
				
				return new ProfiloUtente(
						rsUtente.getString("Username"),
						rsUtente.getString("Email"),
						rsUtente.getDouble("Saldo"),
						rsUtente.getBytes("Immagine_profilo"),
						rsUtente.getString("Residenza"),
						rsUtente.getString("PW"),
						rsUtente.getBoolean("Sospeso"));
						
			}
		}
	}
	
	private Annuncio annuncioCorrenteRecuperato(ResultSet rs) throws SQLException, IOException{
		Oggetto oggettoInAnnuncio = recuperaOggettoInAnnuncio(rs);
		ProfiloUtente utenteProprietario = recuperaUtenteProprietario(rs);
		int idAnnuncioRecuperato = rs.getInt("idAnnuncio");
		boolean spedizione = rs.getBoolean("Spedizione");
		boolean ritiroInPosta = rs.getBoolean("Ritiro_in_posta");
		boolean incontro = rs.getBoolean("Incontro");
		StatoAnnuncioEnum stato = StatoAnnuncioEnum.confrontaConDB(rs.getString("Stato"));
		Timestamp momentoPubblicazione = rs.getTimestamp("Momento_pubblicazione");
		String nome = rs.getString("Nome");
		if(rs.getString("Tipo_annuncio") == "Vendita") {
			return new AnnuncioVendita(idAnnuncioRecuperato, spedizione, ritiroInPosta, incontro, 
				stato, momentoPubblicazione, nome, utenteProprietario, 
				oggettoInAnnuncio, rs.getDouble("Prezzo_iniziale"));
		}
		else if(rs.getString("Tipo_annuncio") == "Scambio") {
			return new AnnuncioScambio(
				idAnnuncioRecuperato, spedizione, ritiroInPosta, incontro, 
				stato, momentoPubblicazione, nome, utenteProprietario, 
				oggettoInAnnuncio, rs.getString("Nota_scambio")	
			);
		}
		else {
			return new AnnuncioRegalo(idAnnuncioRecuperato, spedizione, 
				ritiroInPosta, incontro, stato, momentoPubblicazione, nome, 
				utenteProprietario, oggettoInAnnuncio
			);
		}
	}
}
