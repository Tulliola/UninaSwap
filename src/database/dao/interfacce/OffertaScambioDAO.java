package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.OffertaScambio;
import dto.ProfiloUtente;
import utilities.StatoOffertaEnum;

public interface OffertaScambioDAO {
	public ArrayList<OffertaScambio> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<OffertaScambio> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException;
	public void inserisciOfferta(OffertaScambio offertaDaInserire) throws SQLException;
	public void updateStatoOfferta(OffertaScambio offerta, StatoOffertaEnum stato) throws SQLException;
}
