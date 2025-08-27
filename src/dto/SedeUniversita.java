package dto;

public class SedeUniversita {
	private String nome;
	private int idSede;
	
	public SedeUniversita(String nome) {
		this.nome = nome;
	}
	
	public SedeUniversita(int idSede, String nome) {
		this.idSede = idSede;
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public int getIdSede() {
		return idSede;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	@Override
	public boolean equals(Object sede) {
		return this.nome.equals(((SedeUniversita)sede).getNome());
	}
}
