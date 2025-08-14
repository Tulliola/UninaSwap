package dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.StatoAnnuncioEnum;

public class AnnuncioScambio extends Annuncio {
	private String notaScambio;
	
	public AnnuncioScambio(int idAnnuncio, boolean spedizione, boolean ritiroInPosta, boolean incontro,
			StatoAnnuncioEnum stato, Timestamp momentoPubblicazione, String nome, ProfiloUtente utenteProprietario,
			Oggetto oggettoInAnnuncio, String notaScambio) {
		super(idAnnuncio, spedizione, ritiroInPosta, incontro, stato, momentoPubblicazione, nome, utenteProprietario,
				oggettoInAnnuncio);
		this.notaScambio = notaScambio;
	}
	
	public AnnuncioScambio(boolean spedizione, boolean ritiroInPosta, boolean incontro,
			StatoAnnuncioEnum stato, String nome, ProfiloUtente utenteProprietario,
			Oggetto oggettoInAnnuncio, String notaScambio) {
		super(spedizione, ritiroInPosta, incontro, stato, nome, utenteProprietario,
				oggettoInAnnuncio);
		this.notaScambio = notaScambio;
	}

	@Override
	public String getNotaScambio() {
		return notaScambio;
	}
	
	@Override
	public String toString() {
		String toReturn = super.toString();
		toReturn += "\nNota Scambio"+notaScambio+"\n";
		
		return toReturn;
	}
}
