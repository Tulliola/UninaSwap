package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Offerta;

public interface OffertaScambioDAO {
	public void updateOfferta(Connection connessioneDB, Offerta offertaDaModificare, ArrayList<String> operazioniDaEseguire) throws SQLException;
}
