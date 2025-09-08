package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.dao.interfacce.UfficioPostaleDAO;
import dto.UfficioPostale;

public class UfficioPostaleDAO_Postgres implements UfficioPostaleDAO{
	
	@Override
	public ArrayList<UfficioPostale> recuperaUfficiPostali(Connection connessioneDB) throws SQLException {
		ArrayList<UfficioPostale> ufficiRecuperati = new ArrayList<UfficioPostale>();
		
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

	@Override
	public UfficioPostale recuperaUfficioPostaleConId(Connection connessioneDB, int idUfficio) throws SQLException {
		try(PreparedStatement psRecuperaUfficio = connessioneDB.prepareStatement("SELECT * FROM Ufficio_postale WHERE idUfficio = ?")){
			psRecuperaUfficio.setInt(1, idUfficio);
			
			try(ResultSet rsRecuperaUfficio = psRecuperaUfficio.executeQuery()){
				if(rsRecuperaUfficio.next())
					return new UfficioPostale(rsRecuperaUfficio.getInt("idUfficio"), rsRecuperaUfficio.getString("Nome"), 
											  rsRecuperaUfficio.getString("Via"), rsRecuperaUfficio.getString("Civico"),
											  rsRecuperaUfficio.getString("Comune"), rsRecuperaUfficio.getString("CAP"));
			}
		}
		
		return null;
	}
	
	@Override
	public Integer recuperaIdUfficio(Connection connessioneDB, String nome) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT idUfficio FROM Ufficio_postale WHERE nome = ?")){
				ps.setString(1, nome);
				try(ResultSet rs = ps.executeQuery()){
					if(rs.next()) {
						return rs.getInt("idUfficio");
					}
					else
						return null;
				}
		}
	}
}
