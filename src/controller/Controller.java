package controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import org.apache.log4j.Logger;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import database.dao.implementazioni.*;
import database.dao.interfacce.*;

//Import dal package GUI
import gui.*;
import utilities.MapOffertaToOffertaDAO;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;
//Import dal package DTO
import dto.*;

//Import dal package DAO
import database.DatabaseManager;
//Import dal package Eccezioni

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
	private DialogVisualizzaOggetti dialogVisualizzaOggetti;
	private DialogVisualizzaFoto dialogVisualizzaFoto;
	private DialogConfermaRitiroOfferta dialogConfermaRitiroOfferte;
	private DialogOffertaSpecificaUtente dialogOffertaSpecifica;
	private DialogConfermaRimozioneAnnuncio dialogConfermaRimozioneAnnuncio;
	private DialogCashout dialogCashout;
	private DialogOffertaAccettataAnnuncio dialogOffertaAccettataAnnuncio;
	private DialogDiAvvenutaRegistrazione dialogDiAvvenutaRegistrazione;
	private DialogDiComunicataSospensione dialogDiComunicataSospensione;
		
	private static Logger logger = Logger.getLogger(Controller.class);
	
	private Connection connessioneDB;
	
	private byte[][] immaginiDiSistema;
	
	private ProfiloUtente utenteLoggato;
	private ArrayList<Annuncio> annunciInBacheca;
	private ArrayList<SedeUniversita> sediPresenti;
	private ArrayList<UfficioPostale> ufficiPresenti;
	
	private ProfiloUtenteDAO utenteDAO;
	private SedeUniversitaDAO sedeDAO;
	private UfficioPostaleDAO ufficioDAO;
	private ImmagineDiSistemaDAO immagineDiSistemaDAO;
	private AnnuncioDAO annuncioDAO;
	private OggettoDAO oggettoDAO;
	private OffertaDAO offertaDAO;
	private IncontroDAO incontroDAO;
	private SegnalazioneDAO segnalazioneDAO;
	private ImmagineDAO immagineDAO;
	
	public Controller() {
		
		utenteDAO = new ProfiloUtenteDAO_Postgres();
		sedeDAO = new SedeUniversitaDAO_Postgres();
		ufficioDAO = new UfficioPostaleDAO_Postgres();
		immagineDiSistemaDAO = new ImmagineDiSistemaDAO_Postgres();
		immagineDAO = new ImmagineDAO_Postgres();
		annuncioDAO = new AnnuncioDAO_Postgres();
		oggettoDAO = new OggettoDAO_Postgres();
		incontroDAO = new IncontroDAO_Postgres();
		segnalazioneDAO = new SegnalazioneDAO_Postgres();
		
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);
			
	}

	static {
		UIManager.put("ToolTip.font", new Font("Ubuntu Sans", Font.BOLD, 16));
		UIManager.put("ToolTip.background", Color.white);
		 try {
	            UIManager.setLookAndFeel(new FlatMacLightLaf());
	            UIManager.put("TextComponent.arc", 20); 
	            UIManager.put("TextComponent.padding", new Insets(5, 10, 5, 10)); 

	        } catch (UnsupportedLookAndFeelException e) {
	            e.printStackTrace();
	        }
	}
	
	public static void main(String[] args) {
		new Controller();
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
		
		SwingUtilities.invokeLater(() -> {
			frameHomePage.setVisible(true);
		});	
	}
	
	public void passaAFrameHomePage(JDialog dialogDiPartenza) {
		for(FrameCaricaOggettoScambio frameCaricaOggetti: frameCaricaOggetto) {
			if(frameCaricaOggetti != null)
				frameCaricaOggetti.dispose();
		}
		dialogDiPartenza.dispose();
	}

	public void passaAFramePubblicaAnnuncio(String tipoAnnuncioDaPubblicare) {
		frameHomePage.setVisible(false);
		framePubblicaAnnuncio = new FramePubblicaAnnuncio(this, tipoAnnuncioDaPubblicare, sediPresenti);
		framePubblicaAnnuncio.setVisible(true);
	}
	
	public void passaAFrameCaricaOggetto(int frameOggettoIesimo, Oggetto oggettoCaricato) {
		if(dialogOffertaScambio != null)
			dialogOffertaScambio.setVisible(false);
		
		//Se sto creando l'oggetto per la prima volta o se ho chiamato la dispose sul frame
		if(frameCaricaOggetto[frameOggettoIesimo] == null || !frameCaricaOggetto[frameOggettoIesimo].isDisplayable())
			frameCaricaOggetto[frameOggettoIesimo] = new FrameCaricaOggettoScambio(this, frameOggettoIesimo, oggettoCaricato);
		
		frameCaricaOggetto[frameOggettoIesimo].setVisible(true);
	}

	public void passaADialogDiComunicataSospensione(String emailUtente) throws SQLException{
		String[] motiviSegnalazioni;
		String[] utentiSegnalanti;
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			
			motiviSegnalazioni = segnalazioneDAO.recuperaMotiviSegnalazioni(connessioneDB, utenteLoggato.getEmail());
			utentiSegnalanti = segnalazioneDAO.recuperaUtentiSegnalanti(connessioneDB, utenteLoggato.getEmail());
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
		
		dialogDiComunicataSospensione = new DialogDiComunicataSospensione(frameDiLogin, utenteLoggato.getDataSospensione(), motiviSegnalazioni, true, utentiSegnalanti);
		dialogDiComunicataSospensione.setVisible(true);
		
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
	
	public void passaADialogVisualizzaOggetti(ArrayList<Oggetto> oggettiDaMostrare) {
		dialogVisualizzaOggetti = new DialogVisualizzaOggetti(this, oggettiDaMostrare);
		dialogVisualizzaOggetti.setVisible(true);
	}
	
	public void passaADialogVisualizzaFoto(ArrayList<byte[]> immaginiDaMostrare) {
		dialogVisualizzaFoto = new DialogVisualizzaFoto(this, immaginiDaMostrare);
		dialogVisualizzaFoto.setVisible(true);
	}
	
	public void passaADialogConfermaLogout() {
		this.dialogConfermaLogout = new DialogConfermaLogout(this, frameProfiloUtente);
		dialogConfermaLogout.setVisible(true);
	}

	
	public void passaAFrameVisualizzaOfferte(ArrayList<Offerta> offerte) {
		frameProfiloUtente.setVisible(false);
		frameVisualizzaOfferteAnnuncio = new FrameVisualizzaOfferteAnnuncio(offerte, this);
		frameVisualizzaOfferteAnnuncio.setVisible(true);
	}

	public void passaADialogVersamento() {
		dialogVersamento = new DialogVersamento(this, utenteLoggato, frameProfiloUtente);
		dialogVersamento.setVisible(true);
	}	

	public void passaADialogConfermaRimozioneAnnuncio(Annuncio annuncioToRemove) {
		dialogConfermaRimozioneAnnuncio = new DialogConfermaRimozioneAnnuncio(this, frameProfiloUtente, annuncioToRemove);
		dialogConfermaRimozioneAnnuncio.setVisible(true);
	}
	

	public void passaADialogCashout() {
		dialogCashout = new DialogCashout(this, utenteLoggato, frameProfiloUtente);
		dialogCashout.setVisible(true);
	}


	public void passaADialogConfermaRitiroOfferta(Offerta offertaDaRitirare) {
		dialogConfermaRitiroOfferte = new DialogConfermaRitiroOfferta(this, frameProfiloUtente, offertaDaRitirare);
		dialogConfermaRitiroOfferte.setVisible(true);
	}
	
	

	public void passaADialogSegnalaUtente(Annuncio annuncio) {
		dialogSegnalaUtente = new DialogSegnalaUtente(this, frameHomePage, utenteLoggato, annuncio.getUtenteProprietario());
		dialogSegnalaUtente.setVisible(true);
	}


	public void passaADialogVisualizzaOffertaSpecificaUtente(Offerta offerta, Container container) {
		dialogOffertaSpecifica = new DialogOffertaSpecificaUtente(offerta, this, container);
		dialogOffertaSpecifica.setVisible(true);
	}


	public void passaADialogOffertaAccettataAnnuncio(Offerta offertaAccettata) {
		dialogOffertaAccettataAnnuncio = new DialogOffertaAccettataAnnuncio(offertaAccettata, this, frameProfiloUtente);
		dialogOffertaAccettataAnnuncio.setVisible(true);
	}


	public void passaADialogOffertaAcquistoDaModificare(Offerta offertaDaModificare) {
		dialogOffertaAcquisto = new DialogOffertaAcquisto(offertaDaModificare.getAnnuncioRiferito(), this, offertaDaModificare);
		dialogOffertaAcquisto.setVisible(true);
	}
	
	public void passaADialogOffertaScambioDaModificare(OffertaScambio offerta) {
		
		dialogOffertaScambio = new DialogOffertaScambio(offerta.getAnnuncioRiferito(), this, offerta);
		
		for(int i = 0; i < offerta.getOggettiOfferti().size(); i++)
			frameCaricaOggetto[i] = new FrameCaricaOggettoScambio(this, i, offerta.getOggettiOfferti().get(i));
		
		dialogOffertaScambio.setVisible(true);
	}


	public void passaADialogOffertaRegaloDaModificare(Offerta offerta) {
		dialogOffertaRegalo = new DialogOffertaRegalo(offerta.getAnnuncioRiferito(), this, offerta);
		dialogOffertaRegalo.setVisible(true);
	}
	
	// Metodi onButtonClicked
	public void onAccessoButtonClicked(String email, String password) throws SQLException{
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			
			this.impostaDatiPerUtenteLoggato(connessioneDB, email, password);
			
			if(utenteLoggato.isSospeso()) 
				this.passaADialogDiComunicataSospensione(email);
			else {
				
				this.ufficiPresenti = ufficioDAO.recuperaUfficiPostali(connessioneDB);
				this.sediPresenti = sedeDAO.recuperaSediPresenti(connessioneDB);
				this.immaginiDiSistema = immagineDiSistemaDAO.recuperaImmaginiDiSistema(connessioneDB);
				this.annunciInBacheca = annuncioDAO.recuperaAnnunciInBacheca(connessioneDB, utenteLoggato.getEmail());
				
				
				this.passaAFrameHomePage(frameDiLogin);
			}
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
	}
	
	public void onConfermaRegistrazioneButtonClicked(String usernameIn, String emailIn, String passwordIn, String residenzaIn) throws SQLException{
		ProfiloUtente nuovoUtente = new ProfiloUtente(usernameIn, emailIn, residenzaIn, passwordIn);
		
		try{
			connessioneDB = DatabaseManager.getConnection();
			utenteDAO.inserisciNuovoUtente(connessioneDB, nuovoUtente);
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
				
		dialogDiAvvenutaRegistrazione = new DialogDiAvvenutaRegistrazione(frameDiRegistrazione, "Ti diamo il benvenuto in UninaSwap!", true);
		dialogDiAvvenutaRegistrazione.setVisible(true);
		
		this.tornaALogin();
	}

	public void onConfermaCambiaImmagineButton(byte[] newBioPic) throws SQLException{
		utenteLoggato.setImmagineProfilo(newBioPic);
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			utenteDAO.aggiornaBioPicUtente(connessioneDB, utenteLoggato);
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
		
		dialogConfermaCambiaImmagine.dispose();
		frameCambiaImmagine.dispose();
		
		
		frameProfiloUtente = new FrameProfiloUtente(this,"   Il mio profilo", utenteLoggato);
		frameProfiloUtente.setVisible(true);
	}
	
	public void onSalvaModificheButtonClickedAggiornaUsername(String newUsername) throws SQLException{
		utenteLoggato.setUsername(newUsername);
		
		try{
			connessioneDB = DatabaseManager.getConnection();
			utenteDAO.aggiornaUsernameUtente(connessioneDB, utenteLoggato);
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
	}
	
	public void onSalvaModificheButtonClickedAggiornaPWD(String newPassword) throws SQLException{
		utenteLoggato.setPassword(newPassword);
		
		try{
			connessioneDB = DatabaseManager.getConnection();
			utenteDAO.aggiornaPasswordUtente(connessioneDB, utenteLoggato);
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
	}
	
	public void onSalvaModificheButtonClickedAggiornaResidenza(String newResidenza) throws SQLException{
		utenteLoggato.setResidenza(newResidenza);
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			utenteDAO.aggiornaResidenzaUtente(connessioneDB, utenteLoggato);			
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}

	}
	
	public void onPubblicaAnnuncioButtonClicked(Annuncio newAnnuncio) throws SQLException{
		try {
			connessioneDB = DatabaseManager.getConnection();
			connessioneDB.setAutoCommit(false);
			
			oggettoDAO.inserisciOggetto(connessioneDB, newAnnuncio.getOggettoInAnnuncio(), utenteLoggato.getEmail());
			immagineDAO.inserisciImmagini(connessioneDB, newAnnuncio.getOggettoInAnnuncio());
			annuncioDAO.inserisciAnnuncio(connessioneDB, newAnnuncio);
			
			if(newAnnuncio.isIncontro())
				for(int i = 0; i < newAnnuncio.getSedeIncontroProposte().size(); i++)					
					incontroDAO.inserisciIncontro(connessioneDB, newAnnuncio, i);
			
			connessioneDB.commit();
		}
		catch(SQLException exc) {
			logger.error(exc);
			connessioneDB.rollback();
			throw exc;
		}
		finally {
			if(connessioneDB != null) {
				connessioneDB.setAutoCommit(true);
				connessioneDB.close();
			}
		}
		
		utenteLoggato.aggiungiAnnuncio(newAnnuncio);
		
		this.passaAFrameHomePage(framePubblicaAnnuncio);
	}
	
	public void onConfermaOffertaButtonClicked(Offerta offertaToAdd) throws SQLException {
		offertaDAO = MapOffertaToOffertaDAO.getOffertaDAO(offertaToAdd, connessioneDB);

		try {
			connessioneDB = DatabaseManager.getConnection();
			
			connessioneDB.setAutoCommit(false);
			offertaDAO.inserisciOfferta(connessioneDB, offertaToAdd);
			
			utenteLoggato.aggiungiOfferta(offertaToAdd);	
			
			if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable()) {
				
				this.utenteLoggato.aggiornaSaldo(-offertaToAdd.getPrezzoOfferto());
				utenteDAO.aggiornaSaldoUtente(connessioneDB, this.utenteLoggato);
				
				dialogOffertaAcquisto.dispose();
			}
			
			connessioneDB.commit();
		}
		catch(SQLException exc) {
			connessioneDB.rollback();
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null) {
				connessioneDB.setAutoCommit(true);
				connessioneDB.close();
			}
		}
		
		if(dialogOffertaScambio != null && dialogOffertaScambio.isDisplayable()) {
			dialogOffertaScambio.dispose();
			for(FrameCaricaOggettoScambio frameCaricaOggetti: frameCaricaOggetto) {
				if(frameCaricaOggetti != null && frameCaricaOggetti.isDisplayable())
					frameCaricaOggetti.dispose();
			}
		}
		
		if(dialogOffertaRegalo != null && dialogOffertaRegalo.isDisplayable())
			dialogOffertaRegalo.dispose();
	}
	
	public void onCaricaOggettoButtonClicked(int indiceNellArrayDeiFrame, String nomeOggetto) {
		this.frameCaricaOggetto[indiceNellArrayDeiFrame].setVisible(false);
		
		dialogOffertaScambio.aggiungiOggettoCaricato(indiceNellArrayDeiFrame, nomeOggetto);
		dialogOffertaScambio.setVisible(true);
	}
	
	public void onModificaOggettoButtonClicked(int indiceNellArrayDeiFrame, String nomeOggetto) {
		this.frameCaricaOggetto[indiceNellArrayDeiFrame].setVisible(false);
		
		dialogOffertaScambio.modificaOggettoCaricato(indiceNellArrayDeiFrame, nomeOggetto);
		dialogOffertaScambio.setVisible(true);
	}
	
	public void onConfermaSegnalazioneButtonClicked(String emailSegnalante, String emailSegnalato, String motivoSegnalazione) throws SQLException{
		try {
			connessioneDB = DatabaseManager.getConnection();
			segnalazioneDAO.inserisciSegnalazione(connessioneDB, emailSegnalante, emailSegnalato, motivoSegnalazione);			
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
		
		dialogSegnalaUtente.dispose();
	}
	
	public void onModificaOffertaAcquistoButtonClicked(Offerta offertaModificata) throws SQLException {
		offertaDAO = new OffertaAcquistoDAO_Postgres();
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			
			double prezzoPrecedentementeOfferto = ((OffertaAcquistoDAO_Postgres)offertaDAO).recuperaPrezzoOfferta(connessioneDB, this.utenteLoggato.getEmail(), offertaModificata.getMomentoProposta());
			
			((OffertaAcquistoDAO_Postgres)offertaDAO).updateOfferta(connessioneDB, offertaModificata);		
			this.utenteLoggato.aggiornaSaldo(prezzoPrecedentementeOfferto - offertaModificata.getPrezzoOfferto());
			utenteDAO.aggiornaSaldoUtente(connessioneDB, this.utenteLoggato);			
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
		
		
		if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable())
			dialogOffertaAcquisto.dispose();
		
		if(dialogOffertaScambio != null && dialogOffertaScambio.isDisplayable())
			dialogOffertaScambio.dispose();
		
		if(dialogOffertaRegalo != null && dialogOffertaRegalo.isDisplayable())
			dialogOffertaRegalo.dispose();
		
		this.passaAFrameHomePage(frameProfiloUtente);
	}


	public void onModificaOffertaScambioButtonClicked(Offerta offertaDaModificare, ArrayList<String> operazioniDaEseguire) throws SQLException {
		offertaDAO = new OffertaScambioDAO_Postgres();
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			
			connessioneDB.setAutoCommit(false);
			((OffertaScambioDAO_Postgres)offertaDAO).updateOfferta(connessioneDB, offertaDaModificare, operazioniDaEseguire);
			
			connessioneDB.commit();
		}
		catch(SQLException exc) {
			logger.error(exc);
			connessioneDB.rollback();
			throw exc;
		}
		finally {
			if(connessioneDB != null) {
				connessioneDB.setAutoCommit(true);
				connessioneDB.close();
			}
		}
		
		if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable())
			dialogOffertaAcquisto.dispose();
		
		if(dialogOffertaScambio != null && dialogOffertaScambio.isDisplayable()) {
			dialogOffertaScambio.dispose();
			for(FrameCaricaOggettoScambio frameCaricaOggetti: frameCaricaOggetto) {
				if(frameCaricaOggetti != null && frameCaricaOggetti.isDisplayable())
					frameCaricaOggetti.dispose();
			}
		}
		
		if(dialogOffertaRegalo != null && dialogOffertaRegalo.isDisplayable())
			dialogOffertaRegalo.dispose();
	}

	public void onModificaOffertaRegaloButtonClicked(Offerta offertaDaModificare) throws SQLException {
		offertaDAO = new OffertaRegaloDAO_Postgres();
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			((OffertaRegaloDAO_Postgres)offertaDAO).updateOfferta(connessioneDB, offertaDaModificare);			
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
		finally {
			if(connessioneDB != null)
				connessioneDB.close();
		}
		
		if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable())
			dialogOffertaAcquisto.dispose();
		
		if(dialogOffertaScambio != null && dialogOffertaScambio.isDisplayable())
			dialogOffertaScambio.dispose();
		
		if(dialogOffertaRegalo != null && dialogOffertaRegalo.isDisplayable())
			dialogOffertaRegalo.dispose();
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


	

	public void aggiornaStatoOfferta(Offerta offerta, StatoOffertaEnum newStato) {
		offertaDAO = MapOffertaToOffertaDAO.getOffertaDAO(offerta, connessioneDB);
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			
			if(offerta instanceof OffertaAcquisto && (newStato.equals(StatoOffertaEnum.Accettata) || newStato.equals(StatoOffertaEnum.Ritirata)))
				utenteLoggato.aggiornaSaldo(offerta.getPrezzoOfferto());
			
			offerta.setStato(newStato);
			offertaDAO.updateStatoOfferta(connessioneDB, offerta);
			passaAFrameHomePage(frameProfiloUtente);
		} 
		catch (SQLException e) {
			logger.error(e);
		}
		finally {
			if(connessioneDB != null) {
				try {
					connessioneDB.close();					
				}
				catch(SQLException exc) {
					logger.error(exc);
				}
			}
		}
	}



	public void aggiornaSaldoUtente(double newImporto) {
		
		try {
			connessioneDB = DatabaseManager.getConnection();
			
			utenteLoggato.aggiornaSaldo(newImporto);
			utenteDAO.aggiornaSaldoUtente(connessioneDB, utenteLoggato);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connessioneDB != null) {
				try {
					connessioneDB.close();					
				}
				catch(SQLException exc) {
					logger.error(exc);
				}
			}
		}
	}


	public void aggiornaStatoAnnuncio(Annuncio annuncio, StatoAnnuncioEnum newStato) {
		try {
			connessioneDB = DatabaseManager.getConnection();
			
			annuncio.setStatoEnum(newStato);
			annuncioDAO.aggiornaStatoAnnuncio(connessioneDB, annuncio);
			
			passaAFrameHomePage(frameProfiloUtente);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(connessioneDB != null) {
				try {
					connessioneDB.close();					
				}
				catch(SQLException exc) {
					logger.error(exc);
				}
			}
		}
	}

	public void chiudiDialogConfermaLogout() {
		dialogConfermaLogout.dispose();
	}

	public void chiudiDialogVersamento(boolean haVersato) {
		dialogVersamento.dispose();
		if(haVersato)
			this.passaAFrameHomePage(frameProfiloUtente);
	}

	public void chiudDialogConfermaRimozioneAnnuncio(boolean prelevatoOVersato) {
		dialogConfermaRimozioneAnnuncio.dispose();
	}


	public void chiudiDialogCashout(boolean haPrelevato) {
		dialogCashout.dispose();
		if(haPrelevato)
			this.passaAFrameHomePage(frameProfiloUtente);
	}


	public void chiudiDialogConfermaRitiroOfferta() {
		dialogConfermaRitiroOfferte.dispose();
	}

	private void impostaDatiPerUtenteLoggato(Connection connessioneDB, String email, String password) throws SQLException{
		try {			
			annuncioDAO = new AnnuncioDAO_Postgres();
			
			utenteLoggato = utenteDAO.recuperaUtenteConEmailOUsernameEPassword(connessioneDB, email, password);
			utenteLoggato.setAnnunciUtente(annuncioDAO.recuperaAnnunciDiUtente(connessioneDB, utenteLoggato));
			
			for(Annuncio annuncioCorrente: utenteLoggato.getAnnunci()) {
				offertaDAO = new OffertaAcquistoDAO_Postgres();
				annuncioCorrente.aggiungiOfferte(offertaDAO.recuperaOfferteDiAnnuncio(connessioneDB, annuncioCorrente));
				
				offertaDAO = new OffertaScambioDAO_Postgres();
				annuncioCorrente.aggiungiOfferte(offertaDAO.recuperaOfferteDiAnnuncio(connessioneDB, annuncioCorrente));
				
				offertaDAO = new OffertaRegaloDAO_Postgres();
				annuncioCorrente.aggiungiOfferte(offertaDAO.recuperaOfferteDiAnnuncio(connessioneDB, annuncioCorrente));
			}
			
			offertaDAO = new OffertaAcquistoDAO_Postgres();
			utenteLoggato.aggiungiOfferte(offertaDAO.recuperaOfferteDiUtente(connessioneDB, utenteLoggato));
			
			offertaDAO = new OffertaScambioDAO_Postgres();
			utenteLoggato.aggiungiOfferte(offertaDAO.recuperaOfferteDiUtente(connessioneDB, utenteLoggato));
			
			offertaDAO = new OffertaRegaloDAO_Postgres();
			utenteLoggato.aggiungiOfferte(offertaDAO.recuperaOfferteDiUtente(connessioneDB, utenteLoggato));
		}
		catch(SQLException exc) {
			logger.error(exc);
			throw exc;
		}
	}
}