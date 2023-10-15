/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.ExpressionKind;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.ToExpression;

/**
 * really belongs in stages.deduce
 *
 * Created on Apr 18, 2020 at 23:03
 */
public class ToExpressionImpl extends BasicBinaryExpressionImpl implements ToExpression {

	public ToExpressionImpl(final IExpression fromPart, final IExpression toPart) {
		left = fromPart;
		right = toPart;
		_kind = ExpressionKind.TO_EXPR;
	}
}

//
//
//
