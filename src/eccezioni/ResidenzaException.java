package eccezioni;

public class ResidenzaException extends RuntimeException {
	public ResidenzaException() {
		super();
	}
	
	public ResidenzaException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
