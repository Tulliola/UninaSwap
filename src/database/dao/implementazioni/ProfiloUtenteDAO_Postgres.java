package database.dao.implementazioni;

import database.dao.interfacce.ProfiloUtenteDAO;
import dto.ProfiloUtente;
import eccezioni.*;

import java.sql.*;

public class ProfiloUtenteDAO_Postgres implements ProfiloUtenteDAO{
	private Connection connessioneDB = null;
	
	public ProfiloUtenteDAO_Postgres(Connection connInput) {
		this.connessioneDB = connInput;
	}

	@Override
	public ProfiloUtente recuperaUtenteConEmailOUsername(String emailOrUsername, String password) throws SQLException  {
		
		isUtenteExisting(emailOrUsername);
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Profilo_utente WHERE PW = ? AND (Email = ? OR Username = ?);")){
			ps.setString(1, password);
			ps.setString(2, emailOrUsername);
			ps.setString(3, emailOrUsername);
			
			try(ResultSet rs = ps.executeQuery()){
			
				isPasswordMatching(rs);
			
				return new ProfiloUtente(
						rs.getString("username"),
						rs.getString("email"),
						rs.getDouble("saldo"),
						rs.getString("residenza"),
						rs.getBytes("immagine_profilo")
				);
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
