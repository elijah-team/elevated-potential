package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

public interface SubExpression extends CiExpression {
	CiExpression getExpression();

	@Override
	ExpressionKind getKind();

	@Override
	boolean is_simple();
}
