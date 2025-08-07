package database.dao.interfacce;

import java.sql.SQLException;

import dto.ProfiloUtente;

public interface ProfiloUtenteDAO {
//	public void inserisciNuovoUtente(ProfiloUtente profiloDaInserire);
	public ProfiloUtente recuperaUtenteConEmail(String email, String password) throws SQLException;
}
