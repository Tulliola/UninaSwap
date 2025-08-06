package eccezioni;

public class NomeSchemaException extends Exception{
	public NomeSchemaException() {
		super();
	}
	
	public NomeSchemaException(String messaggioDiErrore) {
		super(messaggioDiErrore);
	}
}
