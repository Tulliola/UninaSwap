package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.sql.Types;

import java.util.ArrayList;

import database.dao.interfacce.OffertaAcquistoDAO;
import database.dao.interfacce.OffertaDAO;
import dto.Annuncio;

import dto.AnnuncioVendita;
import dto.Offerta;

import dto.OffertaAcquisto;
import dto.ProfiloUtente;
import dto.SedeUniversita;
import dto.UfficioPostale;
import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaAcquistoDAO_Postgres implements OffertaDAO, OffertaAcquistoDAO{

	private Connection connessioneDB;
	
	public OffertaAcquistoDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public ArrayList<Offerta> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList<Offerta>();
		
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE Email = ?")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ProfiloUtente offerente = utenteLoggato;
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(rs.getInt("idAnnuncio"));
					double prezzoOfferto = rs.getDouble("Prezzo_offerto");
					
					OffertaAcquisto offertaToAdd = new OffertaAcquisto(offerente, momentoProposta, modConsegnaScelta, stato,
							annuncioRiferito, prezzoOfferto);
					
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
						SedeUniversita sedeScelta = sedeDAO.recuperaSedeNome(rs.getString("Sede_incontro"));
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
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException {
		ArrayList<Offerta> toReturn = new ArrayList<Offerta>();
		
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE idAnnuncio = ?")){
			ps.setInt(1, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					ProfiloUtente offerente = utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("Email"));
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncio;
					double prezzoOfferto = rs.getDouble("Prezzo_offerto");
					
					OffertaAcquisto offertaToAdd = new OffertaAcquisto(offerente, momentoProposta, modConsegnaScelta, stato,
							annuncioRiferito, prezzoOfferto);
					
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
						SedeUniversita sedeScelta = sedeDAO.recuperaSedeNome(rs.getString("Sede_incontro"));
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
		String emailOfferente = offertaDaInserire.getUtenteProprietario().getEmail();
		int idAnnuncioRiferito = offertaDaInserire.getAnnuncioRiferito().getIdAnnuncio();
		String modalitaConsegnaScelta = offertaDaInserire.getModalitaConsegnaScelta();
		String nota = offertaDaInserire.getNota();

		String inserisciOffertaAcquisto = "INSERT INTO Offerta_acquisto(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
				+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, "
				+ "Prezzo_offerto, Messaggio_motivazionale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		connessioneDB.setAutoCommit(false);
					
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
				
		
			psInserisciOffertaAcquisto.setString(10, modalitaConsegnaScelta);
			psInserisciOffertaAcquisto.setDouble(11, offertaDaInserire.getPrezzoOfferto());
			
			if(offertaDaInserire.getMessaggioMotivazionale() == null)
				psInserisciOffertaAcquisto.setNull(12, Types.VARCHAR);
			else
				psInserisciOffertaAcquisto.setString(12, offertaDaInserire.getMessaggioMotivazionale());
			
			psInserisciOffertaAcquisto.executeUpdate();
			
			ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
			utenteDAO.aggiornaSaldoUtente(offertaDaInserire.getUtenteProprietario(), -offertaDaInserire.getPrezzoOfferto());
			
			offertaDaInserire.getUtenteProprietario().aggiornaSaldo(-offertaDaInserire.getPrezzoOfferto());
		}
		catch(SQLException exc) {
			System.out.println(exc.getMessage());
			System.out.println(exc.getSQLState());
			exc.printStackTrace();
			throw exc;
		}
		finally {
			connessioneDB.setAutoCommit(true);
		}
	}

	@Override
	public void updateStatoOfferta(Offerta offerta, StatoOffertaEnum stato) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			ps.setString(1, stato.toString());
			ps.setString(2, offerta.getUtenteProprietario().getEmail());
			ps.setTimestamp(3, offerta.getMomentoProposta());
			
			ps.executeUpdate();
		}
	}
}
