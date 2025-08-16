package dto;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import utilities.GiornoEnum;
import utilities.StatoAnnuncioEnum;

public abstract class Annuncio {
	//Attributi propri
	private int idAnnuncio;
	private boolean spedizione;
	private boolean ritiroInPosta;
	private boolean incontro;
	private StatoAnnuncioEnum stato = StatoAnnuncioEnum.Disponibile;
	private Timestamp momentoPubblicazione;
	private String nome;
	private Date dataScadenza;

	//Attributi derivati da relazioni
	private ArrayList<Offerta> offerteRicevute = new ArrayList();
	private ProfiloUtente utenteProprietario;
	private Oggetto oggettoInAnnuncio;
	private ArrayList<SedeUniversita> sedeIncontroProposte = new ArrayList();
	private ArrayList<String> oraInizioIncontro = new ArrayList();
	private ArrayList<String> oraFineIncontro = new ArrayList();
	private ArrayList<GiornoEnum> giornoIncontro = new ArrayList();
	
	//Attributi per le interazioni
	private Random generatoreCasualeDiInterazioni = new Random();
	private int numeroDiInterazioni = generatoreCasualeDiInterazioni.nextInt(200 - 0 + 1);
	
	//Costruttore per la costruzione di oggetti durante la retrieve
	public Annuncio(int idAnnuncio, boolean spedizione, boolean ritiroInPosta,
			boolean incontro, StatoAnnuncioEnum stato, Timestamp momentoPubblicazione, String nome, ProfiloUtente utenteProprietario, Oggetto oggettoInAnnuncio) {
		this.idAnnuncio = idAnnuncio;
		this.spedizione = spedizione;
		this.ritiroInPosta = ritiroInPosta;
		this.incontro = incontro;
		this.stato = stato;
		this.momentoPubblicazione = momentoPubblicazione;
		this.nome = nome;
		this.utenteProprietario = utenteProprietario;
		this.oggettoInAnnuncio = oggettoInAnnuncio;	
	}
	
	//Costruzione per la costruzione di oggetti durante la create
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

	//Metodo che aggiunge eventuali offerte all'annuncio
	public void aggiungiOffertaRicevuta(Offerta offertaRicevuta) {
		this.offerteRicevute.add(offertaRicevuta);
	}
	
	//Metodo che aggiunge proposte di incontro all'annuncio, se questa opportunità è offerta
	public void aggiungiPropostaIncontro(SedeUniversita sedeIncontroProposta, String oraInizioIncontro,
			String oraFineIncontro, GiornoEnum giornoIncontro) {
		if(incontro) {
			this.sedeIncontroProposte.add(sedeIncontroProposta);
			this.oraInizioIncontro.add(oraInizioIncontro);
			this.oraFineIncontro.add(oraFineIncontro);
			this.giornoIncontro.add(giornoIncontro);
		}
	}
	
	//Metodo che setta l'eventuale scadenza dell'annuncio
	public void impostaDataScadenza(Date dataScadenza) {
		if(this.dataScadenza == null)
			this.dataScadenza = dataScadenza;
	}
	
	//Getter e setter
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
	
	public Double getPrezzoIniziale() {
		return null;
	}
	
	public String getNotaScambio() {
		return null;
	}
	
	public int getNumeroInterazioni() {
		return this.numeroDiInterazioni;
	}
	
	public String getIncontro(int i) {
		if(i >= 0 && i < this.giornoIncontro.size()) {
			String incontro = this.giornoIncontro.get(i) + ", " + "dalle " + this.oraInizioIncontro.get(i) + " alle " + this.oraFineIncontro.get(i) + " a " + this.sedeIncontroProposte.get(i);
			return incontro;
		}
		else
			throw new IllegalArgumentException("Indice non valido");
	}
	
	@Override
	public String toString() {
		String toReturn = "ANNUNCIO\n-----------------------------------------\n";
		toReturn += "Id Annuncio = "+idAnnuncio+"\n";
		toReturn += "Spedizione = "+spedizione+"\n";
		toReturn += "Ritiro in posta = "+ritiroInPosta+"\n";
		toReturn += "Incontro = "+incontro+"\n";
		toReturn += "Stato = "+stato+"\n";
		toReturn += "Momento pubblicazione = "+momentoPubblicazione+"\n";
		toReturn += "Nome = "+nome+"\n";
		toReturn += "Data scadenza = "+dataScadenza+"\n";
		
		toReturn += "Offerte a questo annuncio= \n";
		for(Offerta offerta: offerteRicevute)
			toReturn += offerta;
		
		toReturn += "Utente proprietario dell'annuncio = \n" + utenteProprietario;
		toReturn += "Oggetto riguardante: \n" + oggettoInAnnuncio;
		
		toReturn += "Sedi proposte = \n";
		for(SedeUniversita sede: sedeIncontroProposte) {
			toReturn += sede;
			toReturn += "Ora inizio incontro = "+oraInizioIncontro+"\n";
			toReturn += "Ora fine incontro = "+oraFineIncontro+"\n";
			toReturn += "Giorno incontro = "+giornoIncontro+"\n";
		}
				
		return toReturn;
	}

}
