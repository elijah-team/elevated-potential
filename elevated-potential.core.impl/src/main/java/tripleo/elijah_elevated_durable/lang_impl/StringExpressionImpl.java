/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.lang_impl;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.Helpers;
import tripleo.elijah_fluffy.util.NotImplementedException;

public class StringExpressionImpl extends AbstractExpression implements StringExpression {

	private String repr_;

	public StringExpressionImpl(final @NotNull Token g) { // TODO List<Token>
		set(g.getText());
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.STRING_LITERAL;
	}

	@Override
	public IExpression getLeft() {
//		assert false;
//		return this;
		throw new NotImplementedException();
	}

	@Override
	public @NotNull String getText() {
		return Helpers.remove_single_quotes_from_string(repr_); // TODO wont work with triple quoted string
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String repr_() {
		return repr_;
	}

	@Override
	public void set(final String g) {
		repr_ = g;
	}

	@Override
	public void setLeft(final IExpression iexpression) {
		throw new IllegalArgumentException("Should use set()");
	}

	@Override
	public String toString() {
		return String.format("<StringExpression %s>", repr_);
	}

}

//
//
//
