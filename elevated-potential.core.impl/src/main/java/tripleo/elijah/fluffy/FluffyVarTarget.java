package tripleo.elijah.fluffy;

public interface FluffyVarTarget {
	/**
	 * MEMBER means class or namespace<br/>
	 * FUNCTION means a function or something "under" it (loop, etc)<br/>
	 * <br/>
	 * ARGUMENT means a function argument (not used...)
	 */
	enum Ty {
		FUNCTION, MEMBER
	}

	Ty getTy();

}
