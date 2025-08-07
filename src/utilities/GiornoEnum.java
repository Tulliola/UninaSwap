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
	
	public String toString() {
		return giorno;
	}
};

