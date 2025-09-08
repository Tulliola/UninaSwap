package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.dao.interfacce.ImmagineDAO;
import dto.Oggetto;

public class ImmagineDAO_Postgres implements ImmagineDAO{

	@Override
	public byte[][] recuperaImmagini(Connection connessioneDB, int idOggetto) throws SQLException {
		byte[][] toReturn = new byte[3][];
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT file_immagine FROM IMMAGINE WHERE idOggetto = ?")){
			ps.setInt(1, idOggetto);
			
			try(ResultSet rs = ps.executeQuery()){
				int i = 0;
				
				while(rs.next()) {
					toReturn[i] = rs.getBytes("file_immagine");
					i++;
				}
				
				return toReturn;
			}
		}
	}

	@Override
	public void inserisciImmagini(Connection connessioneDB, Oggetto oggettoConImmagini) throws SQLException {
		for(int i = 0; i < oggettoConImmagini.getImmagini().length; i++) {
			if(oggettoConImmagini.getImmagine(i) != null) {
				String inserimentoImmagini = "INSERT INTO Immagine (File_immagine, idOggetto) VALUES (?, ?)";
				
				try(PreparedStatement psInserimentoImmagini = connessioneDB.prepareStatement(inserimentoImmagini)){
					psInserimentoImmagini.setBytes(1, oggettoConImmagini.getImmagine(i));
					psInserimentoImmagini.setInt(2, oggettoConImmagini.getIdOggetto());
					
					psInserimentoImmagini.executeUpdate();
				}
			}
		}
	}

	@Override
	public void deleteImmaginiDiOggetto(Connection connessioneDB, int idOggetto) throws SQLException {
		try(PreparedStatement psEliminaFotoOggetto = connessioneDB.prepareStatement("DELETE FROM Immagine WHERE idOggetto = ?")){
			psEliminaFotoOggetto.setInt(1, idOggetto);
			
			psEliminaFotoOggetto.executeUpdate();
		}
	}

}
