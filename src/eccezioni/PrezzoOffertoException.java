package eccezioni;

public class PrezzoOffertoException extends Exception {
	public PrezzoOffertoException() {
		super();
	}
	
	public PrezzoOffertoException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
