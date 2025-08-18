package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import database.dao.implementazioni.*;
import database.dao.interfacce.*;

//Import dal package GUI
import gui.*;
import utilities.MyJDialog;
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
	private FrameCaricaOggettoScambio[] frameCaricaOggetto = new FrameCaricaOggettoScambio[3];
	private DialogOffertaAcquisto dialogOffertaAcquisto;
	private DialogOffertaScambio dialogOffertaScambio;
	private DialogConfermaLogout dialogConfermaLogout;
	
	private static Connection connessioneDB;
	
	private ProfiloUtente utenteLoggato;
	private ArrayList<Annuncio> annunciInBacheca;
	private ArrayList<SedeUniversita> sediPresenti;
	private ArrayList<UfficioPostale> ufficiPresenti;
	
	public Controller() {
		this.definisciConnessioneAlDB();
		

		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);				
		
//		framePubblicaAnnuncio = new FramePubblicaAnnuncio(this, "Vendita", sediPresenti);
//		framePubblicaAnnuncio.setVisible(true);
		
//		ProfiloUtenteDAO_Postgres dao = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
//		try {
//			utenteLoggato = dao.recuperaUtenteConEmailOUsername("king_antonio", "killerpin");
//			frameProfiloUtente = new FrameProfiloUtente(this, "        Annunci disponibili", utenteLoggato);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		frameProfiloUtente.setVisible(true);
//		
//		try {
//			UfficioPostaleDAO_Postgres ufficiPostaliDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
//			this.ufficiPresenti = ufficiPostaliDAO.recuperaUfficiPostali();
//
//			ProfiloUtenteDAO_Postgres dao = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
//			utenteLoggato = dao.recuperaUtenteConEmailOUsernameEPassword("tulliola", "tullio33");
//			AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
//			annuncioDAO.recuperaAnnunciDiUtente(utenteLoggato);
//			dialogOffertaScambio = new DialogOffertaScambio(utenteLoggato.getAnnunciUtente().get(2), this);
//			dialogOffertaScambio.setVisible(true);
//		}
//		catch(SQLException exc) {
//
//			
//		}

//		frameHomePage = new FrameHomePage(this, utenteLoggato);
//		frameHomePage.setVisible(true);
	}

	static {
		UIManager.put("ToolTip.font", new Font("Ubuntu Sans", Font.BOLD, 16));
		UIManager.put("ToolTip.background", Color.white);
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

	
	// Metodi tornaA
	public void tornaALogin() {
		frameDiRegistrazione.dispose();
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);
	}
	
	public void tornaADialogOffertaScambio(JFrame frameDiScambioDiPartenza) {
		frameDiScambioDiPartenza.setVisible(false);
		dialogOffertaScambio.setVisible(true);
	}
	
	public void tornaADialogOffertaScambioEliminandoFrame(int indiceNellArrayDeiFrame) {
		frameCaricaOggetto[indiceNellArrayDeiFrame].dispose();
		dialogOffertaScambio.eliminaOggettoCaricato(indiceNellArrayDeiFrame);

		dialogOffertaScambio.setVisible(true);
	}
	
	
	// Metodi passaA
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
		if(frameHomePage.isVisible())
			frameHomePage.setVisible(false);
		frameProfiloUtente = new FrameProfiloUtente(this, sezioneSelezionata, utenteLoggato);
		frameProfiloUtente.setVisible(true);
	}
		
	public void passaAFrameHomePage(MyJFrame frameDiPartenza) {
		frameDiPartenza.dispose();
		frameHomePage = new FrameHomePage(this, utenteLoggato, annunciInBacheca);
		frameHomePage.setVisible(true);
		
	}
	
	public void passaAFrameHomePage(JDialog frameDiPartenza) {
		frameDiPartenza.dispose();
	}

	public void passaAFramePubblicaAnnuncio(String tipoAnnuncioDaPubblicare) {
		frameHomePage.dispose();
		framePubblicaAnnuncio = new FramePubblicaAnnuncio(this, tipoAnnuncioDaPubblicare, sediPresenti);
		framePubblicaAnnuncio.setVisible(true);
	}
	
	public void passaAFrameCaricaOggetto(int frameOggettoIesimo) {
		dialogOffertaScambio.setVisible(false);
		
		//Se sto creando l'oggetto per la prima volta o se ho chiamato la dispose sul frame
		if(frameCaricaOggetto[frameOggettoIesimo] == null || !frameCaricaOggetto[frameOggettoIesimo].isDisplayable())
			frameCaricaOggetto[frameOggettoIesimo] = new FrameCaricaOggettoScambio(this, frameOggettoIesimo);
		
		frameCaricaOggetto[frameOggettoIesimo].setVisible(true);
	}

	public void passaADialogDiComunicataSospensione(String emailUtente) throws SQLException {
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
		String[] motiviSegnalazioni = profiloDAO.recuperaMotiviSegnalazioni(utenteLoggato.getEmail());
		String[] utentiSegnalanti = profiloDAO.recuperaUtentiSegnalanti(utenteLoggato.getEmail());
		
		DialogDiComunicataSospensione comunicaSospensione = new DialogDiComunicataSospensione(frameDiLogin, utenteLoggato.getDataSospensione(), motiviSegnalazioni, true, utentiSegnalanti);
		comunicaSospensione.setVisible(true);
		frameDiLogin.dispose();
	}
	
	public void passaADialogOffertaAcquisto(Annuncio annuncioACuiOffrire) {
		dialogOffertaAcquisto = new DialogOffertaAcquisto(annuncioACuiOffrire, this);
		dialogOffertaAcquisto.setVisible(true);
	}
	
	public void passaADialogOffertaScambio(Annuncio annuncioACuiOffrire) {
		dialogOffertaScambio = new DialogOffertaScambio(annuncioACuiOffrire, this);
		dialogOffertaScambio.setVisible(true);
	}

	
	// Metodi onButtonClicked
	public void onAccessoButtonClicked(String email, String password) throws SQLException, IOException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
		utenteLoggato = profiloDAO.recuperaUtenteConEmailOUsernameEPassword(email, password);
		
		if(utenteLoggato.isSospeso())
			this.passaADialogDiComunicataSospensione(email);
		else {
			AnnuncioDAO_Postgres annunciDAO = new AnnuncioDAO_Postgres(connessioneDB);
			OffertaDAO_Postgres offerteDAO = new OffertaDAO_Postgres(connessioneDB);
			SedeUniversitaDAO_Postgres sediDAO = new SedeUniversitaDAO_Postgres(connessioneDB);
			UfficioPostaleDAO_Postgres ufficiPostaliDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
			
			this.utenteLoggato.setOfferteUtente(offerteDAO.recuperaOfferteDiUtente(utenteLoggato.getEmail()));
			this.utenteLoggato.setAnnunciUtente(annunciDAO.recuperaAnnunciDiUtente(utenteLoggato));
			this.ufficiPresenti = ufficiPostaliDAO.recuperaUfficiPostali();
			this.sediPresenti = sediDAO.recuperaSediPresenti();
			this.annunciInBacheca = annunciDAO.recuperaAnnunciInBacheca(utenteLoggato.getEmail());
			
			this.passaAFrameHomePage(frameDiLogin);
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
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB, newAnnuncio);
		annuncioDAO.inserisciAnnuncio(newAnnuncio);
		utenteLoggato.aggiungiAnnuncio(newAnnuncio);

		this.passaAFrameHomePage(framePubblicaAnnuncio);
	}
	
	public void onConfermaOffertaButtonClicked(Offerta offertaToAdd) throws SQLException {
		OffertaDAO_Postgres offertaDAO = new OffertaDAO_Postgres(connessioneDB);
		offertaDAO.inserisciOfferta(offertaToAdd);
		utenteLoggato.aggiungiOfferta(offertaToAdd);
		
		if(dialogOffertaAcquisto != null)
			dialogOffertaAcquisto.dispose();
		else if(dialogOffertaScambio != null)
			dialogOffertaScambio.dispose();
	}
	
	public void onCaricaOModificaOggettoButtonClicked(int indiceNellArrayDeiFrame, String nomeOggetto) {
		this.frameCaricaOggetto[indiceNellArrayDeiFrame].setVisible(false);
		dialogOffertaScambio.aggiungiOggettoCaricato(indiceNellArrayDeiFrame, nomeOggetto);

		dialogOffertaScambio.setVisible(true);
	}
	
	// Metodi di recupero
	
	public Oggetto recuperaOggettoDaFrame(int indiceDelFrame) {
		Oggetto oggettoRecuperato = frameCaricaOggetto[indiceDelFrame].passaOggettoAlController();
		
		return oggettoRecuperato;
	}
	
	//Getters
	public ProfiloUtente getUtenteLoggato() {
		return utenteLoggato;
	}
	
	public ArrayList<UfficioPostale> getUfficiPostali(){
		return ufficiPresenti;
	}


	public void logout() {
		frameProfiloUtente.dispose();
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);
	}


	public void passaADialogConfermaLogout() {
		this.dialogConfermaLogout = new DialogConfermaLogout(this, frameProfiloUtente);
		dialogConfermaLogout.setVisible(true);
	}
}