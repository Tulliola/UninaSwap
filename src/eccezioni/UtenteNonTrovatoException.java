package eccezioni;

public class UtenteNonTrovatoException extends RuntimeException {
	public UtenteNonTrovatoException() {
		super();
	}
	
	public UtenteNonTrovatoException(String messaggioErrore) {
		super(messaggioErrore);
	}
}
