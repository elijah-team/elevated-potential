package tripleo.elijah.ci.cil;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.cii.SubExpression;

public class SubExpressionImpl extends CiAbstractExpression implements SubExpression {
	private final CiExpression carrier;

	public SubExpressionImpl(final CiExpression ee) {
		carrier = ee;
	}

	@Override
	public CiExpression getExpression() {
		return carrier;
	}

	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.SUBEXPRESSION;
	}

	@Override
	public boolean is_simple() {
		return true;
	}
}
