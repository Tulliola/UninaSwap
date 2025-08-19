package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public class OffertaRegalo extends Offerta {
	public OffertaRegalo(ProfiloUtente offerente, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito) {
		super(offerente, momentoProposta, modalitaConsegnaScelta, stato, annuncioRiferito);
	}
	
	public OffertaRegalo(ProfiloUtente offerente, ModConsegnaEnum modalitaConsegnaScelta, Annuncio annuncioRiferito) {
		super(offerente, modalitaConsegnaScelta, annuncioRiferito);
	}
	@Override
	public String toString() {
		String toReturn = super.toString();
		
		toReturn += "\nPrezzo offerto = "+"\n";
		toReturn += "Oggetti offerti = \n";
		
		return toReturn;
	}
}
