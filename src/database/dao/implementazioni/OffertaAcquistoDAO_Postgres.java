//package database.dao.implementazioni;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import database.dao.interfacce.OffertaAcquistoDAO;
//import dto.Offerta;
//import dto.ProfiloUtente;
//
//public class OffertaAcquistoDAO_Postgres implements OffertaAcquistoDAO{
//	private Connection connessioneDB = null;
//	
//	public OffertaAcquistoDAO_Postgres(Connection connessioneDB) {
//		this.connessioneDB = connessioneDB;
//	}
//
//	@Override
//	public ArrayList<Offerta> recuperaOfferteAcquistoDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException {
//		ArrayList<Offerta> offerteDiUtente = new ArrayList();
//		
//		try(PreparedStatement ps = connessioneDB.prepareStatement("SELECT * FROM OFFERTA_ACQUISTO WHERE Email = ?")){
//			ps.setString(1, utenteLoggato.getEmail());
//			
//			try(ResultSet rs = ps.executeQuery()){
//				while(rs.next()) {
//					offerteDiUtente.add(offertaCorrente(rs));
//				}
//				
//				return offerteDiUtente;
//			}
//		}
//	}
//
//
//	@Override
//	public ArrayList<Offerta> recuperaOfferteAcquistoNonDiUtente(ProfiloUtente utenteLoggato) throws SQLException, IOException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	
//	private Offerta offertaCorrente(ResultSet rs) {
//		int idOfferta = rs.getInt(0)W
//		return new OffertaAcquisto()
//		return null;
//	}
//}
