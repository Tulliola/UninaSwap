package database.dao.interfacce;

import java.sql.SQLException;

import dto.Oggetto;

public interface ImmagineDAO {
	public void updateImmagine(Oggetto oggettoDiImmagine) throws SQLException;
}
