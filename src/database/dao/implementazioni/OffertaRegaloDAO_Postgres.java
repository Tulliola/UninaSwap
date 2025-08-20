package database.dao.implementazioni;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.dao.interfacce.OffertaRegaloDAO;
import dto.Annuncio;
import dto.OffertaRegalo;
import dto.ProfiloUtente;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaRegaloDAO_Postgres implements OffertaRegaloDAO{

	private Connection connessioneDB;
	
	public OffertaRegaloDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public ArrayList<OffertaRegalo> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<OffertaRegalo> toReturn = new ArrayList<OffertaRegalo>();
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto where Email = ? AND Prezzo_offerto = 0")){
			ps.setString(1, utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					OffertaRegalo offertaToAdd;
					
					ProfiloUtente utenteOfferente = utenteLoggato;
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncioDAO.recuperaAnnuncioConId(rs.getInt("idAnnuncio"));
					
					offertaToAdd = new OffertaRegalo(utenteOfferente, momentoProposta, modConsegnaScelta, stato,
							annuncioRiferito);
					
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
	public ArrayList<OffertaRegalo> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException {
		ArrayList<OffertaRegalo> toReturn = new ArrayList<OffertaRegalo>();
		
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE idAnnuncio = ? AND Prezzo_offerto = 0")){
			ps.setInt(1, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					OffertaRegalo offertaToAdd;
					
					ProfiloUtente utenteOfferente = utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("Email"));
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum stato = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					Annuncio annuncioRiferito = annuncio;
					
					offertaToAdd = new OffertaRegalo(utenteOfferente, momentoProposta, modConsegnaScelta, stato,
							annuncioRiferito);
					
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
	public void inserisciOfferta(OffertaRegalo offertaDaInserire) throws SQLException {
		String inserisciOffertaRegalo = "INSERT INTO Offerta_acquisto(Email, idAnnuncio, idUfficio, Nota, Indirizzo_spedizione, "
				+ "Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro, Sede_incontro, Modalita_consegna_scelta, "
				+ "Prezzo_offerto, Messaggio_motivazionale) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		String emailOfferente = offertaDaInserire.getUtenteProprietario().getEmail();
		int idAnnuncioRiferito = offertaDaInserire.getAnnuncioRiferito().getIdAnnuncio();
		String modalitaConsegnaScelta = offertaDaInserire.getModalitaConsegnaScelta();
		String nota = offertaDaInserire.getNota();
		String messaggioMotivazionale = offertaDaInserire.getMessaggioMotivazionale();
		
		try(PreparedStatement psInserisciOffertaRegalo = connessioneDB.prepareStatement(inserisciOffertaRegalo)){
			psInserisciOffertaRegalo.setString(1, emailOfferente);
			psInserisciOffertaRegalo.setInt(2, idAnnuncioRiferito);
			
			if(modalitaConsegnaScelta.equals("Ritiro in posta"))
				psInserisciOffertaRegalo.setInt(3, offertaDaInserire.getUfficioRitiro().getIdUfficio());
			else
				psInserisciOffertaRegalo.setNull(3, Types.INTEGER);
			
			psInserisciOffertaRegalo.setString(4, nota);
			
			if(modalitaConsegnaScelta.equals("Spedizione"))
				psInserisciOffertaRegalo.setString(5, offertaDaInserire.getIndirizzoSpedizione());
			else
				psInserisciOffertaRegalo.setNull(5, Types.VARCHAR);
			
			if(modalitaConsegnaScelta.equals("Incontro")) {
				psInserisciOffertaRegalo.setString(6, offertaDaInserire.getOraInizioIncontro());
				psInserisciOffertaRegalo.setString(7, offertaDaInserire.getOraFineIncontro());
				psInserisciOffertaRegalo.setString(8, offertaDaInserire.getGiornoIncontro());
				psInserisciOffertaRegalo.setString(9, offertaDaInserire.getSedeDIncontroScelta().toString());
			}
			else {
				psInserisciOffertaRegalo.setNull(6, Types.VARCHAR);
				psInserisciOffertaRegalo.setNull(7, Types.VARCHAR);
				psInserisciOffertaRegalo.setNull(8, Types.VARCHAR);
				psInserisciOffertaRegalo.setNull(9, Types.VARCHAR);
			}
				
		
			psInserisciOffertaRegalo.setString(10, modalitaConsegnaScelta);;
			psInserisciOffertaRegalo.setDouble(11, 0);
			psInserisciOffertaRegalo.setString(12, offertaDaInserire.getMessaggioMotivazionale());
			
			psInserisciOffertaRegalo.executeUpdate();
		}
	}

	@Override
	public void updateStatoOfferta(OffertaRegalo offerta, StatoOffertaEnum stato) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Offerta_acquisto SET Stato = ? WHERE email = ? AND Momento_proposta = ?")){
			ps.setString(1, stato.toString());
			ps.setString(2, offerta.getUtenteProprietario().getEmail());
			ps.setTimestamp(3, offerta.getMomentoProposta());
			
			ps.executeUpdate();
		}
	}
}