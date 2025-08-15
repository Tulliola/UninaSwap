package dto;

import utilities.CategoriaEnum;
import utilities.CondizioneEnum;

public class Oggetto {
	//Attributi propri
	private int idOggetto;
	private String descrizione;
	private CategoriaEnum categoria;
	private CondizioneEnum condizioni;
	private byte[][] immagini = new byte[3][];
	private boolean disponibile = true;
	
	//Attributi derivati da relazioni
	private Annuncio annuncioContenente;

	
	
	public Oggetto(int idOggetto, CategoriaEnum categoria, CondizioneEnum condizioni, byte[] immagine,
			boolean disponibile) {
		this.idOggetto = idOggetto;
		this.categoria = categoria;
		this.condizioni = condizioni;
		this.immagini[0] = immagine;
		this.disponibile = disponibile;
	}
	
	public Oggetto(CategoriaEnum categoria, CondizioneEnum condizioni, byte[] immagine,
			boolean disponibile) {
		this.categoria = categoria;
		this.condizioni = condizioni;
		this.immagini[0] = immagine;
		this.disponibile = disponibile;
	}

	public void aggiungiImmagine(int index, byte[] immagine) {
		if(immagine != null)
			this.immagini[index] = immagine;
	}
	
	public void aggiungiImmagini(byte[][] immagini, int numeroImmaginiDaInserire, int numeroImmaginiGiaPresenti) {
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

	public String getCategoria() {
		return categoria.toString();
	}
	
	public CategoriaEnum getCategoriaEnum() {
		return categoria;
	}

	public void setCategoria(CategoriaEnum cateogria) {
		this.categoria = cateogria;
	}

	public String getCondizioni() {
		return condizioni.toString();
	}
	
	public CondizioneEnum getCondizioniEnum() {
		return condizioni;
	}

	public void setCondizioni(CondizioneEnum condizioni) {
		this.condizioni = condizioni;
	}

	public byte[][] getImmagini() {
		return immagini;
	}
	
	public byte[] getImmagine(int indiceImmagine) {
		if(indiceImmagine >= 0 && indiceImmagine <= 2)
			return immagini[indiceImmagine];
		else
			throw new IllegalArgumentException("Indice non valido");
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
	
	@Override
	public String toString() {
		String toReturn = "OGGETTO\n--------------------------------\n";
		
		toReturn += "id Oggetto = "+idOggetto+"\n";
		toReturn += "Descrizione = "+descrizione+"\n";
		toReturn += "Categoria = "+categoria+"\n";
		toReturn += "Condizioni = "+condizioni+"\n";
		toReturn += "Disponibile? = "+disponibile+"\n";
		toReturn += "Annuncio in cui è eventualmente contenuto = "+annuncioContenente+"\n";
		
		return toReturn;
	}
}
