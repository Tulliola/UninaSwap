package eccezioni;

public class FotoException extends RuntimeException {
	public FotoException() {
		super();
	}
	
	public FotoException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
