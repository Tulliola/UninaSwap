package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaScambio extends Offerta {
	//Attributi propri
	int idOfferta;
	
	//Attributi derivati da relazioni
	private ArrayList<Oggetto> oggettiOfferti = new ArrayList()	;

	public OffertaScambio(int idOfferta, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito, ArrayList<Oggetto> oggettiOfferti) {
		super( momentoProposta, modalitaConsegnaScelta, stato, annuncioRiferito);
		this.idOfferta = idOfferta;
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
}
