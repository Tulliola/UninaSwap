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
	
	public static StatoOffertaEnum confrontaConDB(String statoOfferta) {
		for(StatoOffertaEnum sO : values()) {
			if(sO.statoOfferta.equals(statoOfferta)) {
				return sO;
			}
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
		
	@Override
	public String toString() {
		return statoOfferta;
	}
};
