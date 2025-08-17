package eccezioni;

public class OggettoException extends RuntimeException {
	public OggettoException() {
		super();
	}
	
	public OggettoException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
