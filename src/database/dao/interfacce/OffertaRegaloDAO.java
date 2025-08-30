package database.dao.interfacce;

import java.sql.SQLException;

import dto.Offerta;

public interface OffertaRegaloDAO {
	public Offerta updateOfferta(Offerta offertaDaModificare) throws SQLException;
}
