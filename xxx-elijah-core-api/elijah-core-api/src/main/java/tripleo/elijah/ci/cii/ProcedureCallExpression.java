package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;

public interface ProcedureCallExpression extends CiExpression {
	CiExpressionList exprList();

	CiExpressionList getArgs();

	void identifier(CiExpression ee);

	@Override
	String printableString();

	void setArgs(CiExpressionList aExpl);

	@Override
	String toString();
}
