package dto;

import java.sql.Timestamp;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaAcquisto extends Offerta{
	private Double prezzoOfferto;

	public OffertaAcquisto(Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito, double prezzoOfferto) {
		super(momentoProposta, modalitaConsegnaScelta, stato, annuncioRiferito);
		this.prezzoOfferto = prezzoOfferto;
	}

	@Override
	public Double getPrezzoOfferto() {
		return prezzoOfferto;
	}
	
	@Override 
	public String toString() {
		String toReturn = super.toString();
		
		toReturn += "\nPrezzo offerto = "+prezzoOfferto;
		
		return toReturn;
	}
}
