package eccezioni;

public class UsernameException extends RuntimeException{
	public UsernameException() {
		super();
	}
	
	public UsernameException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
