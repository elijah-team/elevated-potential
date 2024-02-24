package tripleo.elijah.ci_impl;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiSubExpression;

public class CiSubExpressionImpl extends CiAbstractExpression implements CiSubExpression {
	private final CiExpression carrier;

	public CiSubExpressionImpl(final CiExpression ee) {
		carrier = ee;
	}

	@Override
	public CiExpression getExpression() {
		return carrier;
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.SUBEXPRESSION;
	}

	@Override
	public boolean is_simple() {
		return true;
	}
}
