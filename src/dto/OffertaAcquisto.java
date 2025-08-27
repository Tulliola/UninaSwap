package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaAcquisto extends Offerta{
	private Double prezzoOfferto;

	public OffertaAcquisto(ProfiloUtente offerente, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito, double prezzoOfferto) {
		super(offerente, momentoProposta, modalitaConsegnaScelta, stato, annuncioRiferito);
		this.prezzoOfferto = prezzoOfferto;
	}

	//Costruttore per l'inserimento
	public OffertaAcquisto(ProfiloUtente offerente, ModConsegnaEnum modalitaConsegnaScelta, Annuncio annuncioRiferito, double prezzoOfferto) {
		super(offerente, modalitaConsegnaScelta, annuncioRiferito);
		this.prezzoOfferto = prezzoOfferto;
	}
	
	@Override
	public Double getPrezzoOfferto() {
		return prezzoOfferto;
	}
	
	public void setPrezzoOfferto(Double newPrezzo) {
		this.prezzoOfferto = newPrezzo;
	}
	@Override 
	public String toString() {
		String toReturn = super.toString();
		
		toReturn += "\nPrezzo offerto = "+prezzoOfferto;
		
		return toReturn;
	}
	
}
