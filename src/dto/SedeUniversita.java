package dto;

public class SedeUniversita {
	private String nome;
	
	public SedeUniversita(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
