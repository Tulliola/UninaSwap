package eccezioni;

public class NotaScambioException extends RuntimeException {
	public NotaScambioException() {
		super();
	}
	
	public NotaScambioException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
