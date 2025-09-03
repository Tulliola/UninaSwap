package database.dao.interfacce;

import java.sql.SQLException;
import java.sql.Timestamp;

import dto.Offerta;

public interface OffertaAcquistoDAO extends OffertaDAO{
	public void updateOfferta(Offerta offertaDaModificare) throws SQLException;
	public Double recuperaPrezzoOfferta(String email, Timestamp momentoProposta) throws SQLException;
}
