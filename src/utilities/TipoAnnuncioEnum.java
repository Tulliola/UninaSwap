package utilities;

public enum TipoAnnuncioEnum {
	Scambio("Scambio"),
	Vendita("Vendita"),
	Regalo("Regalo");
		
	private String tipoAnnuncio;
		
	TipoAnnuncioEnum(String tipoAnnuncio) {
		this.tipoAnnuncio = tipoAnnuncio;
	}
		
	public static TipoAnnuncioEnum confrontaConDB(String tipoAnnuncio) {
		for(TipoAnnuncioEnum tA : values()) {
			if(tA.tipoAnnuncio.equals(tipoAnnuncio)) {
				return tA;
			}
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
	
	@Override
	public String toString() {
		return tipoAnnuncio;
	}
}
