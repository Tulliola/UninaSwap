package utilities;

public enum CondizioneEnum {
	Nuovo("Nuovo"),
	Usato_Come_nuovo("Usato - come nuovo"),
	Ottime_condizioni("Usato - ottime condizioni"),
	Buone_condizioni("Usato - buone condizioni"),
	Condizioni_accettabili("Usato - condizioni accettabili"),
	Usurato("Usato - usurato"),
	Ricondizionato("Ricondizionato");
		
	private String condizioni;
		
	CondizioneEnum(String condizioni) {
		this.condizioni = condizioni;
	}
	
	public static CondizioneEnum confrontaConStringa(String condizioni) {
		for(CondizioneEnum c : values()) {
			if(c.condizioni.equals(condizioni)) {
				return c;
			}
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
		
	@Override
	public String toString() {
		return condizioni;
	}
};
