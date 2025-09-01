package database.dao.interfacce;

import java.sql.SQLException;

import dto.Offerta;

public interface OffertaRegaloDAO {
	public void updateOfferta(Offerta offertaDaModificare) throws SQLException;
}
