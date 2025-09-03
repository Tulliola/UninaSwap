package database.dao.implementazioni;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.dao.interfacce.ImmagineDiSistemaDAO;

import java.sql.Connection;

public class ImmagineDiSistemaDAO_Postgres implements ImmagineDiSistemaDAO{
	private Connection connessioneDB;
	
	public ImmagineDiSistemaDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public byte[][] recuperaImmaginiDiSistema() throws SQLException{
		String recuperaNumFoto = "SELECT COUNT(*) AS numFoto FROM Immagine_di_sistema";
		byte[][] immaginiDiSistema;
		
		try(PreparedStatement psRecuperaNumFoto = connessioneDB.prepareStatement(recuperaNumFoto);
			ResultSet rsRecuperaNumFoto = psRecuperaNumFoto.executeQuery()){
			
			rsRecuperaNumFoto.next();
			
			immaginiDiSistema = new byte[rsRecuperaNumFoto.getInt("numFoto")][];
			
			String recuperaFoto = "SELECT File_immagine FROM Immagine_di_sistema";
			
			try(PreparedStatement psRecuperaFoto = connessioneDB.prepareStatement(recuperaFoto);
				ResultSet rsRecuperaFoto = psRecuperaFoto.executeQuery()){
				
				int i = 0;
				while(rsRecuperaFoto.next()) {
					immaginiDiSistema[i] = rsRecuperaFoto.getBytes("File_immagine");
					i++;
				}
			}
			
		}
		
		return immaginiDiSistema;
	}
}
