package utilities;

import java.sql.SQLException;

public interface ImmagineDiSistemaDAO {
	public byte[][] recuperaImmaginiDiSistema() throws SQLException;
}
