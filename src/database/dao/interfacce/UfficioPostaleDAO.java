package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.UfficioPostale;

public interface UfficioPostaleDAO {
	public ArrayList<UfficioPostale> recuperaUfficiPostali() throws SQLException;
	public UfficioPostale recuperaUfficioPostaleConId(int idUfficio) throws SQLException;
}
