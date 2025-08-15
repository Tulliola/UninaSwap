package utilities;

public enum GiornoEnum {
	Lunedì("Lunedì"),
	Martedì("Martedì"),
	Mercoledì("Mercoledì"),
	Giovedì("Giovedì"),
	Venerdì("Venerdì");
	
	private String giorno;
	
	private GiornoEnum(String giorno) {
		this.giorno = giorno;
	}
	
	public static GiornoEnum confrontaConStringa(String giorno) {
		for(GiornoEnum g : values()) {
			if(g.giorno.equals(giorno)) {
				return g;
			}
		}
		throw new IllegalArgumentException("Valore non presente nel database");
	}
	
	public String toString() {
		return giorno;
	}
};
