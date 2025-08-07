package utilities;

public enum ModConsegnaEnum {
	Spedizione("Spedizione"),
	Ritiro_in_posta("Ritiro in posta"),
	Incontro("Incontro");
		
	private String modalitàConsegna;
		
	ModConsegnaEnum(String modalitàConsegna) {
		this.modalitàConsegna = modalitàConsegna;
	}
		
	@Override
	public String toString() {
		return modalitàConsegna;
	}
}
