package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.dao.interfacce.SedeUniversitaDAO;
import dto.Annuncio;
import dto.SedeUniversita;

public class SedeUniversitaDAO_Postgres implements SedeUniversitaDAO{
	private Connection connessioneDB;
	private SedeUniversita sede;
	
	public SedeUniversitaDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	public SedeUniversitaDAO_Postgres(Connection connessioneDB, SedeUniversita sede) {
		this.connessioneDB = connessioneDB;
		this.sede = sede;
	}

	@Override
	public ArrayList<SedeUniversita> recuperaSediPresenti() throws SQLException {
		String recuperaSedi = "SELECT idSede, nome FROM Sede_universita";
		ArrayList<SedeUniversita> sediRitornate = new ArrayList();
		
		try(PreparedStatement psRecuperaSedi = connessioneDB.prepareStatement(recuperaSedi)){
			try(ResultSet rsRecuperaSedi = psRecuperaSedi.executeQuery()){
				while(rsRecuperaSedi.next()) {
					sediRitornate.add(new SedeUniversita(rsRecuperaSedi.getInt("idSede"), rsRecuperaSedi.getString("Nome")));
				}
				
				return sediRitornate;
			}
		}
	}
	
	@Override
	public SedeUniversita recuperaSedeDaId(int idSede) throws SQLException {
		String recuperaSede = "SELECT Nome FROM Sede_universita WHERE idSede = ?";
		
		try(PreparedStatement psRecuperaSede = connessioneDB.prepareStatement(recuperaSede)){
			psRecuperaSede.setInt(1, idSede);
			try(ResultSet rsRecuperaSede = psRecuperaSede.executeQuery()){	
				if(rsRecuperaSede.next())
					return new SedeUniversita(idSede, rsRecuperaSede.getString("Nome"));
			}
		}
		
		return null;
	}
}
