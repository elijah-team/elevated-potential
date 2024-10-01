/**
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import tripleo.elijah.lang.i.*;

/**
 * @author Tripleo(envy)
 * <p>
 * Created Mar 27, 2020 at 12:59:41 AM
 */
public class DotExpressionImpl extends BasicBinaryExpressionImpl implements DotExpression {
	public DotExpressionImpl(final IExpression aDotExpressionLeft, final IdentExpression aDotExpressionRight) {
		left  = aDotExpressionLeft;
		right = aDotExpressionRight;
		_kind = ExpressionKind.DOT_EXP;
	}

	public DotExpressionImpl(final IExpression ee, final IExpression aExpression) {
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
		return asString();
	}

	@Override
	public String asString() {
		final String rightSide;
		if (right == null) {
			rightSide = "";
		} else {
			rightSide = "." + right.asString();
		}
		final String leftSide  = left.asString();
		return String.format("%s%s", leftSide, rightSide);
	}
}

//
//
//
