/**
 *
 */
package tripleo.elijah.ci_impl;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.CiDotExpression;
import tripleo.elijah.ci.CiIdentExpression;

/**
 * @author Tripleo(envy)
 * <p>
 * Created Mar 27, 2020 at 12:59:41 AM
 */
public class DotExpressionImpl extends BasicBinaryExpressionImpl implements CiDotExpression {

	public DotExpressionImpl(final CiExpression ee, final CiIdentExpression identExpression) {
		left  = ee;
		right = identExpression;
		_kind = ExpressionKind.DOT_EXP;
	}

	public DotExpressionImpl(final CiExpression ee, final CiExpression aExpression) {
		left  = ee;
		right = aExpression;
		_kind = ExpressionKind.DOT_EXP;
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

//
//
//
