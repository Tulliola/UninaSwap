package dto;


import java.sql.Date;
import java.sql.Timestamp;

import utilities.StatoAnnuncioEnum;

public abstract class Annuncio {
	private int idAnnuncio;
	private String emailProprietario;
	private Oggetto oggettoInAnnuncio;
	private boolean spedizione;
	private boolean ritiroInPosta;
	private boolean incontro;
	private StatoAnnuncioEnum stato;
	private Timestamp momentoPubblicazione;
	private String nome;
	private Date dataScadenza;
	
	public Annuncio(int idAnnuncio, String emailProprietario, Oggetto oggettoInAnnuncio, boolean spedizione, boolean ritiroInPosta, boolean incontro, StatoAnnuncioEnum stato, Timestamp momentoPubblicazione, String nome, Date dataScadenza) {
		this.idAnnuncio = idAnnuncio;
		this.emailProprietario = emailProprietario;
		this.oggettoInAnnuncio = oggettoInAnnuncio;
		this.spedizione = spedizione;
		this.ritiroInPosta = ritiroInPosta;
		this.incontro = incontro;
		this.stato = stato;
		this.momentoPubblicazione = momentoPubblicazione;
		this.nome = nome;
		this.dataScadenza = dataScadenza;
	}
	
	public int getIdAnnuncio() {
		return idAnnuncio;
	}

	public void setIdAnnuncio(int idAnnuncio) {
		this.idAnnuncio = idAnnuncio;
	}

	public String getEmailProprietario() {
		return emailProprietario;
	}

	public void setEmailProprietario(String emailProprietario) {
		this.emailProprietario = emailProprietario;
	}

	public Oggetto getOggettoInAnnuncio() {
		return oggettoInAnnuncio;
	}

	public void setOggettoInAnnuncio(Oggetto oggettoInAnnuncio) {
		this.oggettoInAnnuncio = oggettoInAnnuncio;
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
		momentoPubblicazione = momentoPubblicazione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	public abstract double getPrezzo();
	
	public abstract String getNotaScambio();
}
