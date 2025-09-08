package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.UfficioPostale;

public interface UfficioPostaleDAO {
	public ArrayList<UfficioPostale> recuperaUfficiPostali(Connection connessioneDB) throws SQLException;
	public UfficioPostale recuperaUfficioPostaleConId(Connection connessioneDB, int idUfficio) throws SQLException;
	public Integer recuperaIdUfficio(Connection connessioneDB, String nome) throws SQLException;
}
