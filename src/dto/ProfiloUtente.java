package dto;

public class ProfiloUtente {
	private String username;
	private String email;
	private double saldo;
	private byte[] immagineProfilo;
	private String residenza;
	
	
	public ProfiloUtente(String username, String email, double saldo, String residenza, byte[] immagine_profilo) {
		this.username = username;
		this.email = email;
		this.saldo = saldo;
		this.residenza = residenza;
		this.immagineProfilo = immagine_profilo;
	}
	
	@Override
	public String toString() {
		return username;
	}
}
