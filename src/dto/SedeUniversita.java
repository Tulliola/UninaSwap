package dto;

public class SedeUniversita {
	private int idSede;
	private String nome;
	private String Indirizzo;
	
	public SedeUniversita(String nome, String indirizzo) {
		this.idSede = idSede;
		this.nome = nome;
		Indirizzo = indirizzo;
	}
	
	public SedeUniversita(int idSede, String nome, String indirizzo) {
		this.idSede = idSede;
		this.nome = nome;
		Indirizzo = indirizzo;
	}

	public int getIdSede() {
		return idSede;
	}

	public void setIdSede(int idSede) {
		this.idSede = idSede;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return Indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		Indirizzo = indirizzo;
	}
}
