package dto;

import java.sql.Date;
import java.util.ArrayList;

import utilities.StatoAnnuncioEnum;

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
		this.username = username;
		this.email = email;
		this.saldo = saldo;
		this.immagineProfilo = immagineProfilo;
		this.residenza = residenza;
		this.password = password;
		this.sospeso = sospeso;
	}
	
	public void aggiungiAnnuncio(Annuncio annuncioAggiunto) {
		this.annunciUtente.add(annuncioAggiunto);
	}
	
	public void aggiungiOfferta(Offerta offertaAggiunta) {
		this.offerteUtente.add(offertaAggiunta);
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
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Disponibile))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
	
	public ArrayList<Annuncio> getAnnunciUltimati(){
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		for(Annuncio annuncio: annunciUtente) {
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Venduto) || annuncio.getStato().equals(StatoAnnuncioEnum.Scambiato) || annuncio.getStato().equals(StatoAnnuncioEnum.Regalato))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
	
	public ArrayList<Annuncio> getAnnunciScaduti(){
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		for(Annuncio annuncio: annunciUtente) {
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Scaduto))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
	
	public ArrayList<Annuncio> getAnnunciRimossi(){
		ArrayList<Annuncio> toReturn = new ArrayList();
		
		for(Annuncio annuncio: annunciUtente) {
			if(annuncio.getStato().equals(StatoAnnuncioEnum.Rimosso))
				toReturn.add(annuncio);
		}
		
		return toReturn;
	}
}
