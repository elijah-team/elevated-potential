package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

public interface FloatExpression extends CiExpression {
	@Override
	ExpressionKind getKind();

	@Override
	CiExpression getLeft();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aType);

	@Override
	void setLeft(CiExpression aLeft);

	@Override
	String toString();
}
