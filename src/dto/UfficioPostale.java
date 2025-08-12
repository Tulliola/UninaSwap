package dto;

public class UfficioPostale {
	private int idUfficio;
	private String nome;
	private String Via;
	private String civico;
	private String comune;
	private String cap;
	
	public UfficioPostale(int idUfficio, String nome, String via, String civico, String comune, String cap) {
		super();
		this.idUfficio = idUfficio;
		this.nome = nome;
		Via = via;
		this.civico = civico;
		this.comune = comune;
		this.cap = cap;
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
		return Via;
	}

	public void setVia(String via) {
		Via = via;
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
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
}
