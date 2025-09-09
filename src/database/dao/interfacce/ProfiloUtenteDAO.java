package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;

import dto.ProfiloUtente;

public interface ProfiloUtenteDAO {
	//Metodi di inserimento
	public void inserisciNuovoUtente(Connection connessioneDB, ProfiloUtente newUtente) throws SQLException;
	
	//Metodi di aggiornamento
	public void aggiornaUsernameUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaPasswordUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaResidenzaUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaBioPicUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException;
	public void aggiornaSaldoUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException;
	
	//Metodi di eliminazione

	//Metodi di ricerca
	public ProfiloUtente recuperaUtenteConEmailOUsernameEPassword(Connection connessioneDB, String emailOUsername, String password) throws SQLException;
	public String recuperaMatricolaConEmail(Connection connessioneDB, String emailIn) throws SQLException;
	public ProfiloUtente recuperaUtenteNonLoggatoConEmail(Connection connessioneDB, String email) throws SQLException;
	public boolean isUtenteSospeso(Connection connessioneDB, ProfiloUtente utente) throws SQLException;

}
