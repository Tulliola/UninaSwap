package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.Offerta;
import dto.OffertaAcquisto;
import dto.ProfiloUtente;
import utilities.StatoOffertaEnum;

public interface OffertaAcquistoDAO {
	public ArrayList<OffertaAcquisto> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<OffertaAcquisto> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException;
	public void inserisciOfferta(OffertaAcquisto offertaDaInserire) throws SQLException;
	public void updateStatoOfferta(OffertaAcquisto offerta, StatoOffertaEnum stato) throws SQLException;
}
