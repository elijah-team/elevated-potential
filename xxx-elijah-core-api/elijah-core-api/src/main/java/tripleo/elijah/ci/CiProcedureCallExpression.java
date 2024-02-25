package tripleo.elijah.ci;

// FIXME wrap CiExpression and ExpressionList and ExpressionKind too
public interface CiProcedureCallExpression extends CiExpression {
	CiExpressionList getExpressionList();

	void setExpressionList(CiExpressionList ael);

	void setArgs(CiExpressionList aEl);

	CiExpressionList exprList();

	CiExpressionList getArgs();

	void identifier(CiExpression ee);

	@Override
	String printableString();

	@Override
	String toString();
}
