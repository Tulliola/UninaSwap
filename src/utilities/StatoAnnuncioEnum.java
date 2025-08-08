package utilities;

public enum StatoAnnuncioEnum {
	Disponibile("Disponibile"),
	Indisponibile("Indisponibile"),
	Venduto("Venduto"),
	Scambiato("Scambiato"),
	Regalato("Regalato"),
	Scaduto("Scaduto"),
	Rimosso("Rimosso");
		
	private String statoAnnuncio;
		
	StatoAnnuncioEnum(String statoAnnuncio) {
		this.statoAnnuncio = statoAnnuncio;
	}
		
	@Override
	public String toString() {
		return statoAnnuncio;
	}
}
