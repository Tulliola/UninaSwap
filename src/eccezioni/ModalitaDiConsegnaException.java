package eccezioni;

public class ModalitaDiConsegnaException extends RuntimeException {
	public ModalitaDiConsegnaException() {
		super();
	}
	
	public ModalitaDiConsegnaException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
