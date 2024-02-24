package tripleo.elijah.ci_impl;

import antlr.Token;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiGetItemExpression;
import tripleo.elijah.ci.*;

/**
 * @author Tripleo
 * <p>
 * Created Apr 16, 2020 at 7:58:36 AM
 */
public class CiGetItemExpressionImpl extends CiAbstractExpression implements CiGetItemExpression {
	public CiExpression index; // TODO what about multidimensional arrays?

	public CiGetItemExpressionImpl(final CiExpression ee, final CiExpression expr) {
		this.left  = ee;
		this.index = expr;
		this._kind = CiExpressionKind.GET_ITEM;
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.GET_ITEM;
	}

	@Override
	public boolean is_simple() {
		return false; // TODO is this correct? Let's err on the side of caution
	}

	@Override
	public void parens(final Token lb, final Token rb) {
		// TODO implement me later
		throw new UnintendedUseException();
	}

	@Override
	public CiExpression index() {
		return index;
	}
}
