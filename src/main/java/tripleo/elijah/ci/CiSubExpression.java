package tripleo.elijah.ci;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;

public interface CiSubExpression extends CiExpression {
	CiExpression getExpression();

	@Override
    CiExpressionKind getKind();

	@Override
	boolean is_simple();
}
