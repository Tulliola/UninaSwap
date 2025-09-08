package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.SedeUniversita;

public interface SedeUniversitaDAO {
	public ArrayList<SedeUniversita> recuperaSediPresenti(Connection connessioneDB) throws SQLException;
	public SedeUniversita recuperaSedeDaId(Connection connessioneDB, int idSede) throws SQLException;
	public SedeUniversita recuperaSedeDaNome(Connection connessioneDB, String nomeSede) throws SQLException;
}
