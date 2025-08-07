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
	public String recuperaMatricolaConEmail(String emailIn) throws SQLException {
		String sqlQuery = "SELECT Matricola FROM Studente WHERE Email = ?";
		try(PreparedStatement prepStat = connessioneDB.prepareStatement(sqlQuery)){
			prepStat.setString(1, emailIn);
			ResultSet resSet = prepStat.executeQuery();
			
			if(resSet.next())
				return resSet.getString("Matricola");
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
	
}
