package eccezioni;

public class PrezzoInizialeException extends RuntimeException {
	public PrezzoInizialeException() {
		super();
	}
	
	public PrezzoInizialeException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
