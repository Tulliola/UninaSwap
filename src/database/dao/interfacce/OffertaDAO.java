package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.Offerta;
import dto.ProfiloUtente;

public interface OffertaDAO {
	public ArrayList<Offerta> recuperaOfferteDiUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Connection connessioneDB, Annuncio annuncio) throws SQLException;
	public void inserisciOfferta(Connection connessioneDB, Offerta offertaDaInserire) throws SQLException;
	public void updateStatoOfferta(Connection connessioneDB, Offerta offerta) throws SQLException;
}

