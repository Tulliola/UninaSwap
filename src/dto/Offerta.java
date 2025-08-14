package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public abstract class Offerta {
	//Attributi propri
	private Timestamp momentoProposta;
	private String nota;
	private String sedeIncontro;
	private String indirizzoSpedizione;
	private String oraInizioIncontro;
	private String oraFineIncontro;
	private GiornoEnum giornoIncontro;
	private ModConsegnaEnum modalitaConsegnaScelta;
	private StatoOffertaEnum stato = StatoOffertaEnum.In_attesa;
	
	//Attributi derivati da relazioni
	private ProfiloUtente utenteProprietario;
	private ArrayList<UfficioPostale> ufficiRitiro;
	private SedeUniversita sedeDIncontroScelta;

	private Annuncio annuncioRiferito;
	
	public Offerta(Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito) {
		this.momentoProposta = momentoProposta;
		this.modalitaConsegnaScelta = modalitaConsegnaScelta;
		this.stato = stato;
		this.annuncioRiferito = annuncioRiferito;
	}
	
	public Offerta(ModConsegnaEnum modalitaConsegnaScelta, Annuncio annuncioRiferito) {
		this.modalitaConsegnaScelta = modalitaConsegnaScelta;
		this.annuncioRiferito = annuncioRiferito;
	}
	
	public SedeUniversita getSedeDIncontroScelta() {
		return sedeDIncontroScelta;
	}
	
	public void setSedeDIncontroScelta(SedeUniversita sedeDIncontroScelta) {
		this.sedeDIncontroScelta = sedeDIncontroScelta;
	}
	
	public Double getPrezzoOfferto() {
		return null;
	}
	
	public Integer getIdOfferta() {
		return null;
	}
	
	public ArrayList<Oggetto> getOggettiOfferti(){
		return null;
	}
	
	
	public Timestamp getMomentoProposta() {
		return momentoProposta;
	}

	public void setMomentoProposta(Timestamp momentoProposta) {
		this.momentoProposta = momentoProposta;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getSedeIncontro() {
		return sedeIncontro;
	}

	public void setSedeIncontro(String sedeIncontro) {
		this.sedeIncontro = sedeIncontro;
	}

	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}

	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public String getOraInizioIncontro() {
		return oraInizioIncontro;
	}

	public void setOraInizioIncontro(String oraInizioIncontro) {
		this.oraInizioIncontro = oraInizioIncontro;
	}

	public String getOraFineIncontro() {
		return oraFineIncontro;
	}

	public void setOraFineIncontro(String oraFineIncontro) {
		this.oraFineIncontro = oraFineIncontro;
	}

	public String getGiornoIncontro() {
		return giornoIncontro.toString();
	}

	public void setGiornoIncontro(GiornoEnum giornoIncontro) {
		this.giornoIncontro = giornoIncontro;
	}

	public String getModalitaConsegnaScelta() {
		return modalitaConsegnaScelta.toString();
	}

	public void setModalitaConsegnaScelta(ModConsegnaEnum modalitaConsegnaScelta) {
		this.modalitaConsegnaScelta = modalitaConsegnaScelta;
	}

	public String getStato() {
		return stato.toString();
	}

	public void setStato(StatoOffertaEnum stato) {
		this.stato = stato;
	}

	public ProfiloUtente getUtenteProprietario() {
		return utenteProprietario;
	}

	public void setUtenteProprietario(ProfiloUtente utenteProprietario) {
		this.utenteProprietario = utenteProprietario;
	}

	public ArrayList<UfficioPostale> getUfficiRitiro() {
		return ufficiRitiro;
	}

	public void setUfficiRitiro(ArrayList<UfficioPostale> ufficiRitiro) {
		this.ufficiRitiro = ufficiRitiro;
	}

	public Annuncio getAnnuncioRiferito() {
		return annuncioRiferito;
	}

	public void setAnnuncioRiferito(Annuncio annuncioRiferito) {
		this.annuncioRiferito = annuncioRiferito;
	}

}
