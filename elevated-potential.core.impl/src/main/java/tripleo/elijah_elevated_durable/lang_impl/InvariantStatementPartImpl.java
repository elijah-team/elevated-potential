/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import antlr.Token;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.IInvariantStatement;

/**
 * @author Tripleo
 *         <p>
 *         Created Apr 19, 2020 at 00:32:00 AM
 */
public class InvariantStatementPartImpl implements tripleo.elijah.lang.i.InvariantStatementPart {
	private       IExpression         expr;
	private final Token               name;
	private final IInvariantStatement parent;

	public InvariantStatementPartImpl(final IInvariantStatement cr, final Token token) {
		this.parent = cr;
		this.name = token;
	}

	@Override
	public void setExpr(final IExpression expr) {
		this.expr = expr;
	}
}

//
//
//
