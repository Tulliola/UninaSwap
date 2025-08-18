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
import utilities.GiornoEnum;
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
	public Annuncio recuperaAnnuncioDaID(int idAnnuncio) throws SQLException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE idAnnuncio = ?")){
			ps.setInt(1, idAnnuncio);
			
			try(ResultSet rs = ps.executeQuery()){
				rs.next();
				return annuncioCorrenteRecuperato(rs);
			}
		}
	}



	@Override
	public ArrayList<Annuncio> recuperaAnnunciDiUtente(ProfiloUtente utenteLoggato) throws SQLException{
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email = ? ORDER BY Momento_pubblicazione ASC")){
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
	public ArrayList<Annuncio> recuperaAnnunciInBacheca(ProfiloUtente utenteLoggato) throws SQLException{
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
		
		try {
			OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
			Integer idOggettoInserito = oggettoDAO.inserisciOggetto(annuncioDaInserire.getOggettoInAnnuncio(), annuncioDaInserire.getUtenteProprietario().getEmail());
		
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

	
	private Annuncio annuncioCorrenteRecuperato(ResultSet rs) throws SQLException{
		Annuncio annuncioRecuperato;
		OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
		
		int idAnnuncioRecuperato = rs.getInt("idAnnuncio");
		boolean spedizione = rs.getBoolean("Spedizione");
		boolean ritiroInPosta = rs.getBoolean("Ritiro_in_posta");
		boolean incontro = rs.getBoolean("Incontro");
		StatoAnnuncioEnum stato = StatoAnnuncioEnum.confrontaConDB(rs.getString("Stato"));
		Timestamp momentoPubblicazione = rs.getTimestamp("Momento_pubblicazione");
		String nome = rs.getString("Nome");
		Oggetto oggettoInAnnuncio = oggettoDAO.recuperaOggettoConId(rs.getInt("idOggetto"));
		ProfiloUtente utenteProprietario = recuperaUtenteProprietario(rs.getString("Email"));
		

		if(rs.getString("Tipo_annuncio").equals("Vendita")) {
			annuncioRecuperato = new AnnuncioVendita(idAnnuncioRecuperato, spedizione, ritiroInPosta, incontro, 
				stato, momentoPubblicazione, nome, utenteProprietario, 
				oggettoInAnnuncio, rs.getDouble("Prezzo_iniziale"));
		}
		else if(rs.getString("Tipo_annuncio").equals("Scambio")) {
			annuncioRecuperato = new AnnuncioScambio(
				idAnnuncioRecuperato, spedizione, ritiroInPosta, incontro, 
				stato, momentoPubblicazione, nome, utenteProprietario, 
				oggettoInAnnuncio, rs.getString("Nota_scambio")	
			);
		}
		else {
			annuncioRecuperato = new AnnuncioRegalo(idAnnuncioRecuperato, spedizione, 
				ritiroInPosta, incontro, stato, momentoPubblicazione, nome, 
				utenteProprietario, oggettoInAnnuncio
			);
		}
		
		if(rs.getBoolean("Incontro")) {
			String recuperaIncontri = "SELECT * FROM Incontro WHERE idAnnuncio = ?";
			
			try(PreparedStatement psRecuperaIncontri = connessioneDB.prepareStatement(recuperaIncontri)){
				psRecuperaIncontri.setInt(1, idAnnuncioRecuperato);
				
				try(ResultSet rsRecuperaIncontri = psRecuperaIncontri.executeQuery()){
					while(rsRecuperaIncontri.next()) {
						SedeUniversita sedeDiIncontro;
						
						SedeUniversitaDAO_Postgres sedeUniversitaDAO = new SedeUniversitaDAO_Postgres(this.connessioneDB);
						sedeDiIncontro = sedeUniversitaDAO.recuperaSedeDaId(rsRecuperaIncontri.getInt("idSede"));
						
						annuncioRecuperato.aggiungiPropostaIncontro(sedeDiIncontro, rsRecuperaIncontri.getString("Ora_inizio_incontro"), 
																	rsRecuperaIncontri.getString("Ora_fine_incontro"), GiornoEnum.confrontaConStringa(rsRecuperaIncontri.getString("Giorno_incontro")));
					}
				}
			}
		}
		
		if(rs.getDate("Data_scadenza") != null)
			annuncioRecuperato.setDataScadenza(rs.getDate("Data_scadenza"));
		
		return annuncioRecuperato;
	}
	
	
}
