/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.ci_impl;

import antlr.Token;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.CiGetItemExpression;


/**
 * @author Tripleo
 * <p>
 * Created Apr 16, 2020 at 7:58:36 AM
 */
public class GetItemExpressionImpl extends CiAbstractExpression implements CiGetItemExpression {
	public CiExpression index; // TODO what about multidimensional arrays?

	public GetItemExpressionImpl(final CiExpression ee, final CiExpression expr) {
		this.left  = ee;
		this.index = expr;
		this._kind = ExpressionKind.GET_ITEM;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CiExpression#getKind()
	 */
	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.GET_ITEM;
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
