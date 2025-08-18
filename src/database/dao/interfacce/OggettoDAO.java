package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.Oggetto;

public interface OggettoDAO{
	public ArrayList<Oggetto> recuperaOggettiOffertiConIdOfferta(int idOfferta) throws SQLException;
	public Integer inserisciOggetto (Oggetto oggettoToAdd, String emailUtenteProprietario) throws SQLException;
	public byte[] recuperaPrimaImmagineDiOggetto(int idOggetto) throws SQLException;
	public Oggetto recuperaOggettoConId(int idOggetto) throws SQLException;
	public boolean isOggettoDisponibile(int idOggetto) throws SQLException;
	public byte[][] recuperaImmagini(int idOggetto) throws SQLException;
}
