package dto;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import utilities.GiornoEnum;
import utilities.StatoAnnuncioEnum;

public abstract class Annuncio {
	//Attributi propri
	private int idAnnuncio;
	private boolean spedizione;
	private boolean ritiroInPosta;
	private boolean incontro;
	private ArrayList<String> oraInizioIncontro;
	private ArrayList<String> oraFineIncontro;
	private ArrayList<GiornoEnum> giornoIncontro;
	private StatoAnnuncioEnum stato = StatoAnnuncioEnum.Disponibile;
	private Timestamp momentoPubblicazione;
	private String nome;
	private Date dataScadenza;

	//Attributi derivati da relazioni
	private ArrayList<Offerta> offerteRicevute;
	private ProfiloUtente utenteProprietario;
	private Oggetto oggettoInAnnuncio;
	private ArrayList<SedeUniversita> sedeIncontroProposte;
	
	public Annuncio(int idAnnuncio, boolean spedizione, boolean ritiroInPosta,
			boolean incontro, StatoAnnuncioEnum stato, Timestamp momentoPubblicazione, String nome, ProfiloUtente utenteProprietario, Oggetto oggettoInAnnuncio) {
		this.spedizione = spedizione;
		this.ritiroInPosta = ritiroInPosta;
		this.incontro = incontro;
		this.stato = stato;
		this.momentoPubblicazione = momentoPubblicazione;
		this.nome = nome;
		this.utenteProprietario = utenteProprietario;
		this.oggettoInAnnuncio = oggettoInAnnuncio;	
	}
	
	public Annuncio(boolean spedizione, boolean ritiroInPosta,
			boolean incontro, StatoAnnuncioEnum stato, String nome, ProfiloUtente utenteProprietario, Oggetto oggettoInAnnuncio) {
		this.spedizione = spedizione;
		this.ritiroInPosta = ritiroInPosta;
		this.incontro = incontro;
		this.stato = stato;
		this.nome = nome;
		this.utenteProprietario = utenteProprietario;
		this.oggettoInAnnuncio = oggettoInAnnuncio;	
	}
	
	
	public void aggiungiOffertaRicevuta(Offerta offertaRicevuta) {
		this.offerteRicevute.add(offertaRicevuta);
	}
	
	public void aggiungiPropostaIncontro(SedeUniversita sedeIncontroProposta, String oraInizioIncontro,
			String oraFineIncontro, GiornoEnum giornoIncontro) {
		if(!(isIncontroGiaProposto(sedeIncontroProposta, oraInizioIncontro, oraFineIncontro, giornoIncontro))){
			this.sedeIncontroProposte.add(sedeIncontroProposta);
			this.oraInizioIncontro.add(oraInizioIncontro);
			this.oraFineIncontro.add(oraFineIncontro);
			this.giornoIncontro.add(giornoIncontro);
		}
	}
	
	public void impostaDataScadenza(Date dataScadenza) {
		if(this.dataScadenza == null)
			this.dataScadenza = dataScadenza;
	}
	
	public boolean isIncontroGiaProposto(SedeUniversita sedeIncontroProposta, String oraInizioIncontro,
			String oraFineIncontro, GiornoEnum giornoIncontro) {
		if(this.sedeIncontroProposte.contains(sedeIncontroProposta) && this.oraInizioIncontro.contains(oraInizioIncontro) &&
				this.oraFineIncontro.contains(oraFineIncontro) && this.giornoIncontro.contains(giornoIncontro))
			return true;
		else
			return false;
	}


	public int getIdAnnuncio() {
		return idAnnuncio;
	}


	public void setIdAnnuncio(int idAnnuncio) {
		this.idAnnuncio = idAnnuncio;
	}


	public boolean isSpedizione() {
		return spedizione;
	}


	public void setSpedizione(boolean spedizione) {
		this.spedizione = spedizione;
	}


	public boolean isRitiroInPosta() {
		return ritiroInPosta;
	}


	public void setRitiroInPosta(boolean ritiroInPosta) {
		this.ritiroInPosta = ritiroInPosta;
	}


	public boolean isIncontro() {
		return incontro;
	}


	public void setIncontro(boolean incontro) {
		this.incontro = incontro;
	}


	public StatoAnnuncioEnum getStato() {
		return stato;
	}


	public void setStato(StatoAnnuncioEnum stato) {
		this.stato = stato;
	}


	public Timestamp getMomentoPubblicazione() {
		return momentoPubblicazione;
	}


	public void setMomentoPubblicazione(Timestamp momentoPubblicazione) {
		this.momentoPubblicazione = momentoPubblicazione;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public ProfiloUtente getUtenteProprietario() {
		return utenteProprietario;
	}


	public void setUtenteProprietario(ProfiloUtente utenteProprietario) {
		this.utenteProprietario = utenteProprietario;
	}


	public Oggetto getOggettoInAnnuncio() {
		return oggettoInAnnuncio;
	}


	public void setOggettoInAnnuncio(Oggetto oggettoInAnnuncio) {
		this.oggettoInAnnuncio = oggettoInAnnuncio;
	}


	public ArrayList<String> getOraInizioIncontro() {
		return oraInizioIncontro;
	}


	public void setOraInizioIncontro(ArrayList<String> oraInizioIncontro) {
		this.oraInizioIncontro = oraInizioIncontro;
	}


	public ArrayList<String> getOraFineIncontro() {
		return oraFineIncontro;
	}


	public void setOraFineIncontro(ArrayList<String> oraFineIncontro) {
		this.oraFineIncontro = oraFineIncontro;
	}


	public ArrayList<GiornoEnum> getGiornoIncontro() {
		return giornoIncontro;
	}


	public void setGiornoIncontro(ArrayList<GiornoEnum> giornoIncontro) {
		this.giornoIncontro = giornoIncontro;
	}


	public Date getDataScadenza() {
		return dataScadenza;
	}


	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}


	public ArrayList<Offerta> getOfferteRicevute() {
		return offerteRicevute;
	}


	public void setOfferteRicevute(ArrayList<Offerta> offerteRicevute) {
		this.offerteRicevute = offerteRicevute;
	}


	public ArrayList<SedeUniversita> getSedeIncontroProposte() {
		return sedeIncontroProposte;
	}


	public void setSedeIncontroProposte(ArrayList<SedeUniversita> sedeIncontroProposte) {
		this.sedeIncontroProposte = sedeIncontroProposte;
	}
	
	public double getPrezzoIniziale() {
		return -1.0;
	}
	
	public String getNotaScambio() {
		return null;
	}
}
