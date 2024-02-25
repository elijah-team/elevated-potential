package tripleo.elijah.ci;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

public interface CiSubExpression extends CiExpression {
	CiExpression getExpression();

	@Override
	ExpressionKind getKind();

	@Override
	boolean is_simple();
}
