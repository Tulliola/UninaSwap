package database.dao.interfacce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Offerta;
import dto.ProfiloUtente;

public interface OffertaAcquistoDAO {
	public ArrayList<Offerta> recuperaOfferteAcquistoDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException;
	public ArrayList<Offerta> recuperaOfferteAcquistoNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException;
}
