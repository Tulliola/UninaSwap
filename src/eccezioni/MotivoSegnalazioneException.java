package eccezioni;

public class MotivoSegnalazioneException extends RuntimeException {
	public MotivoSegnalazioneException() {
		super();
	}
	
	public MotivoSegnalazioneException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
