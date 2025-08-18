package database.dao.interfacce;

import java.sql.SQLException;

import dto.ProfiloUtente;

public interface ProfiloUtenteDAO {
	//Metodi di inserimento
	public void inserisciNuovoUtente(String usernameIn, String emailIn, String passwordIn, String residenzaIn) throws SQLException;

	//Metodi di aggiornamento
	public void aggiornaUsernameUtente(String emailIn, String newUsername) throws SQLException;
	public void aggiornaPasswordUtente(String emailIn, String newPassword) throws SQLException;
	public void aggiornaResidenzaUtente(String emailIn, String newResidenza) throws SQLException;
	
	//Metodi di eliminazione

	//Metodi di ricerca
	public ProfiloUtente recuperaUtenteConEmailOUsernameEPassword(String email, String password) throws SQLException;
	public String recuperaMatricolaConEmail(String emailIn) throws SQLException;
	public String[] recuperaMotiviSegnalazioni(String emailIn) throws SQLException;
	public String[] recuperaUtentiSegnalanti(String emailSegnalato) throws SQLException;
	public ProfiloUtente recuperaUtenteConEmail(String email) throws SQLException;
}
