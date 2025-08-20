package database.dao.interfacce;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Annuncio;
import dto.Offerta;
import dto.ProfiloUtente;

public interface OffertaDAO {
	public ArrayList<Offerta> recuperaOfferteDiUtente(ProfiloUtente utenteLoggato) throws SQLException;
	public ArrayList<Offerta> recuperaOfferteDiAnnuncio(Annuncio annuncio) throws SQLException;
	public void inserisciOfferta(Offerta offertaDaInserire) throws SQLException;
	public ArrayList<Offerta> recuperaOfferteAnnuncioRegalo(Annuncio annuncioRecuperato) throws SQLException;
	public ArrayList<Offerta> recuperaOfferteAnnuncioScambio(Annuncio annuncioRecuperato) throws SQLException;
	public ArrayList<Offerta> recuperaOfferteAnnuncioVendita(Annuncio annuncioRecuperato) throws SQLException;
	public void accettaOfferta(Offerta offertaToAccept) throws SQLException;
}

