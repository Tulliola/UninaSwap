package eccezioni;

public class NomeAnnuncioException extends RuntimeException {
	public NomeAnnuncioException() {
		super();
	}
	
	public NomeAnnuncioException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
