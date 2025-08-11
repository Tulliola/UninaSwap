package dto;

import java.sql.Date;
import java.sql.Timestamp;

import utilities.StatoAnnuncioEnum;

public class AnnuncioRegalo extends Annuncio{
	
	
	public AnnuncioRegalo(int idAnnuncio, String emailProprietario, Oggetto oggettoInAnnuncio, boolean spedizione,
			boolean ritiroInPosta, boolean incontro, StatoAnnuncioEnum stato, Timestamp momentoPubblicazione,
			String nome, Date dataScadenza) {
		super(idAnnuncio, emailProprietario, oggettoInAnnuncio, spedizione, ritiroInPosta, incontro, stato,
				momentoPubblicazione, nome, dataScadenza);
		
	}

	@Override
	public double getPrezzo() {
		return -1;
	}

	@Override
	public String getNotaScambio() {
		return null;
	}
}
