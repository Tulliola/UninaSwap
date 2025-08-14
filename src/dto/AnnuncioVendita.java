package dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.StatoAnnuncioEnum;

public class AnnuncioVendita extends Annuncio {
	private double prezzoIniziale;
	
	public AnnuncioVendita(int idAnnuncio, boolean spedizione, boolean ritiroInPosta, boolean incontro,
			StatoAnnuncioEnum stato, Timestamp momentoPubblicazione, String nome, ProfiloUtente utenteProprietario,
			Oggetto oggettoInAnnuncio, double prezzoIniziale) {
		super(idAnnuncio, spedizione, ritiroInPosta, incontro, stato, momentoPubblicazione, nome, utenteProprietario,
				oggettoInAnnuncio);
		this.prezzoIniziale = prezzoIniziale;
	}
	
	public AnnuncioVendita(boolean spedizione, boolean ritiroInPosta, boolean incontro,
			StatoAnnuncioEnum stato, String nome, ProfiloUtente utenteProprietario,
			Oggetto oggettoInAnnuncio, double prezzoIniziale) {
		super(spedizione, ritiroInPosta, incontro, stato, nome, utenteProprietario,
				oggettoInAnnuncio);
		this.prezzoIniziale = prezzoIniziale;
	}

	@Override
	public Double getPrezzoIniziale() {
		return prezzoIniziale;
	}
	
	@Override
	public String toString() {
		String toReturn = super.toString();
		toReturn += "\nPrezzo iniziale annuncio = "+prezzoIniziale+"\n";
		
		return toReturn;
	}
}
