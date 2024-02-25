/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on Sep 2, 2005 2:28:42 PM
 *
 * $Id$
 *
 */
package tripleo.elijah.ci_impl;

import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.CiBinaryExpression;


public class ExpressionBuilder {
	public static CiExpression build(final CiExpression left, final ExpressionKind aType) {
		return new CiAbstractExpression(left, aType) {
			@Override
			public boolean is_simple() {
				return false; // TODO whoa
			}

			@Override
			public String printableString() {
				throw new UnintendedUseException();
			}
		};
	}

	public static CiBinaryExpression build(final CiExpression left, final ExpressionKind aType,
										   final CiExpression aExpression) {
		return new BasicBinaryExpressionImpl(left, aType, aExpression);
	}

	public static CiBinaryExpression buildPartial(final CiExpression left, final ExpressionKind aType) {
		return new BasicBinaryExpressionImpl(left, aType, null);
	}

}

//
//
//
