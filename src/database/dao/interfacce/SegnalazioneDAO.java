package database.dao.interfacce;

import java.sql.Connection;
import java.sql.SQLException;

import dto.ProfiloUtente;

public interface SegnalazioneDAO {
	public String[] recuperaUtentiSegnalanti(Connection connessioneDB, String emailSegnalato) throws SQLException;
	public String[] recuperaMotiviSegnalazioni(Connection connessioneDB, String emailIn) throws SQLException;
	public void inserisciSegnalazione(Connection connessioneDB, String emailSegnalante, ProfiloUtente utenteSegnalato, String motivoSegnalazione) throws SQLException;
}
