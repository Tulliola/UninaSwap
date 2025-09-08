package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;

import dto.Oggetto;

public interface ImmagineDAO {
	public void inserisciImmagini(Connection connessioneDB, Oggetto oggettoConImmagini) throws SQLException;
	public byte[][] recuperaImmagini(Connection connessioneDB, int idOggetto) throws SQLException;
	public void deleteImmaginiDiOggetto(Connection connessioneDB, int idOggetto) throws SQLException;
}
