package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.OffertaAcquisto;
import dto.OffertaRegalo;
import dto.ProfiloUtente;
import utilities.StatoOffertaEnum;

public interface OffertaRegaloDAO {
	public ArrayList<OffertaRegalo> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<OffertaRegalo> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException;
	public void inserisciOfferta(OffertaRegalo offertaDaInserire) throws SQLException;
	public void updateStatoOfferta(OffertaRegalo offerta, StatoOffertaEnum stato) throws SQLException;
}
