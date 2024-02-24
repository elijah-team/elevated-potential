package tripleo.elijah.ci_impl;

import tripleo.elijah.ci.*;

/**
 * Created 8/6/20 1:15 PM
 */
public class CiSetItemExpressionImpl extends CiBasicBinaryExpressionImpl
		implements CiSetItemExpression {
	public CiSetItemExpressionImpl(final CiGetItemExpression left_, final CiExpression right_) {
		this.setLeft(left_);
		this.setRight(right_);
		this.setKind(CiExpressionKind.SET_ITEM);
	}
}
