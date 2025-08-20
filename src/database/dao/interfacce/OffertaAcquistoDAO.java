package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.AnnuncioVendita;
import dto.OffertaAcquisto;

public interface OffertaAcquistoDAO {
	public ArrayList<OffertaAcquisto> recuperaOfferteAnnuncioVendita(AnnuncioVendita annuncio) throws SQLException;
}
