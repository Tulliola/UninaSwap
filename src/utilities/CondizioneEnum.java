package utilities;

public enum CondizioneEnum {
	Nuovo("Nuovo"),
	Come_nuovo("Usato - come nuovo"),
	Ottime_condizioni("Usato - ottime condizioni"),
	Buone_condizioni("Usato - buone condizioni"),
	Condizioni_accettabili("Usato - condizioni accettabili"),
	Usurato("Usato - usurato"),
	Ricondizionato("Ricondizionato");
		
	private String condizioni;
		
	CondizioneEnum(String condizioni) {
		this.condizioni = condizioni;
	}
		
	@Override
	public String toString() {
		return condizioni;
	}
};
