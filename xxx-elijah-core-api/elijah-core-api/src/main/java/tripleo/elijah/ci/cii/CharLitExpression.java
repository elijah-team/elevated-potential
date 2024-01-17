package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

public interface CharLitExpression extends CiExpression {
	@Override
	ExpressionKind getKind();

	@Override
	CiExpression getLeft();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aIncrement);

	@Override
	void setLeft(CiExpression iexpression);

	@Override
	String toString();
}
