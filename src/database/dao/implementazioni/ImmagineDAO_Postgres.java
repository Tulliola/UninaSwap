package database.dao.implementazioni;

import database.dao.interfacce.ImmagineDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Controller;
import dto.Oggetto;

public class ImmagineDAO_Postgres implements ImmagineDAO{

	private Controller mainController;
	private Connection connessioneDB;
	
	public ImmagineDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public void updateImmagine(Oggetto oggettoDiImmagine) throws SQLException {
		for(byte[] immagine: oggettoDiImmagine.getImmagini()) {
			if(immagine != null) {
				try(PreparedStatement ps = connessioneDB.prepareStatement("UPDATE Immagine SET file_immagine = ? WHERE idOggetto = ?")){
					ps.setBytes(1, immagine);
					ps.setInt(2, oggettoDiImmagine.getIdOggetto());
					
					System.out.print(ps.executeUpdate());
				}
			}
		}
	}
}
