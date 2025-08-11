package database.dao.interfacce;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.AnnuncioVendita;
import dto.ProfiloUtente;

public interface AnnuncioVenditaDAO {
	public AnnuncioVendita recuperaAnnuncioDaId(int idAnnuncio)throws SQLException;
	public ArrayList<AnnuncioVendita> recuperaAnnunciDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<AnnuncioVendita> recuperaAnnunciNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
}
