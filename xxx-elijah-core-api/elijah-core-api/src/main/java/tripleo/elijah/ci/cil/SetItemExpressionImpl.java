package tripleo.elijah.ci.cil;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.cii.GetItemExpression;
import tripleo.elijah.ci.cii.SetItemExpression;

/**
 * Created 8/6/20 1:15 PM
 */
public class SetItemExpressionImpl extends BasicBinaryExpressionImpl
		implements SetItemExpression {
	public SetItemExpressionImpl(final GetItemExpression left_, final CiExpression right_) {
		this.setLeft(left_);
		this.setRight(right_);
		this.setKind(ExpressionKind.SET_ITEM);
	}
}
