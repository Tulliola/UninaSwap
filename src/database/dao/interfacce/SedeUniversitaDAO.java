package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.SedeUniversita;

public interface SedeUniversitaDAO {
	public ArrayList<SedeUniversita> recuperaSediPresenti() throws SQLException;
	public SedeUniversita recuperaSedeDaId(int idSede) throws SQLException;
	public SedeUniversita recuperaSedeNome(String nomeSede) throws SQLException;
}
