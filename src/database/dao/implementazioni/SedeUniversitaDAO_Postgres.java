package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.dao.interfacce.SedeUniversitaDAO;
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
		String recuperaSedi = "SELECT * FROM Sede_universita";
		ArrayList<SedeUniversita> sediRitornate = new ArrayList();
		
		try(PreparedStatement psRecuperaSedi = connessioneDB.prepareStatement(recuperaSedi)){
			try(ResultSet rsRecuperaSedi = psRecuperaSedi.executeQuery()){
				while(rsRecuperaSedi.next()) {
					sediRitornate.add(recuperaSedeCorrente(rsRecuperaSedi.getInt("idSede")));
				}
				
				return sediRitornate;
			}
		}
	}

	private SedeUniversita recuperaSedeCorrente(int idSede) throws SQLException {
		String sedeCorrente = "SELECT * FROM SEDE_UNIVERSITA WHERE idSede = ?";
		
		try(PreparedStatement psSedeCorrente = connessioneDB.prepareStatement(sedeCorrente)){
			psSedeCorrente.setInt(1, idSede);
			
			try(ResultSet rsSedeCorrente = psSedeCorrente.executeQuery()){
				rsSedeCorrente.next();
				return new SedeUniversita(idSede, rsSedeCorrente.getString("nome"), rsSedeCorrente.getString("indirizzo"));
			}
		}
	}

}
