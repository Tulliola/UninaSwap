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
		
	public Oggetto(int idOggetto, CategoriaEnum categoria, CondizioneEnum condizioni, byte[] immagine) {
		this(categoria, condizioni, immagine);
		this.idOggetto = idOggetto;
	}
	
	public Oggetto(CategoriaEnum categoria, CondizioneEnum condizioni, byte[] immagine) {
		this.categoria = categoria;
		this.condizioni = condizioni;
		this.immagini[0] = immagine;
	}
	

	public void aggiungiImmagine(int index, byte[] immagine) {
		if(immagine != null)
			this.immagini[index] = immagine;
	}
	
	public void aggiungiImmagini(byte[][] immagini) {
		for(int i = 0; i < 3; i++) {
			if(immagini[i] != null)
				this.immagini[i] = immagini[i];
		}
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

	
	@Override
	public String toString() {
		String toReturn = "OGGETTO\n--------------------------------\n";
		
		toReturn += "id Oggetto = "+idOggetto+"\n";
		toReturn += "Descrizione = "+descrizione+"\n";
		toReturn += "Categoria = "+categoria+"\n";
		toReturn += "Condizioni = "+condizioni+"\n";
		
		return toReturn;
	}
}
