package database;

import java.io.*;
import java.sql.*;
import eccezioni.NomeSchemaException;

public class DBConnection implements AutoCloseable{
	private static DBConnection connessioneAlDB = null;
	private Connection connessione = null;
	
	private DBConnection() {
		
	}
	
	public static DBConnection getConnessioneAlDB() {
		if(connessioneAlDB == null)
			connessioneAlDB = new DBConnection();
		
		return connessioneAlDB;
	}
	
	public Connection connettitiTramiteSchema(String nomeSchema) throws NomeSchemaException {
		
		if(nomeSchema == null)
			throw new NomeSchemaException("Non è stato passato alcun nome di schema.");
		else if (nomeSchema.equals(""))
			throw new NomeSchemaException("Il nome di schema passato in input è vuoto.");
		
		String passwordDiAccesso;
		
		try(BufferedReader buffer = new BufferedReader(new FileReader(new File("src/database/pwdfile")))){
			passwordDiAccesso = buffer.readLine();
			
			if(connessione == null || connessione.isClosed()) {
				String urlDiAccesso = "jdbc:postgresql://localhost:5432/UninaSwap?currentSchema="+nomeSchema;
				connessione = DriverManager.getConnection(urlDiAccesso, "postgres", passwordDiAccesso);
			}
		}
		catch(IOException | SQLException eccezione) {
			eccezione.printStackTrace();
		}
		
		return connessione;
	}

	@Override
	public void close() throws Exception {
		connessione.close();
	}
}