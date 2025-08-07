package utilities;

public enum StatoOffertaEnum {
	Accettata("Accettata"),
	Rifiutata("Rifiutata"),
	In_attesa("In attesa"),
	Ritirata("Ritirata");
		
	private String statoOfferta;
		
	StatoOffertaEnum(String statoOfferta) {
		this.statoOfferta = statoOfferta;
	}
		
	@Override
	public String toString() {
		return statoOfferta;
	}
};
