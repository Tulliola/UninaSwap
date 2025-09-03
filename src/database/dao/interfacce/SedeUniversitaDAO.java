package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.SedeUniversita;

public interface SedeUniversitaDAO {
	public ArrayList<SedeUniversita> recuperaSediPresenti() throws SQLException;
	public SedeUniversita recuperaSedeDaId(int idSede) throws SQLException;
	public SedeUniversita recuperaSedeDaNome(String nomeSede) throws SQLException;
}
