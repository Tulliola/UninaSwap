package controller;

import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import database.dao.implementazioni.*;
import database.dao.interfacce.*;

//Import dal package GUI
import gui.*;

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
	
	private ProfiloUtente utenteLoggato;
	
	private static Connection connessioneDB;
	
	public Controller() {
		this.definisciConnessioneAlDB();
		
//		frameDiLogin = new FrameDiLogin(this);
//		frameDiLogin.setVisible(true);		
		
		ProfiloUtenteDAO_Postgres dao = new ProfiloUtenteDAO_Postgres(connessioneDB);
		try {
			utenteLoggato = dao.recuperaUtenteConEmailOUsername("tulliola", "CaneBlu92!");
			frameProfiloUtente = new FrameProfiloUtente(this, utenteLoggato);
			frameProfiloUtente.setVisible(true);
		}
		catch(SQLException exc) {
			
		}
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
	
	public void passaAFrameProfiloUtente() {
		//TODO cambiare quando facciamo la home page. bisogna fare la dispose di frameHomePage
		frameDiLogin.dispose();
		frameProfiloUtente = new FrameProfiloUtente(this, utenteLoggato);
		frameProfiloUtente.setVisible(true);
	}

	public void onAccessoButtonClicked(String email, String password) throws SQLException{
		ProfiloUtenteDAO_Postgres profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		utenteLoggato = profiloDAO.recuperaUtenteConEmailOUsername(email, password);
		
		//Arrivato a questo punto, se non è sollevata eccezione, email e password matchano con qualche riga nel DB.
		
		//TODO cambiare quando facciamo la home page.
		passaAFrameProfiloUtente();
	}
	
	public void onConfermaRegistrazioneButtonClicked(String usernameIn, String emailIn, String passwordIn, String residenzaIn) throws SQLException, MatricolaNonTrovataException{
		ProfiloUtenteDAO_Postgres profiloInRegistrazione = new ProfiloUtenteDAO_Postgres(connessioneDB);
		profiloInRegistrazione.inserisciNuovoUtente(usernameIn, emailIn, passwordIn, residenzaIn);
				
		//Arrivato a questo punto, se non è stata sollevata eccezione, la registrazione è andata a buon fine.
		
		DialogDiAvvenutaRegistrazione caricamentoTornaALoginFrame = new DialogDiAvvenutaRegistrazione(frameDiRegistrazione, "Ti diamo il benvenuto in UninaSwap!", true);
		caricamentoTornaALoginFrame.setVisible(true);
		this.tornaALogin();
	}
	
	public void onSalvaModificheProfiloClicked() {
		
	}
}