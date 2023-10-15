/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 * Created Mar 27, 2019 at 2:20:38 PM
 */
package tripleo.elijah.lang.impl;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;

/**
 * @author Tripleo(sb)
 */
public class CharLitExpressionImpl implements tripleo.elijah.lang.i.CharLitExpression {

	OS_Type _type;
	private final Token char_lit_raw;
	private ExpressionList args;

	public CharLitExpressionImpl(final Token c) {
		char_lit_raw = c;
	}

	public @NotNull ExpressionList getArgs() {
		return args;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#getType()
	 */
	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.CHAR_LITERAL;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#getLeft()
	 */
	@Override
	public @Nullable IExpression getLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OS_Type getType() {
		return _type;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#repr_()
	 */
	@Override
	public String repr_() {
		return String.format("<CharLitExpression %s>", char_lit_raw);
	}

	public void setArgs(ExpressionList ael) {
		args = ael;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#set(tripleo.elijah.lang.impl.
	 * ExpressionType)
	 */
	@Override
	public void setKind(final ExpressionKind aIncrement) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.IExpression#setLeft(tripleo.elijah.lang.impl.
	 * IExpression)
	 */
	@Override
	public void setLeft(final IExpression iexpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setType(final OS_Type deducedExpression) {
		_type = deducedExpression;
	}

	@Override
	public @NotNull String toString() {
		return Helpers.remove_single_quotes_from_string(char_lit_raw.getText());
	}
}

//
//
//
