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
 *
 */
package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;

public class ExpressionBuilder {

	public static @NotNull IExpression build(final IExpression left, final ExpressionKind aType) {
		return new AbstractExpression(left, aType) {
			@Override
			public boolean is_simple() {
				return false; // TODO whoa
			}
		};
	}

	public static @NotNull IBinaryExpression build(final IExpression left, final ExpressionKind aType,
			final IExpression aExpression) {
		return new BasicBinaryExpressionImpl(left, aType, aExpression);
	}

	public static @NotNull IBinaryExpression buildPartial(final IExpression left, final ExpressionKind aType) {
		return new BasicBinaryExpressionImpl(left, aType, null);
	}

	public static IExpression build(final IExpression aEe, final ExpressionKind aE2, final IExpression aE3, final OS_Type aT) {
		// TODO 10/15 look at me
		return build(aEe, aE2, aE3);
	}
}

//
//
//
