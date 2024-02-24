package tripleo.elijah.ci;

// FIXME wrap CiExpression and ExpressionList and ExpressionKind too
public interface CiProcedureCallExpression extends CiExpression {
	void identifier(CiExpression ee);

	void setExpressionList(CiExpressionList ael);

	CiExpressionList getExpressionList();

	CiExpressionList exprList();

	CiExpressionList getArgs();

	@Override String printableString();

	@Override String toString();
}
