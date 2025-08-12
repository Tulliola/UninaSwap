package database.dao.interfacce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.ProfiloUtente;

public interface AnnuncioDAO {
	public Annuncio recuperaAnnuncioDaID(int idAnnuncio) throws SQLException, IOException;
	public ArrayList<Annuncio> recuperaAnnunciDisponibiliDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException;
	public ArrayList<Annuncio> recuperaAnnunciDisponibiliNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException;
}
