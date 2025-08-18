package utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class ImmagineDiSistemaDAO {
	private Connection connessioneDB;
	private byte[][] immaginiDiSistema;
	
	public ImmagineDiSistemaDAO(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
		
		this.recuperaImmaginiDiSistema();
	}
	
	private void recuperaImmaginiDiSistema() {
		String recuperaNumFoto = "SELECT COUNT(*) AS numFoto FROM Immagine_di_sistema";
		
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
		catch(SQLException exc) {
			exc.printStackTrace();
		}
	}
	
	public byte[][] getImmaginiDiSistema() {
		return immaginiDiSistema;
	}
}
