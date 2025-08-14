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
import java.sql.Types;
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
import dto.SedeUniversita;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.StatoAnnuncioEnum;

public class AnnuncioDAO_Postgres implements AnnuncioDAO{
	private Connection connessioneDB;
	private Annuncio annuncio;
	
	//Costruttore per il retrieve
	public AnnuncioDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	//Costruttore per INSERT, UPDATE e DELETE
	public AnnuncioDAO_Postgres(Connection connessioneDB, Annuncio annuncio) {
		this.connessioneDB = connessioneDB;
		this.annuncio = annuncio;
	}

	@Override
	public Annuncio recuperaAnnuncioDaID(int idAnnuncio) throws SQLException, IOException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE idAnnuncio = ?")){
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
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email <> ? AND Stato = 'Disponibile' ORDER BY Momento_pubblicazione DESC")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					toReturn.add(annuncioCorrenteRecuperato(rs));
				}
				return toReturn;
			}
		}
	}
	

	@Override
	public void inserisciAnnuncio(Annuncio annuncioDaInserire) throws SQLException {
		connessioneDB.setAutoCommit(false);
		

		String inserimentoOggetto = "INSERT INTO Oggetto (Email, Descrizione, Categoria, Condizioni) VALUES (?, ?, ?, ?) RETURNING idOggetto";
		
		try (PreparedStatement psInserimentoOggetto = connessioneDB.prepareStatement(inserimentoOggetto)){
			psInserimentoOggetto.setString(1, annuncioDaInserire.getUtenteProprietario().getEmail());
			psInserimentoOggetto.setString(2, annuncioDaInserire.getOggettoInAnnuncio().getDescrizione());
			psInserimentoOggetto.setString(3, annuncioDaInserire.getOggettoInAnnuncio().getCategoria());
			psInserimentoOggetto.setString(4, annuncioDaInserire.getOggettoInAnnuncio().getCondizioni());

			int idOggettoInserito; 
			
			try(ResultSet rsInserimentoOggetto = psInserimentoOggetto.executeQuery()){
				rsInserimentoOggetto.next();
				idOggettoInserito = rsInserimentoOggetto.getInt("idOggetto");
			}
			

			for(int i = 0; i < 3; i++) {
				if(annuncioDaInserire.getOggettoInAnnuncio().getImmagine(i) != null) {
					String inserimentoImmagini = "INSERT INTO Immagine (File_immagine, idOggetto) VALUES (?, ?)";
					
					try(PreparedStatement psInserimentoImmagini = connessioneDB.prepareStatement(inserimentoImmagini)){
						psInserimentoImmagini.setBytes(1, annuncioDaInserire.getOggettoInAnnuncio().getImmagine(i));
						psInserimentoImmagini.setInt(2, idOggettoInserito);
						
						psInserimentoImmagini.executeUpdate();
					}
				}
			}
			
			String inserimentoAnnuncio = "INSERT INTO Annuncio (Email, idOggetto, Spedizione, Incontro, Ritiro_in_posta, Nome, Tipo_annuncio,"
					+ "Nota_scambio, Prezzo_iniziale, Data_scadenza)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idAnnuncio";
			
			try(PreparedStatement psInserimentoAnnuncio = connessioneDB.prepareStatement(inserimentoAnnuncio)){
				psInserimentoAnnuncio.setString(1, annuncioDaInserire.getUtenteProprietario().getEmail());
				psInserimentoAnnuncio.setInt(2, idOggettoInserito);
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
			
				System.out.println(idAnnuncioInserito);

				if(annuncioDaInserire.isIncontro())
					for(int i = 0; i < annuncioDaInserire.getSedeIncontroProposte().size(); i++) {					
						String inserimentoIncontri = "INSERT INTO Incontro (idSede, idAnnuncio, Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro) VALUES (?, ?, ?, ?, ?)";
						
						try(PreparedStatement psInserimentoIncontri = connessioneDB.prepareStatement(inserimentoIncontri)){
							psInserimentoIncontri.setInt(1, annuncioDaInserire.getSedeIncontroProposte().get(i).getIdSede());
							psInserimentoIncontri.setInt(2, idAnnuncioInserito);
							psInserimentoIncontri.setString(3, annuncioDaInserire.getOraInizioIncontro().get(i));
							psInserimentoIncontri.setString(4, annuncioDaInserire.getOraFineIncontro().get(i));
							psInserimentoIncontri.setString(5, annuncioDaInserire.getGiornoIncontro().get(i).toString());
							
							psInserimentoIncontri.executeUpdate();
							
						}
						

					}
				
				System.out.println("Sono quo");

			}

			connessioneDB.commit();

		}
		catch (SQLException exc) {
		    connessioneDB.rollback();
		    throw exc;
		} 
		finally {
		    connessioneDB.setAutoCommit(true);
		}
	}	
	
	private Oggetto recuperaOggettoInAnnuncio(int idOggetto) throws SQLException, IOException{
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, idOggetto);
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				rsOggetto.next();
				Oggetto oggettoInAnnuncio = new Oggetto(
						rsOggetto.getInt("idOggetto"),
						CategoriaEnum.confrontaConStringa(rsOggetto.getString("Categoria")),
						CondizioneEnum.confrontaConStringa(rsOggetto.getString("Condizioni")),
						this.recuperaPrimaImmagineDiOggetto(rsOggetto.getInt("idOggetto")),
						isOggettoDisponibile(idOggetto)
				);
				
				if(rsOggetto.getString("Descrizione") != null) {
					oggettoInAnnuncio.setDescrizione(rsOggetto.getString("Descrizione"));
				}
				
				return oggettoInAnnuncio;
			}
		}
	}

	private boolean isOggettoDisponibile(int idOggetto) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("(SELECT idOggetto FROM OGGETTO NATURAL JOIN ANNUNCIO WHERE idOggetto = ? AND "
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
	
	private ProfiloUtente recuperaUtenteProprietario(String email) throws SQLException {
		try(PreparedStatement psUtente = connessioneDB.prepareStatement("SELECT * FROM PROFILO_UTENTE WHERE Email = ?")){
			psUtente.setString(1, email);
			
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
		int idAnnuncioRecuperato = rs.getInt("idAnnuncio");
		boolean spedizione = rs.getBoolean("Spedizione");
		boolean ritiroInPosta = rs.getBoolean("Ritiro_in_posta");
		boolean incontro = rs.getBoolean("Incontro");
		StatoAnnuncioEnum stato = StatoAnnuncioEnum.confrontaConDB(rs.getString("Stato"));
		Timestamp momentoPubblicazione = rs.getTimestamp("Momento_pubblicazione");
		String nome = rs.getString("Nome");
		Oggetto oggettoInAnnuncio = recuperaOggettoInAnnuncio(rs.getInt("IdOggetto"));
		ProfiloUtente utenteProprietario = recuperaUtenteProprietario(rs.getString("Email"));
		
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
	
	private byte[] recuperaPrimaImmagineDiOggetto(int idOggetto) throws SQLException{
		String recuperaImmagine = "SELECT File_immagine FROM Immagine WHERE idOggetto = ?";
		
		try(PreparedStatement psRecuperaImmagine = connessioneDB.prepareStatement(recuperaImmagine)){
			psRecuperaImmagine.setInt(1, idOggetto);
			
			try(ResultSet rsRecuperaImmagine = psRecuperaImmagine.executeQuery()){
				if(rsRecuperaImmagine.next())
					return rsRecuperaImmagine.getBytes("File_immagine");
				else
					return null;
			}
		}
	}
}
