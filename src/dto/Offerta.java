package dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.ModConsegnaEnum;
import utilities.StatoOffertaEnum;

public abstract class Offerta {
	//Attributi propri
	protected Timestamp momentoProposta;
	protected String nota;
	protected String indirizzoSpedizione;
	protected String oraInizioIncontro;
	protected String oraFineIncontro;
	protected GiornoEnum giornoIncontro;
	protected ModConsegnaEnum modalitaConsegnaScelta;
	protected StatoOffertaEnum stato = StatoOffertaEnum.In_attesa;
	
	//Attributi derivati da relazioni
	protected ProfiloUtente utenteProprietario;
	protected UfficioPostale ufficioRitiro;
	protected SedeUniversita sedeDIncontroScelta;

	protected Annuncio annuncioRiferito;
	protected String messaggioMotivazionale;
	
	public Offerta(ProfiloUtente offerente, Timestamp momentoProposta, ModConsegnaEnum modalitaConsegnaScelta,
			StatoOffertaEnum stato, Annuncio annuncioRiferito) {
		this.utenteProprietario = offerente;
		this.momentoProposta = momentoProposta;
		this.modalitaConsegnaScelta = modalitaConsegnaScelta;
		this.stato = stato;
		this.annuncioRiferito = annuncioRiferito;
	}
	
	public Offerta(ProfiloUtente offerente, ModConsegnaEnum modalitaConsegnaScelta, Annuncio annuncioRiferito) {
		this.utenteProprietario = offerente;
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

	public UfficioPostale getUfficioRitiro() {
		return ufficioRitiro;
	}

	public void setUfficioRitiro(UfficioPostale ufficioRitiro) {
		this.ufficioRitiro = ufficioRitiro;
	}

	public Annuncio getAnnuncioRiferito() {
		return annuncioRiferito;
	}

	public void setAnnuncioRiferito(Annuncio annuncioRiferito) {
		this.annuncioRiferito = annuncioRiferito;
	}
	
	public String getMessaggioMotivazionale() {
		return messaggioMotivazionale;
	}
	
	public void setMessaggioMotivazionale(String messaggioMotivazionale) {
		this.messaggioMotivazionale = messaggioMotivazionale;
	}
	@Override
	public String toString() {
		String toReturn = "OFFERTA\n--------------------------------------\n";
		
		toReturn += "Momento proposta = "+momentoProposta+"\n";
		toReturn += "Nota = "+nota+"\n";
//		toReturn += "Sede Incontro = "+sedeIncontro+"\n";
		toReturn += "Indirizzo spedizione = "+indirizzoSpedizione+"\n";
		toReturn += "Ora inizio incontro scelta = "+oraInizioIncontro+"\n";
		toReturn += "Ora fine incontro scelta = "+oraFineIncontro+"\n";
		toReturn += "Giorno incontro scelto = "+giornoIncontro+"\n";
		toReturn += "Modalita consegna scelta = "+modalitaConsegnaScelta+"\n";
		toReturn += "Stato = "+stato+"\n";
		toReturn += "Utente offerente = "+utenteProprietario+"\n";
		
		toReturn += "Ufficio scelto = " +ufficioRitiro+"\n";
		
		toReturn += "Sede incontro scelta = "+sedeDIncontroScelta+"\n";
		toReturn += "Annuncio riferito = "+annuncioRiferito+"\n";
		
		return toReturn;
	}
	
}
