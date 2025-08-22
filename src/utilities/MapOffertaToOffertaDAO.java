package utilities;

import java.sql.Connection;

import database.dao.implementazioni.OffertaRegaloDAO_Postgres;
import database.dao.implementazioni.OffertaScambioDAO_Postgres;
import database.dao.interfacce.OffertaDAO;
import database.dao.implementazioni.OffertaAcquistoDAO_Postgres;
import dto.Annuncio;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.Offerta;
import dto.OffertaRegalo;
import dto.OffertaScambio;

public class MapOffertaToOffertaDAO {
	public static OffertaDAO getOffertaDAO(Offerta offerta, Connection connessioneDB) {
		if(offerta instanceof OffertaScambio) 
			return new OffertaScambioDAO_Postgres(connessioneDB);
		else if(offerta instanceof OffertaRegalo) 
			return new OffertaRegaloDAO_Postgres(connessioneDB);
		else 
			return new OffertaAcquistoDAO_Postgres(connessioneDB);
	}
}
