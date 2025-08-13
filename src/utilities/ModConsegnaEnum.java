package utilities;

public enum ModConsegnaEnum {
	Spedizione("Spedizione"),
	Ritiro_in_posta("Ritiro in posta"),
	Incontro("Incontro");
		
	private String modalitàConsegna;
		
	ModConsegnaEnum(String modalitàConsegna) {
		this.modalitàConsegna = modalitàConsegna;
	}
		
	
	public static ModConsegnaEnum confrontaConDB(String modalitàConsegna) {
		for(ModConsegnaEnum m : values()) {
			if(m.modalitàConsegna.equals(modalitàConsegna)) {
				return m;
			}
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
	
	@Override
	public String toString() {
		return modalitàConsegna;
	}
}
