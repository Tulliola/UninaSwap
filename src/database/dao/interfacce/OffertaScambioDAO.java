package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Offerta;

public interface OffertaScambioDAO {
	public void updateOfferta(Offerta offertaDaModificare, ArrayList<String> operazioniDaEseguire) throws SQLException;
}
