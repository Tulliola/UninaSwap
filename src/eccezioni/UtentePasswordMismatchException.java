package eccezioni;


public class UtentePasswordMismatchException extends RuntimeException {
	public UtentePasswordMismatchException() {
		super();
	}
	
	public UtentePasswordMismatchException(String messaggioErrore) {
		super(messaggioErrore);
	}
}
