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
	public ProfiloUtente recuperaUtenteConEmail(String emailOrUsername, String password) throws SQLException{
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM Profilo_utente WHERE PW = ? AND (Email = ? OR Username = ?);")){
				ps.setString(1, password);
				ps.setString(2, emailOrUsername);
				ps.setString(3, emailOrUsername);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					return new ProfiloUtente(
						rs.getString("username"),
						rs.getString("email"),
						rs.getDouble("saldo"),
						rs.getString("residenza"),
						rs.getBytes("immagine_profilo")
					);
				}
				else
					throw new UtenteNonTrovatoException("Utente non trovato");
		}
	}
}
