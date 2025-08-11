package database.dao.implementazioni;

import database.DBConnection;
import database.dao.interfacce.AnnuncioRegaloDAO;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.Oggetto;
import dto.ProfiloUtente;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.StatoAnnuncioEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnnuncioRegaloDAO_Postgres implements AnnuncioRegaloDAO{
	private Connection connessioneDB = null;
	
	public AnnuncioRegaloDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}
	
	@Override
	public AnnuncioRegalo recuperaAnnuncioDaId(int idAnnuncio) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE idAannuncio = ?")){
			Oggetto oggettoInAnnuncio;
			ps.setInt(1, idAnnuncio);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					return creaAnnuncio(rs);
				}
			}
		}
		return null;
	}
	
	@Override
	public ArrayList<AnnuncioRegalo> recuperaAnnunciDiUtente(ProfiloUtente utenteLoggato) throws SQLException{
		ArrayList<AnnuncioRegalo> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email = ? AND Tipo_annuncio = 'Regalo' ORDER BY Momento_pubblicazione DESC")){
			ps.setString(1,  utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					toReturn.add(creaAnnuncio(rs));
				}
				return toReturn;
			}
		}
	}
	
	@Override
	public ArrayList<AnnuncioRegalo> recuperaAnnunciNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException{
		ArrayList<AnnuncioRegalo> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO "
				+ "WHERE Email <> ? AND Tipo_annuncio = 'Regalo' AND Stato = 'Disponibile' "
				+ "ORDER BY Momento_pubblicazione DESC")){
			ps.setString(1,  utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					toReturn.add(creaAnnuncio(rs));
				}
				return toReturn;
			}
		}
	}
	
	
	//metodo ausiliario
	private AnnuncioRegalo creaAnnuncio(ResultSet rs) throws SQLException {
		Oggetto oggettoInAnnuncio;
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, rs.getInt("idOggetto"));
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				//Non c'è bisogno di mettere questo next in un if perché idOggetto è foreign key unique not null, quindi sempre valorizzata e al massimo una
				rsOggetto.next();
				oggettoInAnnuncio = new Oggetto(
						rsOggetto.getInt("idOggetto"),
						rsOggetto.getString("Email"),
						rsOggetto.getString("Descrizione"),
						CategoriaEnum.confrontaConDB(rsOggetto.getString("Categoria")),
						CondizioneEnum.confrontaConDB(rsOggetto.getString("Condizioni"))
				);
			}
		}
						
		return new AnnuncioRegalo(
				rs.getInt("idAnnuncio"),
				rs.getString("Email"),
				oggettoInAnnuncio,
				rs.getBoolean("Spedizione"),
				rs.getBoolean("Ritiro_in_posta"),
				rs.getBoolean("Incontro"),
				StatoAnnuncioEnum.confrontaConDB(rs.getString("Stato")),
				rs.getTimestamp("Momento_pubblicazione"),
				rs.getString("Nome"),
				rs.getDate("Data_scadenza")
		);
	}
}
