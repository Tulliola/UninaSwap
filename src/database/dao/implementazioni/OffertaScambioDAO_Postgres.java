package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import database.dao.interfacce.OffertaScambioDAO;
import dto.Annuncio;
import dto.OffertaScambio;
import dto.Oggetto;
import dto.ProfiloUtente;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaScambioDAO_Postgres implements OffertaScambioDAO{

	private Connection connessioneDB;
	
	public OffertaScambioDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	
	@Override
	public ArrayList<OffertaScambio> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<OffertaScambio> toReturn = new ArrayList<OffertaScambio>();
		OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
		
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
					Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(rs.getInt("idAnnuncio"));
					ArrayList<Oggetto> oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(rs.getInt("idOfferta"));
					
					offertaToAdd = new OffertaScambio(offerente, idOfferta, momentoProposta, modConsegnaScelta,
							stato, annuncioRiferito, oggettiOfferti);
					
					if(rs.getString("Messaggio_motivazionale") != null) {
						offertaToAdd.setMessaggioMotivazionale(rs.getString("Messaggio_motivazionale"));
					}
					
					toReturn.add(offertaToAdd);
				}
				return toReturn;
			}
		}
	}

	@Override
	public ArrayList<OffertaScambio> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException {
		ArrayList<OffertaScambio> toReturn = new ArrayList<OffertaScambio>();
		
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		OggettoDAO_Postgres oggettoDAO = new OggettoDAO_Postgres(connessioneDB);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_scambio WHERE idAnnuncio = ?")){
			ps.setInt(1, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					OffertaScambio offertaToAdd;
					
					ProfiloUtente offerente = utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("Email"));
					int idOfferta = rs.getInt("idOfferta");
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncio;
					ArrayList<Oggetto> oggettiOfferti = oggettoDAO.recuperaOggettiOffertiConIdOfferta(rs.getInt("idOfferta"));
					
					offertaToAdd = new OffertaScambio(offerente, idOfferta, momentoProposta, modConsegnaScelta,
							stato, annuncioRiferito, oggettiOfferti);
					
					toReturn.add(offertaToAdd);
				}
				return toReturn;
			}
		}
	}

	@Override
	public void inserisciOfferta(OffertaScambio offertaDaInserire) throws SQLException {
		String inserisciOffertaScambio = "INSERT INTO Offerta_scambio(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
				+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING idOfferta";
		
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
				
			psInserisciOffertaScambio.setString(10, modalitaConsegnaScelta);;
			
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

	@Override
	public void updateStatoOfferta(OffertaScambio offerta, StatoOffertaEnum stato) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			ps.setString(1, stato.toString());
			ps.setString(2, offerta.getUtenteProprietario().getEmail());
			ps.setTimestamp(3, offerta.getMomentoProposta());
			
			ps.executeUpdate();
		}
	}
}