package eccezioni;

public class OffertaException extends RuntimeException {
	public OffertaException() {
		super();
	}
	
	public OffertaException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
