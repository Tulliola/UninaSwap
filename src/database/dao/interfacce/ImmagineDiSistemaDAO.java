package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;

public interface ImmagineDiSistemaDAO {
	public byte[][] recuperaImmaginiDiSistema(Connection connessioneDB) throws SQLException;
}
