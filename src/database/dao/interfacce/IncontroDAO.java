package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;

import dto.Annuncio;

public interface IncontroDAO {
	public void inserisciIncontro(Connection connessioneDB, Annuncio annuncio, int iEsimoIncontro) throws SQLException;
}
