package tripleo.elijah.ci;

// FIXME wrap CiExpression and ExpressionList and ExpressionKind too
public interface CiProcedureCallExpression extends CiExpression {
	CiExpressionList exprList();

	CiExpressionList getExpressionList();

	void identifier(CiExpression ee);

	void setExpressionList(CiExpressionList ael);

	void setArgs(CiExpressionList aEl);
}
