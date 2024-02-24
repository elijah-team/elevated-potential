package tripleo.elijah.ci_impl;

import tripleo.elijah.ci.*;

/**
 * @author Tripleo(envy)
 * <p>
 * Created Mar 27, 2020 at 12:59:41 AM
 */
public class CiDotExpressionImpl extends CiBasicBinaryExpressionImpl implements CiDotExpression {
	public CiDotExpressionImpl(final CiExpression ee, final CiIdentExpression identExpression) {
		left  = ee;
		right = identExpression;
		_kind = CiExpressionKind.DOT_EXP;
	}

	public CiDotExpressionImpl(final CiExpression ee, final CiExpression aExpression) {
		left  = ee;
		right = aExpression;
		_kind = CiExpressionKind.DOT_EXP;
	}

	@Override
	public boolean is_simple() {
		return false; // TODO when is this true or not? see {@link Qualident}
	}

	@Override
	public String toString() {
		return String.format("%s.%s", left, right);
	}

}
