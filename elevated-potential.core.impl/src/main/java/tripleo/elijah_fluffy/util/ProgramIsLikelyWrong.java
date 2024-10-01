package tripleo.elijah_fluffy.util;

public class ProgramIsLikelyWrong extends RuntimeException {
	public ProgramIsLikelyWrong(final String aS) {
		super(aS);
	}

	public ProgramIsLikelyWrong() {
		super();
	}
}
