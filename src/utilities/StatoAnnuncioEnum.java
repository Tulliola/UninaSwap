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
		
	
	public static StatoAnnuncioEnum confrontaConDB(String stato) {
		for(StatoAnnuncioEnum s: values()) {
			if(s.statoAnnuncio.equals(stato)) {
				return s;
			}
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
	
	@Override
	public String toString() {
		return statoAnnuncio;
	}
}
