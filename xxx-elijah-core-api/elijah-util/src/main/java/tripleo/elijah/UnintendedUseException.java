package tripleo.elijah;

public class UnintendedUseException extends RuntimeException {
	public UnintendedUseException(String string) {
		super(string);
	}

	public UnintendedUseException() {
//		super();
	}
}
