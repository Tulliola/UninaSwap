package dto;

import java.sql.Date;
import java.sql.Timestamp;

import utilities.StatoAnnuncioEnum;

public class AnnuncioScambio extends Annuncio{

	private String notaScambio;

	public AnnuncioScambio(int idAnnuncio, String emailProprietario, Oggetto oggettoInAnnuncio, boolean spedizione,
			boolean ritiroInPosta, boolean incontro, StatoAnnuncioEnum stato, Timestamp momentoPubblicazione,
			String nome, Date dataScadenza, String notaScambio) {
		super(idAnnuncio, emailProprietario, oggettoInAnnuncio, spedizione, ritiroInPosta, incontro, stato,
				momentoPubblicazione, nome, dataScadenza);
		this.notaScambio = notaScambio;
	}
	
	public String getNotaScambio() {
		return notaScambio;
	}

	public void setNotaScambio(String notaScambio) {
		this.notaScambio = notaScambio;
	}

	@Override
	public double getPrezzo() {
		return -1;
	}
}
