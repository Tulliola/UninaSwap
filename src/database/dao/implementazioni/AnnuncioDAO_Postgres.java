package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.dao.interfacce.AnnuncioDAO;
import dto.Annuncio;
import dto.AnnuncioRegalo;
import dto.AnnuncioScambio;
import dto.AnnuncioVendita;
import dto.Oggetto;
import dto.ProfiloUtente;
import utilities.CategoriaEnum;
import utilities.CondizioneEnum;
import utilities.StatoAnnuncioEnum;

public class AnnuncioDAO_Postgres implements AnnuncioDAO{
	Connection connessioneDB;
	
	public AnnuncioDAO_Postgres(Connection connessioneDB) {
		this.connessioneDB = connessioneDB;
	}

	@Override
	public Annuncio recuperaAnnuncioDaID(int idAnnuncio) throws SQLException {
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE idAannuncio = ?")){
			Oggetto oggettoInAnnuncio;
			ps.setInt(1, idAnnuncio);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					if(rs.getString("Tipo_annuncio").equals("Vendita"))
						return creaAnnuncioVendita(rs);
					else if(rs.getString("Tipo_annuncio").equals("Scambio"))
						return creaAnnuncioScambio(rs);
					else if(rs.getString("Tipo_annuncio").equals("Regalo"))
						return creaAnnuncioRegalo(rs);
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<Annuncio> recuperaAnnunciDisponibiliDiUtente(ProfiloUtente utenteLoggato) throws SQLException{
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email = ? AND Stato = 'Disponibile' ORDER BY Momento_pubblicazione DESC")){
			ps.setString(1,  utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					if(rs.getString("Tipo_annuncio").equals("Vendita"))
						toReturn.add(creaAnnuncioVendita(rs));
					else if (rs.getString("Tipo_annuncio").equals("Scambio"))
						toReturn.add(creaAnnuncioScambio(rs));
					else if (rs.getString("Tipo_annuncio").equals("Regalo"))
						toReturn.add(creaAnnuncioRegalo(rs));
				}
				return toReturn;
			}
		}
	}

	@Override
	public ArrayList<Annuncio> recuperaAnnunciDisponibiliNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException{
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM ANNUNCIO WHERE Email <> ? AND Stato = 'Disponibile' ORDER BY Momento_pubblicazione DESC")){
			ps.setString(1,  utenteLoggato.getEmail());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					if(rs.getString("Tipo_annuncio").equals("Vendita"))
						toReturn.add(creaAnnuncioVendita(rs));
					else if (rs.getString("Tipo_annuncio").equals("Scambio"))
						toReturn.add(creaAnnuncioScambio(rs));
					else if (rs.getString("Tipo_annuncio").equals("Regalo"))
						toReturn.add(creaAnnuncioRegalo(rs));
				}
				return toReturn;
			}
		}
	}
	
	
	//metodi ausiliari
	private AnnuncioRegalo creaAnnuncioRegalo(ResultSet rs) throws SQLException {
		Oggetto oggettoInAnnuncio;
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, rs.getInt("idOggetto"));
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				//Non c'è bisogno di mettere questo next in un if perché idOggetto è foreign key unique not null, quindi sempre valorizzata e al massimo una
				rsOggetto.next();
				oggettoInAnnuncio = this.creaOggetto(rsOggetto);
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
	
	private AnnuncioVendita creaAnnuncioVendita(ResultSet rs) throws SQLException {
		Oggetto oggettoInAnnuncio;
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, rs.getInt("idOggetto"));
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				//Non c'è bisogno di mettere questo next in un if perché idOggetto è foreign key unique not null, quindi sempre valorizzata e al massimo una
				rsOggetto.next();
				oggettoInAnnuncio = this.creaOggetto(rsOggetto);
			}
		}
						
		return new AnnuncioVendita(
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
				rs.getDouble("Prezzo_iniziale")
		);
	}
	
	private AnnuncioScambio creaAnnuncioScambio(ResultSet rs) throws SQLException {
		Oggetto oggettoInAnnuncio;
		
		try(PreparedStatement psOggetto = connessioneDB.prepareStatement("SELECT * FROM OGGETTO WHERE idOggetto = ?")){
			psOggetto.setInt(1, rs.getInt("idOggetto"));
			
			try(ResultSet rsOggetto = psOggetto.executeQuery()){
				//Non c'è bisogno di mettere questo next in un if perché idOggetto è foreign key unique not null, quindi sempre valorizzata e al massimo una
				rsOggetto.next();
				oggettoInAnnuncio = this.creaOggetto(rsOggetto);
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
	
	private Oggetto creaOggetto(ResultSet rs) throws SQLException {
		return new Oggetto(
				rs.getInt("idOggetto"),
				rs.getString("Email"),
				rs.getString("Descrizione"),
				CategoriaEnum.confrontaConDB(rs.getString("Categoria")),
				CondizioneEnum.confrontaConDB(rs.getString("Condizioni"))
		);
	}
		
}
