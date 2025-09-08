package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.ProfiloUtente;

public interface AnnuncioDAO {
	public Annuncio recuperaAnnuncioConId(Connection connessioneDB, int idAnnuncio) throws SQLException;
	public ArrayList<Annuncio> recuperaAnnunciDiUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<Annuncio> recuperaAnnunciInBacheca(Connection connessioneDB, String emailUtenteLoggato) throws SQLException;
	public void inserisciAnnuncio(Connection connessioneDB, Annuncio annuncioDaInserire) throws SQLException;
	public void aggiornaStatoAnnuncio(Connection connessioneDB, Annuncio annuncio) throws SQLException;
}
