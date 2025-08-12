package dto;

import utilities.CategoriaEnum;
import utilities.CondizioneEnum;

public class Oggetto {
	//Attributi propri
	private int idOggetto;
	private String descrizione;
	private CategoriaEnum cateogria;
	private CondizioneEnum condizioni;
	private byte[][] immagini = new byte[3][];
	private boolean disponibile;
	
	//Attributi derivati da relazioni
	private Annuncio annuncioContenente;

	
	
	public Oggetto(int idOggetto, CategoriaEnum cateogria, CondizioneEnum condizioni, byte[] immagine,
			boolean disponibile) {
		this.idOggetto = idOggetto;
		this.cateogria = cateogria;
		this.condizioni = condizioni;
		this.immagini[0] = immagine;
		this.disponibile = disponibile;
	}

	public void aggiungiImmagine(int index, byte[] immagine) {
		this.immagini[index] = immagine;
	}
	
	public void aggiungiImmagini(byte[][] immagini) {
		for(int i = 0; i < 3; i++) {
			this.immagini[i] = immagini[i];
		}
	}
	
	public void impostaOggettoInAnnuncio() {
		annuncioContenente.setOggettoInAnnuncio(this);
	}

	public int getIdOggetto() {
		return idOggetto;
	}

	public void setIdOggetto(int idOggetto) {
		this.idOggetto = idOggetto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public CategoriaEnum getCateogria() {
		return cateogria;
	}

	public void setCateogria(CategoriaEnum cateogria) {
		this.cateogria = cateogria;
	}

	public CondizioneEnum getCondizioni() {
		return condizioni;
	}

	public void setCondizioni(CondizioneEnum condizioni) {
		this.condizioni = condizioni;
	}

	public byte[][] getImmagini() {
		return immagini;
	}

	public void setImmagini(byte[][] immagini) {
		this.immagini = immagini;
	}

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public Annuncio getAnnuncioContenente() {
		return annuncioContenente;
	}

	public void setAnnuncioContenente(Annuncio annuncioContenente) {
		this.annuncioContenente = annuncioContenente;
	}
	
}
