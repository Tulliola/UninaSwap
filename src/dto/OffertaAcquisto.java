package dto;

import java.sql.Timestamp;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaAcquisto extends Offerta{
	private double prezzoOfferto;

	public OffertaAcquisto(Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito, double prezzoOfferto) {
		super(momentoProposta, modalitaConsegnaScelta, stato, annuncioRiferito);
		this.prezzoOfferto = prezzoOfferto;
	}

	@Override
	public double getPrezzoOfferto() {
		return prezzoOfferto;
	}
}
