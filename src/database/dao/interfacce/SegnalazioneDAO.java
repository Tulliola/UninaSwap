package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;

public interface SegnalazioneDAO {
	public String[] recuperaUtentiSegnalanti(Connection connessioneDB, String emailSegnalato) throws SQLException;
	public String[] recuperaMotiviSegnalazioni(Connection connessioneDB, String emailIn) throws SQLException;
	public void inserisciSegnalazione(Connection connessioneDB, String emailSegnalante, String emailSegnalato, String motivoSegnalazione) throws SQLException;
}
