package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

public interface BasicBinaryExpression extends IBinaryExpression {
	@Override
	void setKind(ExpressionKind aKind);

	@Override
	void setLeft(CiExpression aLeft);

	void shift(ExpressionKind aType);

	@Override
	String toString();
}
