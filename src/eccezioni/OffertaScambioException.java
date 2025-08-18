package eccezioni;

public class OffertaScambioException extends RuntimeException {
	public OffertaScambioException() {
		super();
	}
	
	public OffertaScambioException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
