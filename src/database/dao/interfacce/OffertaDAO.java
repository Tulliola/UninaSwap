package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.Offerta;
import dto.ProfiloUtente;

public interface OffertaDAO {
	public ArrayList<Offerta> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException;
	public void inserisciOfferta(Offerta offertaDaInserire) throws SQLException;
	public void updateStatoOfferta(Offerta offerta) throws SQLException;
}

