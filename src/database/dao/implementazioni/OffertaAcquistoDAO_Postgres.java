package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	public ArrayList<OffertaAcquisto> recuperaOfferteAnnuncioVendita(AnnuncioVendita annuncio) throws SQLException {
		ArrayList<OffertaAcquisto> toReturn = new ArrayList<OffertaAcquisto>();
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Offerta_acquisto WHERE idAnnuncio = ? AND Prezzo_iniziale > 0")){
			ps.setInt(1, annuncio.getIdAnnuncio());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {

					OffertaAcquisto offertaCorrente;
					ProfiloUtente offerente = utenteDAO.recuperaUtenteNonLoggatoConEmail(rs.getString("Email"));
					Timestamp momentoProposta = rs.getTimestamp("Momento_proposta");
					ModConsegnaEnum modalitaConsegnaScelta = ModConsegnaEnum.confrontaConStringa(rs.getString("Modalita_consegna_scelta"));
					StatoOffertaEnum statoOfferta = StatoOffertaEnum.confrontaConDB(rs.getString("Stato"));
					AnnuncioVendita annuncioRiferito = annuncio;
					double prezzoOfferto = rs.getDouble("Prezzo_offerto");
					
					offertaCorrente = new OffertaAcquisto(offerente, momentoProposta, modalitaConsegnaScelta, statoOfferta, annuncioRiferito, prezzoOfferto);
					
					toReturn.add(offertaCorrente);
				}
				
				return toReturn;
			}
		}
		
	}

}
