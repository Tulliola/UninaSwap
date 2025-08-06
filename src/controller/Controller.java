package controller;

import java.sql.*;

//Import dal package GUI
import gui.FrameDiLogin;

<<<<<<< HEAD
public class Controller {
	public Controller() {
		FrameDiLogin loginFrame = new FrameDiLogin(this);
	}
	
	public static void main(String args[]) {
		Controller mainController = new Controller();
=======
//Import dal package Database
import database.DBConnection;
import eccezioni.NomeSchemaException;

public class Controller {
	private Connection connessione = null;
	
	//Prova di connessione
	public Controller() {
		DBConnection dbConnection = DBConnection.getConnessioneAlDB();
		
		try {
			connessione = dbConnection.connettitiTramiteSchema("uninaswap");

			if(connessione == null) {
				System.out.println("Non è stato possibile definire una connessione con il DB.");
				System.exit(0);
			}
			else
				System.out.println("Connessione al DB riuscita.");
		}
		catch(NomeSchemaException eccezione) {
			System.out.println(eccezione.getMessage());
		}
	}
	
	public static void main(String args[]) {
		Controller controller = new Controller();
>>>>>>> main
	}
}
