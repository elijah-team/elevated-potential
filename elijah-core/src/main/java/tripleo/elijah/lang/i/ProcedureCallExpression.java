package tripleo.elijah.lang.i;

public interface ProcedureCallExpression extends IExpression {
	ExpressionList getArgs();

	void identifier(IExpression ee);

	String printableString();

	void setArgs(ExpressionList aExpl);

	@Override
	String toString();
}
