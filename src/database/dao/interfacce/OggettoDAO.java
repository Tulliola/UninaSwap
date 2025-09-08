package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Oggetto;

public interface OggettoDAO{
	public ArrayList<Oggetto> recuperaOggettiOffertiConIdOfferta(Connection connessioneDB, int idOfferta) throws SQLException;
	public Integer inserisciOggetto (Connection connessioneDB, Oggetto oggettoToAdd, String emailUtenteProprietario) throws SQLException;
	public Oggetto recuperaOggettoConId(Connection connessioneDB, int idOggetto) throws SQLException;
	public void deleteOggetto(Connection connessioneDB, int idOggetto) throws SQLException;
	public void updateOggetto(Connection connessioneDB, Oggetto oggettoDaModificare) throws SQLException;
}
