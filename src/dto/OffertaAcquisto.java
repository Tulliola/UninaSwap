package dto;

import java.sql.Timestamp;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaAcquisto extends Offerta{
	private double prezzoOfferto;

	public OffertaAcquisto(int idOfferta, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, double prezzoOfferto) {
		super(idOfferta, momentoProposta, modalitaConsegnaScelta, stato);
		this.prezzoOfferto = prezzoOfferto;
	}

	@Override
	public double getPrezzoOfferto() {
		return prezzoOfferto;
	}
}
