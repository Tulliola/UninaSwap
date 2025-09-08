package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;

import dto.Offerta;

public interface OffertaRegaloDAO {
	public void updateOfferta(Connection connessioneDB, Offerta offertaDaModificare) throws SQLException;
}
