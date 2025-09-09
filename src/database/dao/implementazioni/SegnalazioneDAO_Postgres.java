package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.dao.interfacce.SegnalazioneDAO;
import dto.ProfiloUtente;

public class SegnalazioneDAO_Postgres implements SegnalazioneDAO {

	@Override
	public String[] recuperaUtentiSegnalanti(Connection connessioneDB, String emailSegnalato) throws SQLException {
		String sqlQuery = "SELECT username FROM SEGNALAZIONE AS S JOIN PROFILO_UTENTE AS P ON S.email_utente_segnalante = P.Email WHERE email_utente_segnalato = ? ORDER BY data_segnalazione DESC LIMIT 3";
		
		try(PreparedStatement ps = connessioneDB.prepareStatement(sqlQuery)){
			ps.setString(1, emailSegnalato);
			try(ResultSet rs = ps.executeQuery()){
				String[] utentiSegnalanti = new String[3];
				
				for(int i = 0; i < 3; i++) {
					if(rs.next())
						utentiSegnalanti[i] = rs.getString("username");
				}
				
				return utentiSegnalanti;
			}
		}
	}

	@Override
	public String[] recuperaMotiviSegnalazioni(Connection connessioneDB, String emailIn) throws SQLException {
		String sqlQuery = "SELECT motivo_segnalazione, data_segnalazione FROM SEGNALAZIONE WHERE Email_utente_segnalato = ? ORDER BY data_segnalazione DESC LIMIT 3";
		try(PreparedStatement ps = connessioneDB.prepareStatement(sqlQuery)){
			ps.setString(1, emailIn);
			try(ResultSet rs = ps.executeQuery()){
				String[] motiviSegnalazione = new String[3];
				
				for(int i = 0; i < 3; i++) {
					if(rs.next())
						motiviSegnalazione[i] = rs.getString("motivo_segnalazione");
				}
				
				return motiviSegnalazione;
			}
		}
		
	}

	@Override
	public void inserisciSegnalazione(Connection connessioneDB, String emailSegnalante, ProfiloUtente utenteSegnalato, String motivoSegnalazione) throws SQLException {
		
		String inserisciSegnalazione = "INSERT INTO Segnalazione (Email_utente_segnalante, Email_utente_segnalato, Motivo_segnalazione) VALUES (?, ?, ?)";
		
		try(PreparedStatement psInserisciSegnalazione = connessioneDB.prepareStatement(inserisciSegnalazione)){
			psInserisciSegnalazione.setString(1, emailSegnalante);
			psInserisciSegnalazione.setString(2, utenteSegnalato.getEmail());
			psInserisciSegnalazione.setString(3, motivoSegnalazione);
			
			psInserisciSegnalazione.executeUpdate();	
		}
	}

}
