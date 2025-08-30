package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Offerta;
import dto.Oggetto;

public interface OffertaScambioDAO {
	public void updateOfferta(Offerta offertaDaModificare, ArrayList<String> operazioniDaEseguire) throws SQLException;

}
