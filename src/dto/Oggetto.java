package dto;

import utilities.CategoriaEnum;
import utilities.CondizioneEnum;

public class Oggetto {
	private int idOggetto;
	private String emailProprietario;
	private String descrizione;
	private CategoriaEnum categoria;
	private CondizioneEnum condizioni;
	
	public Oggetto(int idOggetto, String emailProprietariom, String descrizione, CategoriaEnum categoria, CondizioneEnum condizioni) {
		this.idOggetto = idOggetto;
		this.emailProprietario = emailProprietario;
		this.descrizione = descrizione;
		this.categoria = categoria;
		this.condizioni = condizioni;
	}
}
