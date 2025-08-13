package eccezioni;

public class DescrizioneException extends RuntimeException {
	public DescrizioneException() {
		super();
	}
	
	public DescrizioneException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}

