package database.dao.interfacce;

import java.sql.SQLException;

import dto.ProfiloUtente;

public interface ProfiloUtenteDAO {
	public ProfiloUtente recuperaUtenteConEmail(String email, String password) throws SQLException;
	public void inserisciNuovoUtente(String usernameIn, String emailIn, String passwordIn, String residenzaIn) throws SQLException;
	public String recuperaMatricolaConEmail(String emailIn) throws SQLException;
}
