package eccezioni;

public class EmailException extends RuntimeException {
	public EmailException() {
		super();
	}
	
	public EmailException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
