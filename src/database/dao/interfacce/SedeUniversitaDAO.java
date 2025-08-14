package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.SedeUniversita;

public interface SedeUniversitaDAO {
	public ArrayList<SedeUniversita> recuperaSediPresenti() throws SQLException;
}
