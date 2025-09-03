package database.dao.interfacce;

import java.sql.SQLException;

import dto.ProfiloUtente;

public interface ProfiloUtenteDAO {
	//Metodi di inserimento
	public void inserisciNuovoUtente(ProfiloUtente newUtente) throws SQLException;
	public void inserisciSegnalazione(String emailSegnalante, String emailSegnalato, String motivoSegnalazione) throws SQLException;
	
	//Metodi di aggiornamento
	public void aggiornaUsernameUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaPasswordUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaResidenzaUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaBioPicUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaSaldoUtente(ProfiloUtente utenteLoggato) throws SQLException;
	
	//Metodi di eliminazione

	//Metodi di ricerca
	public ProfiloUtente recuperaUtenteConEmailOUsernameEPassword(String emailOUsername, String password) throws SQLException;
	public String recuperaMatricolaConEmail(String emailIn) throws SQLException;
	public String[] recuperaMotiviSegnalazioni(String emailIn) throws SQLException;
	public String[] recuperaUtentiSegnalanti(String emailSegnalato) throws SQLException;
	public ProfiloUtente recuperaUtenteNonLoggatoConEmail(String email) throws SQLException;
}
