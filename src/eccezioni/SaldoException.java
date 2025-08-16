package eccezioni;

public class SaldoException extends RuntimeException {
	public SaldoException() {
		super();
	}
	
	public SaldoException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
