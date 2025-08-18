package database.dao.interfacce;

import java.sql.SQLException;

import dto.Oggetto;

public interface OggettoDAO{
	
	public Integer inserisciOggetto (Oggetto oggettoToAdd, String emailUtenteProprietario) throws SQLException;
	public byte[] recuperaPrimaImmagineDiOggetto(int idOggetto) throws SQLException;
	public Oggetto recuperaOggettoConId(int idOggetto) throws SQLException;
	
}
