package tripleo.elijah.ci;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

public interface CiCharLitExpression extends CiExpression {
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
