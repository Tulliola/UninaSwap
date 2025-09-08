package database.dao.implementazioni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.dao.interfacce.IncontroDAO;
import dto.Annuncio;

public class IncontroDAO_Postgres implements IncontroDAO{

	@Override
	public void inserisciIncontro(Connection connessioneDB, Annuncio annuncio, int iEsimoIncontro) throws SQLException {
		String inserimentoIncontri = "INSERT INTO Incontro (idSede, idAnnuncio, Ora_inizio_incontro, Ora_fine_incontro, Giorno_incontro) VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement psInserimentoIncontri = connessioneDB.prepareStatement(inserimentoIncontri)){
			psInserimentoIncontri.setInt(1, annuncio.getSedeIncontroProposte().get(iEsimoIncontro).getIdSede());
			psInserimentoIncontri.setInt(2, annuncio.getIdAnnuncio());
			psInserimentoIncontri.setString(3, annuncio.getOraInizioIncontro().get(iEsimoIncontro));
			psInserimentoIncontri.setString(4, annuncio.getOraFineIncontro().get(iEsimoIncontro));
			psInserimentoIncontri.setString(5, annuncio.getGiornoIncontro().get(iEsimoIncontro).toString());
			
			psInserimentoIncontri.executeUpdate();
			
		}	}

}
