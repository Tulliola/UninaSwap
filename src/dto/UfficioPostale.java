package dto;

public class UfficioPostale {
	private int idUfficio;
	private String nome;
	private String via;
	private String civico;
	private String comune;
	private String CAP;
	
	public UfficioPostale(int idUfficio, String nome, String via, String civico, String comune, String CAP) {
		this.idUfficio = idUfficio;
		this.nome = nome;
		this.via = via;
		this.civico = civico;
		this.comune = comune;
		this.CAP = CAP;
	}

	public int getIdUfficio() {
		return idUfficio;
	}

	public void setIdUfficio(int idUfficio) {
		this.idUfficio = idUfficio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCap() {
		return CAP;
	}

	public void setCap(String cap) {
		this.CAP = cap;
	}
	
	@Override
	public String toString() {
		return nome+" - " +via+ ", "+civico+", "+comune+", "+CAP;
	}
	
	@Override 
	public boolean equals(Object ufficio) {
		int idUfficio = ((UfficioPostale)ufficio).idUfficio;
		if(this.idUfficio == idUfficio) {
			return true;
		}
		else {
			return false;
		}
	}
}
