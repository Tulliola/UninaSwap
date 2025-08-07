package database.dao.implementazioni;

import database.dao.interfacce.ProfiloUtenteDAO;

import java.sql.*;

public class ProfiloUtenteDAO_Postgres implements ProfiloUtenteDAO{
	private Connection connessioneDB = null;
	
	public ProfiloUtenteDAO_Postgres(Connection connInput) {
		this.connessioneDB = connInput;
	}
	
}
