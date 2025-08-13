package database.dao.interfacce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Offerta;
import dto.OffertaScambio;
import dto.ProfiloUtente;

public interface OffertaScambioDAO {
	public OffertaScambio recuperaOffertaScambioDaId(int idOfferta) throws SQLException, IOException;
	public ArrayList<Offerta> recuperaOfferteScambioDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException;
	public ArrayList<Offerta> recuperaOfferteScambioNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException;
}
