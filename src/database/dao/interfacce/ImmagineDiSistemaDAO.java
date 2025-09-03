package database.dao.interfacce;

import java.sql.SQLException;

public interface ImmagineDiSistemaDAO {
	public byte[][] recuperaImmaginiDiSistema() throws SQLException;
}
