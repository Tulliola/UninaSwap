package utilities;

import java.sql.Connection;

import database.dao.implementazioni.OffertaRegaloDAO_Postgres;
import database.dao.implementazioni.OffertaScambioDAO_Postgres;
import database.dao.interfacce.OffertaDAO;
import database.dao.implementazioni.OffertaAcquistoDAO_Postgres;
import dto.Annuncio;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;

public class MapAnnuncioDAOToOffertaDAO {
	public static OffertaDAO getOffertaDAO(Annuncio annuncio, Connection connessioneDB) {
		if(annuncio instanceof AnnuncioVendita) {
			return new OffertaAcquistoDAO_Postgres();
		}
		else if(annuncio instanceof AnnuncioScambio) {
			return new OffertaScambioDAO_Postgres();
		}
		else {
			return new OffertaRegaloDAO_Postgres();
		}
	}
}
