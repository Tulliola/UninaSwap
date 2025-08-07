package eccezioni;

public class PasswordException extends RuntimeException {
	public PasswordException() {
		super();
	}
	
	public PasswordException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
