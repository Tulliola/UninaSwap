package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public abstract class Offerta {
	//Attributi propri
	private int idOfferta;
	private Timestamp momentoProposta;
	private String nota;
	private String sedeIncontro;
	private String indirizzoSpedizione;
	private String oraInizioIncontro;
	private String oraFineIncontro;
	private GiornoEnum giornoIncontro;
	private ModConsegnaEnum modalitaConsegnaScelta;
	private StatoOffertaEnum stato;
	
	//Attributi derivati da relazioni
	private UfficioPostale ufficioDiRitiro;
	private ProfiloUtente utenteProprietario;
	private ArrayList<UfficioPostale> ufficiRitiro;
	private Annuncio annuncioRiferito;
	public Offerta(int idOfferta, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato) {
		this.idOfferta = idOfferta;
		this.momentoProposta = momentoProposta;
		this.modalitaConsegnaScelta = modalitaConsegnaScelta;
		this.stato = stato;
	}
	
	public double getPrezzoOfferto() {
		return -1.0;
	}
	
	public ArrayList<Oggetto> getOggettiOfferti(){
		return null;
	}
}
