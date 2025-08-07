package controller;


//Import dalle librerie standard
import java.sql.*;

import java.awt.event.*;

import javax.swing.*;

import database.DBConnection;

import database.dao.implementazioni.*;
import database.dao.interfacce.*;
import dto.*;
//Import dal package GUI
import gui.*;

//Import dal package DTO

//Import dal package Eccezioni
import eccezioni.NomeSchemaException;


public class Controller {
	private FrameDiLogin frameDiLogin;
	private FrameDiRegistrazione frameDiRegistrazione;
	
	private ProfiloUtenteDAO profiloDAO;
	
	private static Connection connessioneDB;
	
	public Controller() {
		this.definisciConnessioneAlDB();
		frameDiLogin = new FrameDiLogin(this);
		frameDiLogin.setVisible(true);		
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


	public void onAccessoClicked(String email, String password) throws SQLException{
		profiloDAO = new ProfiloUtenteDAO_Postgres(connessioneDB);
		ProfiloUtente profiloLoggato = profiloDAO.recuperaUtenteConEmail(email, password);
		System.out.println(profiloLoggato);
	}
}