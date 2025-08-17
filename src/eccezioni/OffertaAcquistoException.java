package eccezioni;

public class OffertaAcquistoException extends RuntimeException {
	public OffertaAcquistoException() {
		super();
	}
	
	public OffertaAcquistoException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
