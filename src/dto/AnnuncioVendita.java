package dto;

import java.sql.Date;
import java.sql.Timestamp;

import utilities.StatoAnnuncioEnum;

public class AnnuncioVendita extends Annuncio{
	private double prezzoIniziale;

	public AnnuncioVendita(int idAnnuncio, String emailProprietario, Oggetto oggettoInAnnuncio, boolean spedizione,
			boolean ritiroInPosta, boolean incontro, StatoAnnuncioEnum stato, Timestamp momentoPubblicazione,
			String nome, Date dataScadenza, double prezzoIniziale) {
		super(idAnnuncio, emailProprietario, oggettoInAnnuncio, spedizione, ritiroInPosta, incontro, stato,
				momentoPubblicazione, nome, dataScadenza);
		this.prezzoIniziale = prezzoIniziale;
	}


	public void setPrezzoIniziale(double prezzoIniziale) {
		this.prezzoIniziale = prezzoIniziale;
	}

	@Override
	public double getPrezzo() {
		return this.prezzoIniziale;
	}

	@Override
	public String getNotaScambio() {
		return null;
	}

}
