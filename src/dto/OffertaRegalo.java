package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaRegalo extends Offerta {
	//Attributi propri
	private Double prezzoOfferto = null;
	
	//Attributi derivati da relazioni
	private ArrayList<Oggetto> oggettiOfferti;

	public OffertaRegalo(Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito) {
		super(momentoProposta, modalitaConsegnaScelta, stato, annuncioRiferito);
	}

	public void aggiungiOggettoAllOfferta(Oggetto oggettoOfferto) {
		this.oggettiOfferti.add(oggettoOfferto);
	}
	
	public void aggiungiOggettiAllOfferta(ArrayList<Oggetto> oggettiOfferti) {
		this.oggettiOfferti.addAll(oggettiOfferti);
	}
	
	public void aggiuntiOffertaMonetaria(double offerta) {
		this.prezzoOfferto = offerta;
	}
	
	@Override
	public ArrayList<Oggetto> getOggettiOfferti() {
		return this.oggettiOfferti;
	}

	@Override
	public Double getPrezzoOfferto() {
		return prezzoOfferto;
	}
}
