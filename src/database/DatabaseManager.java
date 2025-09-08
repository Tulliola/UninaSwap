package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

    private static HikariDataSource dataSource;

    static {
        // Creazione di un oggetto di configurazione
        HikariConfig config = new HikariConfig();

        // Configurazione delle proprietà di base
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/UninaSwap?currentSchema=uninaswap");
        config.setUsername("postgres");
        
        try {
        	config.setPassword(recuperaPassword());
        }
        catch(IOException eccezione) {
        	System.err.println("Errore nel reperimento della password ("+ eccezione.getMessage() + ").");
        	System.exit(0);
        }
//        config.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Nome della classe del driver

        // Configurazione delle proprietà del pool (opzionali ma consigliate)
        config.setMaximumPoolSize(10); // Dimensione massima del pool di connessioni
        config.setConnectionTimeout(30000); // Timeout per ottenere una connessione dal pool (ms)
        config.setIdleTimeout(600000); // Timeout di inattività per le connessioni (ms)
        config.setMaxLifetime(1800000); // Durata massima di una connessione (ms)

        // Creazione del DataSource
        dataSource = new HikariDataSource(config);
    }

    // Metodo pubblico per ottenere una connessione
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Metodo per chiudere il pool di connessioni (importante alla chiusura dell'applicazione)
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
    
    private static String recuperaPassword() throws IOException{
    	String passwordDiAccesso;
    	
    	try(BufferedReader buffer = new BufferedReader(new FileReader(new File("src/database/pwdfile")))){
			passwordDiAccesso = buffer.readLine();
		}
    	
    	return passwordDiAccesso;
    }
}