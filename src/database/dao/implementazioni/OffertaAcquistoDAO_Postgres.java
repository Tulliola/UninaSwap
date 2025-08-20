package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.sql.Types;

import java.util.ArrayList;

import database.dao.interfacce.OffertaAcquistoDAO;
import dto.Annuncio;

import dto.AnnuncioVendita;
import dto.Offerta;

import dto.OffertaAcquisto;
import dto.ProfiloUtente;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaAcquistoDAO_Postgres implements OffertaAcquistoDAO{

	private Connection connessioneDB;
	
	public OffertaAcquistoDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public ArrayList<OffertaAcquisto> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<OffertaAcquisto> toReturn = new ArrayList<OffertaAcquisto>();
		
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE email = ?")){
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
					
					toReturn.add(offertaToAdd);
				}
				return toReturn;
			}
		}
	}

	@Override
	public ArrayList<OffertaAcquisto> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException {
		ArrayList<OffertaAcquisto> toReturn = new ArrayList<OffertaAcquisto>();
		
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
	public void inserisciOfferta(OffertaAcquisto offertaDaInserire) throws SQLException {
		String emailOfferente = offertaDaInserire.getUtenteProprietario().getEmail();
		int idAnnuncioRiferito = offertaDaInserire.getAnnuncioRiferito().getIdAnnuncio();
		String modalitaConsegnaScelta = offertaDaInserire.getModalitaConsegnaScelta();
		String nota = offertaDaInserire.getNota();

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

	@Override
	public void updateStatoOfferta(OffertaAcquisto offerta, StatoOffertaEnum stato) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			ps.setString(1, stato.toString());
			ps.setString(2, offerta.getUtenteProprietario().getEmail());
			ps.setTimestamp(3, offerta.getMomentoProposta());
			
			ps.executeUpdate();
		}
	}
}
