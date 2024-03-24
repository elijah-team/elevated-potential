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
package tripleo.elijah_durable_elevated.elijah.lang.impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.Helpers;

/**
 * @author Tripleo(sb)
 */
public class CharLitExpressionImpl implements CharLitExpression {
	
	private final Token char_lit_raw;
	private ExpressionList args;

	public CharLitExpressionImpl(final Token c) {
		char_lit_raw = c;
	}

	public @NotNull ExpressionList getArgs() {
		return args;
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.CHAR_LITERAL;
	}

	@Override
	public @Nullable IExpression getLeft() {
		return this; // TODO 10/15 find SPEC SingleExpressionGetLeftContract
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String repr_() {
		return String.format("<CharLitExpression %s>", char_lit_raw);
	}

	public void setArgs(ExpressionList ael) {
		args = ael;
	}

	@Override
	public void setKind(final ExpressionKind aExpressionKind) {
		throw new UnintendedUseException();
	}

	@Override
	public void setLeft(final IExpression iexpression) {
		throw new UnintendedUseException();
	}

	@Override
	public @NotNull String toString() {
		return Helpers.remove_single_quotes_from_string(char_lit_raw.getText());
	}
}

//
//
//
