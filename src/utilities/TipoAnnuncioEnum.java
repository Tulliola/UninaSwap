package utilities;

public enum TipoAnnuncioEnum {
	Scambio("Scambio"),
	Vendita("Vendita"),
	Regalo("Regalo");
		
	private String tipoAnnuncio;
		
	TipoAnnuncioEnum(String tipoAnnuncio) {
		this.tipoAnnuncio = tipoAnnuncio;
	}
		
	@Override
	public String toString() {
		return tipoAnnuncio;
	}
}
