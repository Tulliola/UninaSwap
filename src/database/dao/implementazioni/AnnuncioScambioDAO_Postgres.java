package database.dao.implementazioni;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Connection;

import database.dao.interfacce.AnnuncioScambioDAO;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.Oggetto;
import dto.ProfiloUtente;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.StatoAnnuncioEnum;

public class AnnuncioScambioDAO_Postgres implements AnnuncioScambioDAO{
	Connection connessioneDB;
	
	public AnnuncioScambioDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}

	@Override
	public AnnuncioScambio recuperaAnnuncioDaId(int idAnnuncio) throws SQLException {
		try(PreparedStatement ps =  connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE idAannuncio = ?")){
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
	public ArrayList<AnnuncioScambio> recuperaAnnunciDiUtente(ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<AnnuncioScambio> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email = ? AND Tipo_annuncio = 'Scambio' ORDER BY Momento_pubblicazione DESC")){
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
	public ArrayList<AnnuncioScambio> recuperaAnnunciNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException {
		ArrayList<AnnuncioScambio> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email <> ? AND Tipo_annuncio = 'Scambio' ORDER BY Momento_pubblicazione DESC")){
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
		private AnnuncioScambio creaAnnuncio(ResultSet rs) throws SQLException {
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
							
			return new AnnuncioScambio(
					rs.getInt("idAnnuncio"),
					rs.getString("Email"),
					oggettoInAnnuncio,
					rs.getBoolean("Spedizione"),
					rs.getBoolean("Ritiro_in_posta"),
					rs.getBoolean("Incontro"),
					StatoAnnuncioEnum.confrontaConDB(rs.getString("Stato")),
					rs.getTimestamp("Momento_pubblicazione"),
					rs.getString("Nome"),
					rs.getDate("Data_scadenza"),
					rs.getString("Nota_scambio")
			);
		}
}
