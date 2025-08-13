package database.dao.interfacce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Offerta;

public interface OffertaDAO {
	public ArrayList<Offerta> offerteDiUtente(String email) throws SQLException, IOException;
	public ArrayList<Offerta> offerteDiAnnuncio(int idAnnuncio) throws SQLException, IOException;
}
