package tripleo.elijah.lang.i;

/*
 * Turns an expression into an element
 */
public interface StatementWrapper extends OS_Element {
	/**
	 * @return the expr
	 */
	IExpression getExpr();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	@Override
	String toString();
}
