/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.OS_Type;

/**
 * @author Tripleo
 *         <p>
 *         Created Apr 16, 2020 at 7:58:36 AM
 */
public class GetItemExpressionImpl extends AbstractExpression implements tripleo.elijah.lang.i.GetItemExpression { // TODO
	// binary?

	public IExpression index; // TODO what about multidimensional arrays?

	public GetItemExpressionImpl(final IExpression ee, final IExpression expr) {
		this.left = ee;
		this.index = expr;
		this._kind = ExpressionKind.GET_ITEM;
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.GET_ITEM;
	}

	@Override
	public IExpression index() {
		return index;
	}

	@Override
	public boolean is_simple() {
		return false; // TODO is this correct? Let's err on the side of caution
	}

	@Override
	public void parens(final Token lb, final Token rb) {
		// TODO implement me later
		// _syntax
		// https://en.wikipedia.org/w/index.php?title=Concrete_syntax_tree&redirect=no
		// https://docs.openrewrite.org/concepts-explanations/lossless-semantic-trees
	}

}

//
//
//
