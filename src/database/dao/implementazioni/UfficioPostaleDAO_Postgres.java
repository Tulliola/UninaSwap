package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.dao.interfacce.UfficioPostaleDAO;
import dto.UfficioPostale;

public class UfficioPostaleDAO_Postgres implements UfficioPostaleDAO{
	
	private Connection connessioneDB;
	
	public UfficioPostaleDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}

	@Override
	public ArrayList<UfficioPostale> recuperaUfficiPostali() throws SQLException {
		ArrayList<UfficioPostale> ufficiRecuperati = new ArrayList();
		
		String recuperaUffici = "SELECT * FROM Ufficio_postale";
		
		try(PreparedStatement psRecuperaUffici = connessioneDB.prepareStatement(recuperaUffici)){
			try(ResultSet rsRecuperaUffici = psRecuperaUffici.executeQuery()){
				while(rsRecuperaUffici.next()) {
					int idUfficio = rsRecuperaUffici.getInt("idUfficio");
					String nome = rsRecuperaUffici.getString("nome");
					String via = rsRecuperaUffici.getString("via");
					String civico = rsRecuperaUffici.getString("civico");
					String comune = rsRecuperaUffici.getString("comune");
					String cap = rsRecuperaUffici.getString("cap");
					ufficiRecuperati.add(new UfficioPostale(idUfficio, nome, via, civico, comune, cap));
				}
				
				return ufficiRecuperati;
			}
		}
	}
	
}
