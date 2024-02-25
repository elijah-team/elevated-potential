package tripleo.elijah.ci_impl;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.CiGetItemExpression;
import tripleo.elijah.ci.CiSetItemExpression;

/**
 * Created 8/6/20 1:15 PM
 */
public class SetItemExpressionImpl extends BasicBinaryExpressionImpl
		implements CiSetItemExpression {
	public SetItemExpressionImpl(final CiGetItemExpression left_, final CiExpression right_) {
		this.setLeft(left_);
		this.setRight(right_);
		this.setKind(ExpressionKind.SET_ITEM);
	}
}
