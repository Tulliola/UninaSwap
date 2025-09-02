package dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaScambio extends Offerta {
	//Attributi propri
	Integer idOfferta;
	private int numOggettiOffribili = 3;
	
	//Attributi derivati da relazioni
	private ArrayList<Oggetto> oggettiOfferti = new ArrayList<>();
	
	
	public OffertaScambio(ProfiloUtente offerente, int idOfferta, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito, ArrayList<Oggetto> oggettiOfferti) {
		super(offerente, momentoProposta, modalitaConsegnaScelta, stato, annuncioRiferito);
		this.idOfferta = idOfferta;
		this.oggettiOfferti.addAll(oggettiOfferti);
	}
	
	public OffertaScambio(ProfiloUtente offerente, ModConsegnaEnum modalitaConsegnaScelta,
			Annuncio annuncioRiferito, ArrayList<Oggetto> oggettiOfferti) {
		super(offerente, modalitaConsegnaScelta, annuncioRiferito);
		this.oggettiOfferti.addAll(oggettiOfferti);
	}
	
	public void aggiungiOggettoAllOfferta(Oggetto oggettoOfferto) {
		this.oggettiOfferti.add(oggettoOfferto);
	}
	
	public void aggiungiOggettiAllOfferta(ArrayList<Oggetto> oggettiOfferti) {
		this.oggettiOfferti.addAll(oggettiOfferti);
	}

	@Override
	public ArrayList<Oggetto> getOggettiOfferti() {
		return this.oggettiOfferti;
	}
	

	public void setOggettiOfferti(ArrayList<Oggetto> oggettiOfferti) {
		this.oggettiOfferti = oggettiOfferti;
	}
	
	public int getNumOggettiOffribili() {
		return numOggettiOffribili;
	}
	
	@Override
	public Integer getIdOfferta() {
		return this.idOfferta;
	}
	
	@Override
	public String toString() {
		String toReturn = super.toString();
		
		toReturn += "Oggetti offerti = \n";
		for(Oggetto oggetto: oggettiOfferti) {
			toReturn += oggetto;
		}
		
		return toReturn;
	}
}
