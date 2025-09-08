package database;

import java.io.*;
import java.sql.*;
import eccezioni.NomeSchemaException;

public class DBConnectionService implements AutoCloseable{
	private static Connection connessione = null;
	
	public static Connection getConnessioneAlDB(String nomeSchema) throws NomeSchemaException, SQLException{
		if(connessione == null || connessione.isClosed())
			connessione = connettitiTramiteSchema(nomeSchema);
		
		return connessione;
	}
	
	private static Connection connettitiTramiteSchema(String nomeSchema) throws NomeSchemaException {
		
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