package tripleo.elijah.util;

public class ProgramIsLikelyWrong extends RuntimeException {
	public ProgramIsLikelyWrong(final String aS) {
		super(aS);
	}

	public ProgramIsLikelyWrong() {
		super();
	}
}
