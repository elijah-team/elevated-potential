/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.IdentExpression;

/**
 * Created 12/22/20 11:44 PM
 */
public class PreconditionImpl implements tripleo.elijah.lang.i.Precondition {
	private IExpression expr;
	private IdentExpression id;

	@Override
	public void expr(IExpression expr) {
		this.expr = expr;
	}

	@Override
	public void id(IdentExpression id) {
		this.id = id;
	}
}

//
//
//
