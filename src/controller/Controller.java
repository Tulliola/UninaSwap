package controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import database.dao.implementazioni.*;
import database.dao.interfacce.*;

//Import dal package GUI
import gui.*;
import utilities.ImmagineDiSistemaDAO;
import utilities.MapAnnuncioDAOToOffertaDAO;
import utilities.MyJDialog;
import utilities.MyJFrame;
import utilities.MyJLabel;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;
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
	private FrameVisualizzaOfferteAnnuncio frameVisualizzaOfferteAnnuncio;
	
	// Dialogs
	private DialogConfermaLogout dialogConfermaLogout;
	private DialogConfermaCambiaImmagine dialogConfermaCambiaImmagine;
	private DialogOffertaAcquisto dialogOffertaAcquisto;
	private DialogOffertaScambio dialogOffertaScambio;
	private DialogScegliOffertaRegalo dialogScegliOffertaRegalo;
	private DialogOffertaRegalo dialogOffertaRegalo;
	private DialogVersamento dialogVersamento;
	private DialogSegnalaUtente dialogSegnalaUtente;

	
	private static Connection connessioneDB;
	
	private byte[][] immaginiDiSistema;
	
	private ProfiloUtente utenteLoggato;
	private ArrayList<Annuncio> annunciInBacheca;
	private ArrayList<SedeUniversita> sediPresenti;
	private ArrayList<UfficioPostale> ufficiPresenti;
	private DialogConfermaRimozioneAnnuncio dialogConfermaRimozioneAnnuncio;
	private DialogCashout dialogCashout;
	
	public Controller() {
		this.definisciConnessioneAlDB();
		
//		dialogSegnalaUtente = new DialogSegnalaUtente(this);
//		dialogSegnalaUtente.setVisible(true);
		
//		PanelVisualizzaReport panel = new PanelVisualizzaReport();
//		panel.setVisible(true);
		
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);
			
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
	
	public void tornaAFrameCambiaImmagine(JDialog dialogDiPartenza) {
		dialogDiPartenza.dispose();
	}
	
	public void tornaAFrameProfiloUtente() {
		frameCambiaImmagine.dispose();
		frameProfiloUtente.setVisible(true);
	}
	
	
	// Metodi passaA
	public void passaAFrameDiRegistrazione() {
		frameDiLogin.dispose();
		frameDiRegistrazione = new FrameDiRegistrazione(this);
		frameDiRegistrazione.setVisible(true);
	}

	public void passaAFrameCambiaImmagine() {
		frameProfiloUtente.dispose();
		
		frameCambiaImmagine = new FrameCambiaImmagine(this, immaginiDiSistema);
		frameCambiaImmagine.setVisible(true);
	}

	public void passaASezioneInFrameProfiloUtente(String sezioneSelezionata) {
		frameHomePage.setVisible(false);
		if(frameVisualizzaOfferteAnnuncio != null && frameVisualizzaOfferteAnnuncio.isDisplayable()) {
			frameVisualizzaOfferteAnnuncio.dispose();
			frameVisualizzaOfferteAnnuncio = null;
		}
		frameProfiloUtente = new FrameProfiloUtente(this, sezioneSelezionata, utenteLoggato);
		frameProfiloUtente.setVisible(true);
	}
	

	public void passaADialogConfermaCambioImmagine(byte[] immagineProfilo) {
		dialogConfermaCambiaImmagine = new DialogConfermaCambiaImmagine(frameCambiaImmagine, this, immagineProfilo);
		dialogConfermaCambiaImmagine.setVisible(true);
	}
		
	public void passaAFrameHomePage(JFrame frameDiPartenza) {
		frameDiPartenza.dispose();
		
		if(frameHomePage == null || frameDiPartenza == this.frameDiLogin)
			frameHomePage = new FrameHomePage(this, utenteLoggato, annunciInBacheca);
		
		
//		JPanel panelCaricamento = new JPanel(new GridBagLayout());	
//		panelCaricamento.setOpaque(true);
//		panelCaricamento.setBackground(new Color(0, 0, 0, 150));
//		
//		JFrame frameCaricamento = new JFrame();
//		frameCaricamento.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//		frameCaricamento.add(panelCaricamento);
//		
//		MyJLabel lblCaricamento = new MyJLabel("Caricamento...");
//		lblCaricamento.setFont(new Font("Ubuntu Sans", Font.ITALIC, 15));
//		panelCaricamento.add(lblCaricamento);
//		
//		frameCaricamento.setVisible(true);
//		new Timer(1000, evento->{
//			frameCaricamento.dispose();
//			((Timer) evento.getSource()).stop();
//        }).start();

		frameHomePage.setVisible(true);
		
	}
	
	public void passaAFrameHomePage(JDialog frameDiPartenza) {
		frameDiPartenza.dispose();
	}

	public void passaAFramePubblicaAnnuncio(String tipoAnnuncioDaPubblicare) {
		frameHomePage.setVisible(false);
		framePubblicaAnnuncio = new FramePubblicaAnnuncio(this, tipoAnnuncioDaPubblicare, sediPresenti);
		framePubblicaAnnuncio.setVisible(true);
	}
	
	public void passaAFrameCaricaOggetto(int frameOggettoIesimo) {
		if(dialogOffertaScambio != null)
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
		if(dialogScegliOffertaRegalo != null)
			dialogScegliOffertaRegalo.dispose();
		
		dialogOffertaAcquisto = new DialogOffertaAcquisto(annuncioACuiOffrire, this);
		dialogOffertaAcquisto.setVisible(true);
	}
	
	public void passaADialogOffertaScambio(Annuncio annuncioACuiOffrire) {
		if(dialogScegliOffertaRegalo != null)
			dialogScegliOffertaRegalo.dispose();
		
		dialogOffertaScambio = new DialogOffertaScambio(annuncioACuiOffrire, this);
		dialogOffertaScambio.setVisible(true);
	}
	
	public void passaADialogScegliOffertaRegalo(Annuncio annuncio) {
		dialogScegliOffertaRegalo = new DialogScegliOffertaRegalo(this, frameHomePage, annuncio);
		dialogScegliOffertaRegalo.setVisible(true);
		
	}
	
	public void passaADialogOffertaRegalo(Annuncio annuncio) {
		dialogScegliOffertaRegalo.dispose();
		
		dialogOffertaRegalo = new DialogOffertaRegalo(annuncio, this);
		dialogOffertaRegalo.setVisible(true);
	}
	
	// Metodi onButtonClicked
	public void onAccessoButtonClicked(String email, String password) throws SQLException, IOException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, null);
		utenteLoggato = profiloDAO.recuperaUtenteConEmailOUsernameEPassword(email, password);
		
		if(utenteLoggato.isSospeso())
			this.passaADialogDiComunicataSospensione(email);
		else {
			AnnuncioDAO_Postgres annunciDAO = new AnnuncioDAO_Postgres(connessioneDB);
			
			SedeUniversitaDAO_Postgres sediDAO = new SedeUniversitaDAO_Postgres(connessioneDB);
			UfficioPostaleDAO_Postgres ufficiPostaliDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
			ImmagineDiSistemaDAO immaginiDiSistemaDAO = new ImmagineDiSistemaDAO(connessioneDB);
			
			this.ufficiPresenti = ufficiPostaliDAO.recuperaUfficiPostali();
			this.sediPresenti = sediDAO.recuperaSediPresenti();
			this.immaginiDiSistema = immaginiDiSistemaDAO.getImmaginiDiSistema();
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

	public void onConfermaCambiaImmagineButton(byte[] newBioPic) throws SQLException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB, utenteLoggato);
		profiloDAO.aggiornaBioPicUtente(utenteLoggato.getEmail(), newBioPic);
		
		dialogConfermaCambiaImmagine.dispose();
		frameCambiaImmagine.dispose();
		
		
		frameProfiloUtente = new FrameProfiloUtente(this,"   Il mio profilo", utenteLoggato);
		frameProfiloUtente.setVisible(true);
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
	
	public void onConfermaOffertaAcquistoButtonClicked(Offerta offertaToAdd) throws SQLException {
		OffertaAcquistoDAO_Postgres offertaDAO = new OffertaAcquistoDAO_Postgres(connessioneDB);
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		offertaDAO.inserisciOfferta(offertaToAdd);
		utenteLoggato.aggiungiOfferta(offertaToAdd);		
		
		utenteDAO.aggiornaSaldoUtente(utenteLoggato, -offertaToAdd.getPrezzoOfferto());
		utenteLoggato.aggiornaSaldo(-offertaToAdd.getPrezzoOfferto());

		if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable())
			dialogOffertaAcquisto.dispose();
		
	}
	
	public void onConfermaOffertaScambioButtonClicked(Offerta offertaToAdd) throws SQLException{
		OffertaScambioDAO_Postgres offertaDAO = new OffertaScambioDAO_Postgres(connessioneDB);
		offertaDAO.inserisciOfferta(offertaToAdd);
		utenteLoggato.aggiungiOfferta(offertaToAdd);
		
		if(dialogOffertaScambio != null && dialogOffertaScambio.isDisplayable())
			dialogOffertaScambio.dispose();
	}
	
	public void onConfermaOffertaRegaloButtonClicked(Offerta offertaToAdd) throws SQLException{
		OffertaRegaloDAO_Postgres offertaDAO = new OffertaRegaloDAO_Postgres(connessioneDB);
		offertaDAO.inserisciOfferta(offertaToAdd);
		
		if(dialogOffertaRegalo != null && dialogOffertaRegalo.isDisplayable())
			dialogOffertaRegalo.dispose();
	}
	
	public void onCaricaOModificaOggettoButtonClicked(int indiceNellArrayDeiFrame, String nomeOggetto) {
		this.frameCaricaOggetto[indiceNellArrayDeiFrame].setVisible(false);
		
		dialogOffertaScambio.aggiungiOggettoCaricato(indiceNellArrayDeiFrame, nomeOggetto);
		dialogOffertaScambio.setVisible(true);
	}
	
	public void onConfermaSegnalazioneButtonClicked(String emailSegnalante, String emailSegnalato, String motivoSegnalazione) throws SQLException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		profiloDAO.inserisciSegnalazione(emailSegnalante, emailSegnalato, motivoSegnalazione);
		
		dialogSegnalaUtente.dispose();
	}
	
	// Metodi di recupero
	
	public Oggetto recuperaOggettoDaFrameCaricaOggetto(int indiceDelFrame) {
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


	public void chiudiDialogConfermaLogout() {
		dialogConfermaLogout.dispose();
	}

	public void chiudiDialogVersamento(boolean hasVersato) {
		dialogVersamento.dispose();
		if(hasVersato)
			this.passaAFrameHomePage(frameProfiloUtente);
	}
	
	public void passaAFrameVisualizzaOfferte(ArrayList<Offerta> offerte) {
		frameProfiloUtente.setVisible(false);
		frameVisualizzaOfferteAnnuncio = new FrameVisualizzaOfferteAnnuncio(offerte, this);
		frameVisualizzaOfferteAnnuncio.setVisible(true);
	}


	public void aggiornaStatoOffertaAcquisto(OffertaAcquisto offerta, StatoOffertaEnum stato) {
		OffertaAcquistoDAO_Postgres offertaDAO = new OffertaAcquistoDAO_Postgres(connessioneDB);
		try {
			offertaDAO.updateStatoOfferta(offerta, stato);
			
			if(stato.equals(StatoOffertaEnum.Accettata)) {
				utenteLoggato.aggiornaSaldo(offerta.getPrezzoOfferto());
			}
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	public void passaADialogVersamento() {
		dialogVersamento = new DialogVersamento(this, utenteLoggato, frameProfiloUtente);
		dialogVersamento.setVisible(true);
	}


	public void aggiornaSaldoUtente(double importo) {
		ProfiloUtenteDAO_Postgres utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		
		try {
			utenteDAO.aggiornaSaldoUtente(utenteLoggato, importo);
			utenteLoggato.aggiornaSaldo(importo);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void passaADialogConfermaRimozioneAnnuncio(Annuncio annuncioToRemove) {
		dialogConfermaRimozioneAnnuncio = new DialogConfermaRimozioneAnnuncio(this, frameProfiloUtente, annuncioToRemove);
		dialogConfermaRimozioneAnnuncio.setVisible(true);
	}


	public void chiudDialogConfermaRimozioneAnnuncio(boolean prelevatoOVersato) {
		dialogConfermaRimozioneAnnuncio.dispose();
	}


	public void aggiornaStatoAnnuncio(Annuncio annuncio, StatoAnnuncioEnum stato) {
		AnnuncioDAO_Postgres annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
		try {
			annuncioDAO.aggiornaStatoAnnuncio(annuncio, stato);
			annuncio.setStatoEnum(stato);
			passaAFrameHomePage(frameProfiloUtente);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void passaADialogCashout() {
		dialogCashout = new DialogCashout(this, utenteLoggato, frameProfiloUtente);
		dialogCashout.setVisible(true);
	}


	public void chiudiDialogCashout(boolean hasPrelevato) {
		dialogCashout.dispose();
		if(hasPrelevato)
			this.passaAFrameHomePage(frameProfiloUtente);
	}
}