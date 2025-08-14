package database.dao.interfacce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Offerta;

public interface OffertaDAO {
	public ArrayList<Offerta> recuperaOfferteDiUtente(String email) throws SQLException, IOException;
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(int idAnnuncio) throws SQLException, IOException;
	public void inserisciOfferta() throws SQLException;
}