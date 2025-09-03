package dto;

import java.sql.Date;
import java.util.ArrayList;

import eccezioni.OffertaException;
import utilities.StatoAnnuncioEnum;
import utilities.StatoOffertaEnum;

public class ProfiloUtente {
	//Attributi propri
	private String username;
	private String email;
	private double saldo;
	private byte[] immagineProfilo;
	private String residenza;
	private String password;
	private Date dataSospensione;
	private boolean sospeso;
	
	//Attributi derivati da relazioni
	private ArrayList<Annuncio> annunciUtente = new ArrayList();
	private ArrayList<Offerta> offerteUtente = new ArrayList();
	
	public ProfiloUtente(String username, String email, double saldo, byte[] immagineProfilo, String residenza,
			String password, boolean sospeso) {
		this(username, email, residenza, password);
		this.saldo = saldo;
		this.immagineProfilo = immagineProfilo;
		this.sospeso = sospeso;
	}
	
	public ProfiloUtente(String username, String email, String residenza, String password) {
		this.username = username;
		this.email = email;
		this.residenza = residenza;
		this.password = password;
	}
	
	public void aggiungiAnnuncio(Annuncio annuncioAggiunto) {
		this.annunciUtente.add(annuncioAggiunto);
	}
	
	public void aggiungiOfferta(Offerta offertaAggiunta) {
		this.offerteUtente.add(offertaAggiunta);
	}

	public void aggiornaSaldo(double importoDaAggiungere) {
		this.saldo += importoDaAggiungere;
	}
	
	public void checkOffertaGiaEsistentePerUtente(int idAnnuncioRiferito) throws OffertaException{
		
		for(Offerta offertaCorrente : getOfferteInAttesa()) {		
			if(offertaCorrente.getAnnuncioRiferito().getIdAnnuncio() == idAnnuncioRiferito)
				throw new OffertaException("Hai già un'offerta attiva per questo annuncio. Ritirala o attendi che venga valutata dal venditore!");
		}
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public byte[] getImmagineProfilo() {
		return immagineProfilo;
	}

	public void setImmagineProfilo(byte[] immagineProfilo) {
		this.immagineProfilo = immagineProfilo;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDataSospensione() {
		return dataSospensione;
	}

	public void setDataSospensione(Date dataSospensione) {
		this.dataSospensione = dataSospensione;
	}

	public boolean isSospeso() {
		return sospeso;
	}

	public void setSospeso(boolean sospeso) {
		this.sospeso = sospeso;
	}

	public ArrayList<Annuncio> getAnnunciUtente() {
		return annunciUtente;
	}

	public void setAnnunciUtente(ArrayList<Annuncio> annunciUtente) {
		this.annunciUtente = annunciUtente;
	}

	public ArrayList<Offerta> getOfferteUtente() {
		return offerteUtente;
	}

	public void setOfferteUtente(ArrayList<Offerta> offerteUtente) {
		this.offerteUtente = offerteUtente;
	}
	
	@Override 
	public String toString() {
		String toReturn = "PROFILO UTENTE\n---------------------------------\n";
		
		toReturn += "Username = "+username+"\n";
		toReturn += "Email = "+email+"\n";
		toReturn += "Saldo = "+saldo+"\n";
		toReturn += "Residenza = "+residenza+"\n";
		toReturn += "Eventuale data sospensione = "+dataSospensione+"\n";
		toReturn += "Utente sospeso? = "+sospeso+"\n";
		
		return toReturn;
	}
	
	public ArrayList<Annuncio> getAnnunciDisponibili(){
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		for(Annuncio annuncio: annunciUtente) {
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Disponibile.toString()))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
	
	public ArrayList<Annuncio> getAnnunciUltimati(){
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		for(Annuncio annuncio: annunciUtente) {
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Venduto.toString()) || annuncio.getStato().equals(StatoAnnuncioEnum.Scambiato.toString()) || 
					annuncio.getStato().equals(StatoAnnuncioEnum.Regalato.toString()))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
	
	public ArrayList<Annuncio> getAnnunciScaduti(){
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		for(Annuncio annuncio: annunciUtente) {
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Scaduto.toString()))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
	
	public ArrayList<Annuncio> getAnnunciRimossi(){
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		for(Annuncio annuncio: annunciUtente) {
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Rimosso.toString()))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
	
	public ArrayList<Offerta> getOfferteInAttesa(){
		ArrayList<Offerta> toReturn = new ArrayList<Offerta>();
		
		for(Offerta offertaCorrente : this.offerteUtente)
			if(offertaCorrente.getStato().equals(StatoOffertaEnum.In_attesa.toString()))
				toReturn.add(offertaCorrente);
		
		return toReturn;
	}
	
	public ArrayList<Offerta> getOfferteAccettate(){
		ArrayList<Offerta> toReturn = new ArrayList();
		
		for(Offerta offertaCorrente: offerteUtente) {
			if(offertaCorrente.getStato().equals(StatoOffertaEnum.Accettata.toString()))
				toReturn.add(offertaCorrente);
		}
		
		return toReturn;
	}
	
	public ArrayList<Offerta> getOfferteRifiutate(){
		ArrayList<Offerta> toReturn = new ArrayList();
		
		for(Offerta offertaCorrente: offerteUtente) {
			if(offertaCorrente.getStato().equals(StatoOffertaEnum.Rifiutata.toString()))
				toReturn.add(offertaCorrente);
		}
		
		return toReturn;
	}
	
	public ArrayList<Offerta> getOfferteRitirate(){
		ArrayList<Offerta> toReturn = new ArrayList();
		
		for(Offerta offertaCorrente: offerteUtente) {
			if(offertaCorrente.getStato().equals(StatoOffertaEnum.Ritirata.toString()))
				toReturn.add(offertaCorrente);
		}
		
		return toReturn;
	}

	public ArrayList<Annuncio> getAnnunci() {
		return this.annunciUtente;
	}
}
