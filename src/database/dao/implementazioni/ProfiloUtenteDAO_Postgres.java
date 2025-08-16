package database.dao.implementazioni;

import database.dao.interfacce.ProfiloUtenteDAO;
import dto.Annuncio;
import dto.ProfiloUtente;
import eccezioni.*;

import java.sql.*;
import java.util.ArrayList;

public class ProfiloUtenteDAO_Postgres implements ProfiloUtenteDAO{
	private Connection connessioneDB = null;
	private ProfiloUtente utenteLoggato;
	
	public ProfiloUtenteDAO_Postgres(Connection connInput, ProfiloUtente utenteIn) {
		this.connessioneDB = connInput;
		this.utenteLoggato = utenteIn;
	}

	//Metodi di inserimento

	@Override
	public void inserisciNuovoUtente(String usernameIn, String emailIn, String passwordIn, String residenzaIn) throws SQLException, MatricolaNonTrovataException{
		
		String matricolaDaEmail = this.recuperaMatricolaConEmail(emailIn);
		
		if(matricolaDaEmail != null) {
			String sqlQuery = "INSERT INTO Profilo_utente (Username, Matricola, Email, PW, Residenza) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)) {
				prepStat.setString(1, usernameIn);
				prepStat.setString(2, matricolaDaEmail);
				prepStat.setString(3, emailIn);
				prepStat.setString(4, passwordIn);
				prepStat.setString(5, residenzaIn);
				prepStat.executeUpdate();
			}
		}
		else
			throw new MatricolaNonTrovataException("Non è stata trovata alcuna matricola associata a questa email.");
	}
	
	//Metodi di aggiornamento
	
	@Override
	public void aggiornaUsernameUtente(String emailIn, String newUsername) throws SQLException {
		String sqlQuery = "UPDATE Profilo_utente SET Username = ? WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, newUsername);
			prepStat.setString(2, emailIn);
			prepStat.executeUpdate();
			
			utenteLoggato.setUsername(newUsername);
		}
	}
	
	@Override
	public void aggiornaPasswordUtente(String emailIn, String newPassword) throws SQLException {
		String sqlQuery = "UPDATE Profilo_utente SET PW = ? WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, newPassword);
			prepStat.setString(2, emailIn);
			prepStat.executeUpdate();
			
			utenteLoggato.setPassword(newPassword);
		}
	}
	
	@Override
	public void aggiornaResidenzaUtente(String emailIn, String newResidenza) throws SQLException {
		String sqlQuery = "UPDATE Profilo_utente SET Residenza = ? WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, newResidenza);
			prepStat.setString(2, emailIn);
			prepStat.executeUpdate();
			
			utenteLoggato.setResidenza(newResidenza);
		}
	}
	
	//Metodi di ricerca
	@Override
	public ProfiloUtente recuperaUtenteConEmailOUsername(String emailOrUsername, String password) throws SQLException  {
		
		isUtenteExisting(emailOrUsername);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Profilo_utente WHERE PW = ? AND (Email = ? OR Username = ?);")){
			ps.setString(1, password);
			ps.setString(2, emailOrUsername);
			ps.setString(3, emailOrUsername);
			
			try(ResultSet rs = ps.executeQuery()){
			
				isPasswordMatching(rs);
				
				ProfiloUtente profiloToReturn = new ProfiloUtente(
													rs.getString("username"),
													rs.getString("email"),
													rs.getDouble("saldo"),
													rs.getBytes("immagine_profilo"),
													rs.getString("residenza"),
													rs.getString("PW"),
													rs.getBoolean("sospeso")
				);
				
				AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
				ArrayList<Annuncio> annunciDiUtente = annuncioDAO.recuperaAnnunciDiUtente(profiloToReturn);
				
				for(Annuncio annuncioCorrente : annunciDiUtente)
					profiloToReturn.aggiungiAnnuncio(annuncioCorrente);
				
				return profiloToReturn;
			}
		}
	}


	@Override
	public String recuperaMatricolaConEmail(String emailIn) throws SQLException {
		String sqlQuery = "SELECT Matricola FROM Studente WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, emailIn);
			try(ResultSet resSet = prepStat.executeQuery()){
			
				if(resSet.next())
					return resSet.getString("Matricola");
			}
		}
		
		return null;
	}

	@Override
	public String[] recuperaMotiviSegnalazioni(String emailIn) throws SQLException {
		String sqlQuery = "SELECT motivo_segnalazione, data_segnalazione FROM SEGNALAZIONE WHERE Email_utente_segnalato = ? ORDER BY data_segnalazione DESC LIMIT 3";
		try(PreparedStatement ps = connessioneDB.prepareStatement(sqlQuery)){
			ps.setString(1, emailIn);
			try(ResultSet rs = ps.executeQuery()){
				String[] motiviSegnalazione = new String[3];
				
				for(int i = 0; i < 3; i++) {
					rs.next();
					motiviSegnalazione[i] = rs.getString("motivo_segnalazione");
				}
				
				return motiviSegnalazione;
			}
		}
		
	}
	
	@Override
	public String[] recuperaUtentiSegnalanti(String emailSegnalato) throws SQLException {
		String sqlQuery = "SELECT username FROM SEGNALAZIONE AS S JOIN PROFILO_UTENTE AS P ON S.email_utente_segnalante = P.Email WHERE email_utente_segnalato = ? ORDER BY data_segnalazione DESC LIMIT 3";
		
		try(PreparedStatement ps = connessioneDB.prepareStatement(sqlQuery)){
			ps.setString(1, emailSegnalato);
			try(ResultSet rs = ps.executeQuery()){
				String[] utentiSegnalanti = new String[3];
				
				for(int i = 0; i < 3; i++) {
					rs.next();
					utentiSegnalanti[i] = rs.getString("username");
				}
				
				return utentiSegnalanti;
			}
		}
	}
	
	//Metodi di utilità non ereditati dall'interfaccia
	
	private void isUtenteExisting(String emailOrUsername) throws SQLException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Profilo_utente WHERE (Email = ? OR Username = ?);")){
			ps.setString(1, emailOrUsername);
			ps.setString(2, emailOrUsername);
			
			ResultSet rs = ps.executeQuery();
			if(!(rs.next())){
				throw new UtenteNonTrovatoException("Utente non trovato");
			}
		}
	}
	
	private void isPasswordMatching(ResultSet rs) throws SQLException{
		if(!(rs.next()))
			throw new UtentePasswordMismatchException("L'email/username o la password sono errati");	
	}


}
