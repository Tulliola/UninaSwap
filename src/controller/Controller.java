package controller;

import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import database.dao.implementazioni.*;
import database.dao.interfacce.*;

//Import dal package GUI
import gui.*;
import utilities.MyJFrame;
import utilities.MyJLabel;
//Import dal package DTO
import dto.*;

//Import dal package DAO
import database.DBConnection;
import database.dao.*;

//Import dal package Eccezioni
import eccezioni.*;


public class Controller {
	private FrameDiLogin frameDiLogin;
	private FrameDiRegistrazione frameDiRegistrazione;
	private FrameProfiloUtente frameProfiloUtente;
	private FrameCambiaImmagine frameCambiaImmagine;
	private FrameHomePage frameHomePage;
	private FramePubblicaAnnuncio framePubblicaAnnuncio;
	
	private static Connection connessioneDB;
	
	private ProfiloUtente utenteLoggato;
	private ArrayList<Annuncio> annunciNonDiUtente;
	
	public Controller() {
		this.definisciConnessioneAlDB();
		
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);		

//		framePubblicaAnnuncio = new FramePubblicaAnnuncio(this, "vendita");
//		framePubblicaAnnuncio.setVisible(true);
		
//		try {
//			ProfiloUtenteDAO_Postgres dao = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
//			utenteLoggato = dao.recuperaUtenteConEmailOUsername("king_antonio", "killerpin");
//		}
//		catch(SQLException exc) {
//			
//		}
//		frameHomePage = new FrameHomePage(this, utenteLoggato);
//		frameHomePage.setVisible(true);
	}

	
	public static void main(String[] args) {
		Controller mainController = new Controller();
	}

	
	private static void definisciConnessioneAlDB() {
		DBConnection dbConn = DBConnection.getConnessioneAlDB();
		
		try {
			connessioneDB = dbConn.connettitiTramiteSchema("uninaswap");
			
		}
		catch(NomeSchemaException exc1) {
			System.out.println(exc1.getMessage());
			
			try {
				connessioneDB.close();
			}
			catch(SQLException exc2) {
				exc2.printStackTrace();
			}
		}
		
		if(connessioneDB == null) {
			System.out.println("C'è stato un errore nella connessione al database.");
			System.exit(0);
		}
		else
			System.out.println("La connessione è stata definita con successo.");
		
	}
	
	public void tornaALogin() {
		frameDiRegistrazione.dispose();
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);
	}

	public void passaAFrameDiRegistrazione() {
		frameDiLogin.dispose();
		frameDiRegistrazione = new FrameDiRegistrazione(this);
		frameDiRegistrazione.setVisible(true);
	}

	public void passaAFrameCambiaImmagine() {
		frameProfiloUtente.setVisible(false);
		frameCambiaImmagine = new FrameCambiaImmagine(this);
		frameCambiaImmagine.setVisible(true);
	}

	public void passaASezioneInFrameProfiloUtente(String sezioneSelezionata) {
		frameHomePage.dispose();
		frameProfiloUtente = new FrameProfiloUtente(this, sezioneSelezionata, utenteLoggato);
		frameProfiloUtente.setVisible(true);
	}
		
	public void passaAHomePage(MyJFrame frameDiPartenza) {
		frameDiPartenza.dispose();
		frameHomePage = new FrameHomePage(this, utenteLoggato, annunciNonDiUtente);
		frameHomePage.setVisible(true);
		
	}

	public void passaAFramePubblicaAnnuncio(String tipoAnnuncioDaPubblicare) {
		frameHomePage.dispose();
		framePubblicaAnnuncio = new FramePubblicaAnnuncio(this, tipoAnnuncioDaPubblicare);
		framePubblicaAnnuncio.setVisible(true);
	}

	public void passaADialogDiComunicataSospensione(String emailUtente) throws SQLException {
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
		String[] motiviSegnalazioni = profiloDAO.recuperaMotiviSegnalazioni(utenteLoggato.getEmail());
		String[] utentiSegnalanti = profiloDAO.recuperaUtentiSegnalanti(utenteLoggato.getEmail());
		
		DialogDiComunicataSospensione comunicaSospensione = new DialogDiComunicataSospensione(frameDiLogin, utenteLoggato.getDataSospensione(), motiviSegnalazioni, true, utentiSegnalanti);
		comunicaSospensione.setVisible(true);
		frameDiLogin.dispose();
	}

	public void onAccessoButtonClicked(String email, String password) throws SQLException, IOException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
		utenteLoggato = profiloDAO.recuperaUtenteConEmailOUsername(email, password);
		
		if(utenteLoggato.isSospeso())
			this.passaADialogDiComunicataSospensione(email);
		else {
			AnnuncioDAO_Postgres annunciDAO = new AnnuncioDAO_Postgres(connessioneDB);
			OffertaScambioDAO_Postgres offerteDAO = new OffertaScambioDAO_Postgres(connessioneDB);
			this.annunciNonDiUtente = annunciDAO.recuperaAnnunciNonDiUtente(utenteLoggato);
			this.utenteLoggato.setAnnunciUtente(annunciDAO.recuperaAnnunciDiUtente(utenteLoggato));
			this.utenteLoggato.setOfferteUtente(offerteDAO.recuperaOfferteScambioDiUtente(utenteLoggato));
			this.passaAHomePage(frameDiLogin);
		}
	}
	
	public void onConfermaRegistrazioneButtonClicked(String usernameIn, String emailIn, String passwordIn, String residenzaIn) throws SQLException{
		ProfiloUtenteDAO_Postgres profiloInRegistrazione = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
		
		profiloInRegistrazione.inserisciNuovoUtente(usernameIn, emailIn, passwordIn, residenzaIn);
		
		DialogDiAvvenutaRegistrazione caricamentoTornaALoginFrame = new DialogDiAvvenutaRegistrazione(frameDiRegistrazione, "Ti diamo il benvenuto in UninaSwap!", true);
		caricamentoTornaALoginFrame.setVisible(true);
		
		this.tornaALogin();
	}
	
	public void onSalvaModificheButtonClickedAggiornaUsername(String newUsername) throws SQLException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, utenteLoggato);
		profiloDAO.aggiornaUsernameUtente(utenteLoggato.getEmail(), newUsername);
	}
	
	public void onSalvaModificheButtonClickedAggiornaPWD(String newPassword) throws SQLException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, utenteLoggato);
		profiloDAO.aggiornaPasswordUtente(utenteLoggato.getEmail(), newPassword);
	}
	
	public void onSalvaModificheButtonClickedAggiornaResidenza(String newResidenza) throws SQLException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, utenteLoggato);
		profiloDAO.aggiornaResidenzaUtente(utenteLoggato.getEmail(), newResidenza);
	}
	
	public void onPubblicaAnnuncioButtonClicked(Annuncio newAnnuncio) throws SQLException{
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
		annuncioDAO.inserisciAnnuncio(newAnnuncio);
		
//		try {	
//			Annuncio annuncioInserito = annuncioDAO.recuperaAnnuncioDaID(newAnnuncio.getIdAnnuncio());
//			
//			System.out.println(annuncioInserito.getNome()+"\n"+annuncioInserito.getOggettoInAnnuncio().getDescrizione());
//			utenteLoggato.aggiungiAnnuncio(newAnnuncio);
//		}
//		catch(SQLException | IOException throwables) {
//			throwables.printStackTrace();
//		}
	}
	
	public ProfiloUtente getUtenteLoggato() {
		return utenteLoggato;
	}
}