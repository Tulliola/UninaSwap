package database.dao.interfacce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.ProfiloUtente;

public interface AnnuncioDAO {
	public Annuncio recuperaAnnuncioConId(int idAnnuncio) throws SQLException;
	public ArrayList<Annuncio> recuperaAnnunciDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<Annuncio> recuperaAnnunciInBacheca(String emailUtenteLoggato) throws SQLException;
	public void inserisciAnnuncio(Annuncio annuncioDaInserire) throws SQLException;
}
