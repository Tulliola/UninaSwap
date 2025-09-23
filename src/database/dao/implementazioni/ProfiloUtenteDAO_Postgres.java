package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.dao.interfacce.ProfiloUtenteDAO;
import dto.ProfiloUtente;
import eccezioni.MatricolaNonTrovataException;
import eccezioni.UtenteNonTrovatoException;
import eccezioni.UtentePasswordMismatchException;

public class ProfiloUtenteDAO_Postgres implements ProfiloUtenteDAO{

	//Metodi di inserimento

	@Override
	public void inserisciNuovoUtente(Connection connessioneDB, ProfiloUtente newUtente) throws SQLException, MatricolaNonTrovataException{
		
		String matricolaDaEmail = this.recuperaMatricolaConEmail(connessioneDB, newUtente.getEmail());
		
		if(matricolaDaEmail != null) {
			String sqlQuery = "INSERT INTO Profilo_utente (Username, Matricola, Email, PW, Residenza) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)) {
				prepStat.setString(1, newUtente.getUsername());
				prepStat.setString(2, matricolaDaEmail);
				prepStat.setString(3, newUtente.getEmail());
				prepStat.setString(4, newUtente.getPassword());
				prepStat.setString(5, newUtente.getResidenza());
				prepStat.executeUpdate();
			}
		}
		else
			throw new MatricolaNonTrovataException("Non è stata trovata alcuna matricola associata a questa email.");
	}
	
	//Metodi di aggiornamento
	
	@Override
	public void aggiornaUsernameUtente(Connection connessioneDB, ProfiloUtente utenteLoggato, String newUsername) throws SQLException {
		String sqlQuery = "UPDATE Profilo_utente SET Username = ? WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, newUsername);
			prepStat.setString(2, utenteLoggato.getEmail());
			prepStat.executeUpdate();
			
		}
	}
	
	@Override
	public void aggiornaPasswordUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException {
		String sqlQuery = "UPDATE Profilo_utente SET PW = ? WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, utenteLoggato.getPassword());
			prepStat.setString(2, utenteLoggato.getEmail());
			prepStat.executeUpdate();
		}
	}
	
	@Override
	public void aggiornaResidenzaUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException {
		String sqlQuery = "UPDATE Profilo_utente SET Residenza = ? WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, utenteLoggato.getResidenza());
			prepStat.setString(2, utenteLoggato.getEmail());
			prepStat.executeUpdate();
		}
	}
	
	@Override
	public void aggiornaBioPicUtente(Connection connessioneDB, ProfiloUtente utenteLoggato) throws SQLException {
		String aggiornaFoto = "UPDATE Profilo_utente SET Immagine_profilo = ? WHERE Email = ?";
		
		try(PreparedStatement psAggiornaFoto = connessioneDB.prepareStatement(aggiornaFoto)){
			psAggiornaFoto.setBytes(1, utenteLoggato.getImmagineProfilo());
			psAggiornaFoto.setString(2, utenteLoggato.getEmail());
			
			psAggiornaFoto.executeUpdate();
		}
	}
	
	//Metodi di ricerca
	@Override
	public ProfiloUtente recuperaUtenteConEmailOUsernameEPassword(Connection connessioneDB, String emailOrUsername, String password) throws SQLException  {
		
		isUtenteExisting(connessioneDB, emailOrUsername);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Profilo_utente WHERE PW = ? AND (Email = ? OR Username = ?)")){
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
				
				if(rs.getDate("Data_sospensione") != null)
					profiloToReturn.setDataSospensione(rs.getDate("Data_sospensione"));
				
				return profiloToReturn;
			}
		}
	}

	@Override
	public ProfiloUtente recuperaUtenteNonLoggatoConEmail(Connection connessioneDB, String email) throws SQLException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Profilo_utente WHERE Email = ?")){
			ps.setString(1, email);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					ProfiloUtente utenteToAdd = new ProfiloUtente(rs.getString("username"), rs.getString("Email"),
							rs.getDouble("Saldo"), rs.getBytes("immagine_profilo"), rs.getString("Residenza"),
							rs.getString("PW"), rs.getBoolean("sospeso"));
					return utenteToAdd;
				}
				
				return null;
			}
		}
	}
	
	@Override
	public String recuperaMatricolaConEmail(Connection connessioneDB, String emailIn) throws SQLException {
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
	public void aggiornaSaldoUtente(Connection connessioneDB, ProfiloUtente utente) throws SQLException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Profilo_utente SET Saldo = ? WHERE Email = ?")){
			ps.setDouble(1, utente.getSaldo());
			ps.setString(2, utente.getEmail());
						
			ps.executeUpdate();
		}
	}
	
	@Override
	public boolean isUtenteSospeso(Connection connessioneDB, ProfiloUtente utente) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT Sospeso FROM Profilo_utente WHERE Email = ?")){
			ps.setString(1, utente.getEmail());
						
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next())
					return rs.getBoolean("Sospeso");
				else
					return false;
			}
		}
	}
	
	//Metodi di utilità non ereditati dall'interfaccia
	
	private void isUtenteExisting(Connection connessioneDB, String emailOrUsername) throws SQLException{
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
