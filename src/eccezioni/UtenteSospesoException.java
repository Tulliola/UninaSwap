package eccezioni;

public class UtenteSospesoException extends RuntimeException {
	public UtenteSospesoException() {
		super();
	}
	
	public UtenteSospesoException(String messaggioErrore) {
		super(messaggioErrore);
	}
}
