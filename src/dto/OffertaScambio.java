package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaScambio extends Offerta {
	//Attributi propri
	
	//Attributi derivati da relazioni
	private ArrayList<Oggetto> oggettiOfferti;

	public OffertaScambio(int idOfferta, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Oggetto oggettoOfferto) {
		super(idOfferta, momentoProposta, modalitaConsegnaScelta, stato);
		this.oggettiOfferti.add(oggettoOfferto);
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
}
