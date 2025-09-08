package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import dto.Offerta;

public interface OffertaAcquistoDAO extends OffertaDAO{
	public void updateOfferta(Connection connessioneDB, Offerta offertaDaModificare) throws SQLException;
	public Double recuperaPrezzoOfferta(Connection connessioneDB, String email, Timestamp momentoProposta) throws SQLException;
}
