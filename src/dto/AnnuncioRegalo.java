package dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.StatoAnnuncioEnum;

public class AnnuncioRegalo extends Annuncio{
	
	public AnnuncioRegalo(int idAnnuncio, boolean spedizione, boolean ritiroInPosta, boolean incontro,
			StatoAnnuncioEnum stato, Timestamp momentoPubblicazione, String nome, ProfiloUtente utenteProprietario,
			Oggetto oggettoInAnnuncio) {
		super(idAnnuncio, spedizione, ritiroInPosta, incontro, stato, momentoPubblicazione, nome, utenteProprietario,
				oggettoInAnnuncio);
	}
	
	public AnnuncioRegalo(boolean spedizione, boolean ritiroInPosta, boolean incontro,
			StatoAnnuncioEnum stato, String nome, ProfiloUtente utenteProprietario,
			Oggetto oggettoInAnnuncio) {
		super(spedizione, ritiroInPosta, incontro, stato, nome, utenteProprietario,
				oggettoInAnnuncio);
	}
}
