package dto;

public class ProfiloUtente {
	private String username;
	private String email;
	private double saldo;
	private byte[] immagineProfilo;
	private String residenza;
	private String password;
	
	public ProfiloUtente(String username, String email, double saldo, String residenza, byte[] immagine_profilo, String password) {
		this.username = username;
		this.email = email;
		this.saldo = saldo;
		this.residenza = residenza;
		this.immagineProfilo = immagine_profilo;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return username;
	}


	//Getter e Setter per username
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	//Getter e Setter per email
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
}
