package eccezioni;

public class MatricolaNonTrovataException extends RuntimeException {
	public MatricolaNonTrovataException() {
		super();
	}
	
	public MatricolaNonTrovataException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
