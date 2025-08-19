package eccezioni;

public class OffertaRegaloException extends RuntimeException {
	public OffertaRegaloException() {
		super();
	}
	
	public OffertaRegaloException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
