package controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import database.dao.implementazioni.*;
import database.dao.interfacce.*;

//Import dal package GUI
import gui.*;
import utilities.ImmagineDiSistemaDAO;
import utilities.MapOffertaToOffertaDAO;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;
//Import dal package DTO
import dto.*;

//Import dal package DAO
import database.DBConnection;

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
	private DialogVisualizzaOggetti dialogVisualizzaOggetti;
	private DialogVisualizzaFoto dialogVisualizzaFoto;
	private DialogConfermaRitiroOfferta dialogConfermaRitiroOfferte;
	private DialogOffertaSpecificaUtente dialogOffertaSpecifica;
	private DialogConfermaRimozioneAnnuncio dialogConfermaRimozioneAnnuncio;
	private DialogCashout dialogCashout;
	private DialogOffertaAccettataAnnuncio dialogOffertaAccettataAnnuncio;
	
	private static Connection connessioneDB;
	
	private byte[][] immaginiDiSistema;
	
	private ProfiloUtente utenteLoggato;
	private ArrayList<Annuncio> annunciInBacheca;
	private ArrayList<SedeUniversita> sediPresenti;
	private ArrayList<UfficioPostale> ufficiPresenti;
	
	private ProfiloUtenteDAO_Postgres utenteDAO;
	private SedeUniversitaDAO_Postgres sedeDAO;
	private UfficioPostaleDAO_Postgres ufficioDAO;
	private ImmagineDiSistemaDAO immagineDAO;
	private AnnuncioDAO_Postgres annuncioDAO;
	private OffertaDAO offertaDAO;
	
	public Controller() {
		this.definisciConnessioneAlDB();
		
		utenteDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		
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
		
		SwingUtilities.invokeLater(() -> {
			frameHomePage.setVisible(true);
		});

		
	}
	
	public void passaAFrameHomePage(JDialog frameDiPartenza) {
		for(FrameCaricaOggettoScambio frameCaricaOggetti: frameCaricaOggetto) {
			if(frameCaricaOggetti != null)
				frameCaricaOggetti.dispose();
		}
		frameDiPartenza.dispose();
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

	public void passaADialogDiComunicataSospensione(String emailUtente) throws SQLException {
		String[] motiviSegnalazioni = utenteDAO.recuperaMotiviSegnalazioni(utenteLoggato.getEmail());
		String[] utentiSegnalanti = utenteDAO.recuperaUtentiSegnalanti(utenteLoggato.getEmail());
		
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
	
	public void passaADialogVisualizzaOggetti(ArrayList<Oggetto> oggettiDaMostrare) {
		dialogVisualizzaOggetti = new DialogVisualizzaOggetti(this, oggettiDaMostrare);
		dialogVisualizzaOggetti.setVisible(true);
	}
	
	public void passaADialogVisualizzaFoto(ArrayList<byte[]> immaginiDaMostrare) {
		dialogVisualizzaFoto = new DialogVisualizzaFoto(this, immaginiDaMostrare);
		dialogVisualizzaFoto.setVisible(true);
	}
	
	// Metodi onButtonClicked
	public void onAccessoButtonClicked(String email, String password) throws SQLException, IOException{
		utenteLoggato = utenteDAO.recuperaUtenteConEmailOUsernameEPassword(email, password);
		
		if(utenteLoggato.isSospeso())
			this.passaADialogDiComunicataSospensione(email);
		else {
			annuncioDAO = new AnnuncioDAO_Postgres(connessioneDB);
			sedeDAO = new SedeUniversitaDAO_Postgres(connessioneDB);
			ufficioDAO = new UfficioPostaleDAO_Postgres(connessioneDB);
			immagineDAO = new ImmagineDiSistemaDAO(connessioneDB);
			
			this.ufficiPresenti = ufficioDAO.recuperaUfficiPostali();
			this.sediPresenti = sedeDAO.recuperaSediPresenti();
			this.immaginiDiSistema = immagineDAO.getImmaginiDiSistema();
			this.annunciInBacheca = annuncioDAO.recuperaAnnunciInBacheca(utenteLoggato.getEmail());
			
			this.passaAFrameHomePage(frameDiLogin);
		}
	}
	
	public void onConfermaRegistrazioneButtonClicked(String usernameIn, String emailIn, String passwordIn, String residenzaIn) throws SQLException{
		
		utenteDAO.inserisciNuovoUtente(usernameIn, emailIn, passwordIn, residenzaIn);
		
		DialogDiAvvenutaRegistrazione caricamentoTornaALoginFrame = new DialogDiAvvenutaRegistrazione(frameDiRegistrazione, "Ti diamo il benvenuto in UninaSwap!", true);
		caricamentoTornaALoginFrame.setVisible(true);
		
		this.tornaALogin();
	}

	public void onConfermaCambiaImmagineButton(byte[] newBioPic) throws SQLException{
		utenteLoggato.setImmagineProfilo(newBioPic);
		utenteDAO.aggiornaBioPicUtente(utenteLoggato);
		
		dialogConfermaCambiaImmagine.dispose();
		frameCambiaImmagine.dispose();
		
		
		frameProfiloUtente = new FrameProfiloUtente(this,"   Il mio profilo", utenteLoggato);
		frameProfiloUtente.setVisible(true);
	}
	
	public void onSalvaModificheButtonClickedAggiornaUsername(String newUsername) throws SQLException{
		utenteLoggato.setUsername(newUsername);
		utenteDAO.aggiornaUsernameUtente(utenteLoggato);
	}
	
	public void onSalvaModificheButtonClickedAggiornaPWD(String newPassword) throws SQLException{
		utenteLoggato.setPassword(newPassword);
		utenteDAO.aggiornaPasswordUtente(utenteLoggato);
	}
	
	public void onSalvaModificheButtonClickedAggiornaResidenza(String newResidenza) throws SQLException{
		utenteLoggato.setResidenza(newResidenza);
		utenteDAO.aggiornaResidenzaUtente(utenteLoggato);
	}
	
	public void onPubblicaAnnuncioButtonClicked(Annuncio newAnnuncio) throws SQLException{
		annuncioDAO.inserisciAnnuncio(newAnnuncio);
		utenteLoggato.aggiungiAnnuncio(newAnnuncio);
		
		this.passaAFrameHomePage(framePubblicaAnnuncio);
	}
	
	public void onConfermaOffertaButtonClicked(Offerta offertaToAdd) throws SQLException {
		offertaDAO = MapOffertaToOffertaDAO.getOffertaDAO(offertaToAdd, connessioneDB);

		offertaDAO.inserisciOfferta(offertaToAdd);
		utenteLoggato.aggiungiOfferta(offertaToAdd);	
		
		if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable()) {
			
			this.utenteLoggato.aggiornaSaldo(-offertaToAdd.getPrezzoOfferto());
			utenteDAO.aggiornaSaldoUtente(this.utenteLoggato);
			
			dialogOffertaAcquisto.dispose();
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
	
	public void onCaricaOModificaOggettoButtonClicked(int indiceNellArrayDeiFrame, String nomeOggetto) {
		this.frameCaricaOggetto[indiceNellArrayDeiFrame].setVisible(false);
		
		dialogOffertaScambio.aggiungiOggettoCaricato(indiceNellArrayDeiFrame, nomeOggetto);
		dialogOffertaScambio.setVisible(true);
	}
	
	public void onConfermaSegnalazioneButtonClicked(String emailSegnalante, String emailSegnalato, String motivoSegnalazione) throws SQLException{
		utenteDAO.inserisciSegnalazione(emailSegnalante, emailSegnalato, motivoSegnalazione);
		
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


	public void aggiornaStatoOfferta(Offerta offerta, StatoOffertaEnum newStato) {
		offertaDAO = MapOffertaToOffertaDAO.getOffertaDAO(offerta, connessioneDB);
		
		try {
			if(offerta instanceof OffertaAcquisto && (newStato.equals(StatoOffertaEnum.Accettata) || newStato.equals(StatoOffertaEnum.Ritirata)))
				utenteLoggato.aggiornaSaldo(offerta.getPrezzoOfferto());
			
			offerta.setStato(newStato);
			offertaDAO.updateStatoOfferta(offerta);
			passaAFrameHomePage(frameProfiloUtente);
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
		
		try {
			utenteLoggato.aggiornaSaldo(importo);
			utenteDAO.aggiornaSaldoUtente(utenteLoggato);
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
		try {
			annuncio.setStatoEnum(stato);
			annuncioDAO.aggiornaStatoAnnuncio(annuncio);
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


	public void passaADialogConfermaRitiroOfferta(Offerta offerta) {
		dialogConfermaRitiroOfferte = new DialogConfermaRitiroOfferta(this, frameProfiloUtente, offerta);
		dialogConfermaRitiroOfferte.setVisible(true);
	}


	public void chiudiDialogConfermaRitiroOfferta() {
		dialogConfermaRitiroOfferte.dispose();
	}


	public void passaADialogSegnalaUtente(Annuncio annuncio) {
		dialogSegnalaUtente = new DialogSegnalaUtente(this, frameHomePage, utenteLoggato, annuncio.getUtenteProprietario());
		dialogSegnalaUtente.setVisible(true);
	}


	public void passaADialogVisualizzaOffertaSpecificaUtente(Offerta offerta, Controller mainController,
			Container container) {
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


	public void onModificaOffertaAcquistoButtonClicked(Offerta offertaModificata) throws SQLException {
		offertaDAO = new OffertaAcquistoDAO_Postgres(connessioneDB);
		
		((OffertaAcquistoDAO_Postgres)offertaDAO).updateOfferta(offertaModificata);
		
		double prezzoPrecedentementeOfferto = ((OffertaAcquistoDAO_Postgres)offertaDAO).recuperaPrezzoOfferta(this.utenteLoggato.getEmail(), offertaModificata.getMomentoProposta());
		this.utenteLoggato.aggiornaSaldo(prezzoPrecedentementeOfferto - offertaModificata.getPrezzoOfferto());
		utenteDAO.aggiornaSaldoUtente(this.utenteLoggato);
		
		if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable())
			dialogOffertaAcquisto.dispose();
		
		if(dialogOffertaScambio != null && dialogOffertaScambio.isDisplayable())
			dialogOffertaScambio.dispose();
		
		if(dialogOffertaRegalo != null && dialogOffertaRegalo.isDisplayable())
			dialogOffertaRegalo.dispose();
		
		this.passaAFrameHomePage(frameProfiloUtente);
	}


	public void onModificaOffertaScambioButtonClicked(Offerta offertaDaModificare, ArrayList<String> operazioniDaEseguire) throws SQLException {
		offertaDAO = new OffertaScambioDAO_Postgres(connessioneDB);
		
		((OffertaScambioDAO_Postgres)offertaDAO).updateOfferta(offertaDaModificare, operazioniDaEseguire);
		
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


	public void onModificaOffertaRegaloButtonClicked(Offerta offertaDaModificare) throws SQLException {
		offertaDAO = new OffertaRegaloDAO_Postgres(connessioneDB);
		
		((OffertaRegaloDAO_Postgres)offertaDAO).updateOfferta(offertaDaModificare);
		
		if(dialogOffertaAcquisto != null && dialogOffertaAcquisto.isDisplayable())
			dialogOffertaAcquisto.dispose();
		
		if(dialogOffertaScambio != null && dialogOffertaScambio.isDisplayable())
			dialogOffertaScambio.dispose();
		
		if(dialogOffertaRegalo != null && dialogOffertaRegalo.isDisplayable())
			dialogOffertaRegalo.dispose();
	}
	
}