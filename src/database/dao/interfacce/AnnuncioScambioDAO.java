package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.AnnuncioScambio;
import dto.ProfiloUtente;

public interface AnnuncioScambioDAO {
	public AnnuncioScambio recuperaAnnuncioDaId(int idAnnuncio)throws SQLException;
	public ArrayList<AnnuncioScambio> recuperaAnnunciDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<AnnuncioScambio> recuperaAnnunciNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
}
