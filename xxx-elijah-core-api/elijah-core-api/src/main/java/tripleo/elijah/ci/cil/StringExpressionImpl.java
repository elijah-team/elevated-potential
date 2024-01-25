/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.ci.cil;

import antlr.Token;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.cii.StringExpression;
import tripleo.elijah.util.NotImplementedException;

public class StringExpressionImpl extends CiAbstractExpression implements StringExpression {
	private String repr_;

	public StringExpressionImpl(final Token g) { // TODO List<Token>
		set(g.getText());
	}

	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.STRING_LITERAL;
	}

	@Override
	public CiExpression getLeft() {
//		assert false;
//		return this;
		throw new NotImplementedException();
	}

	@Override
	public String getText() {
		return Helpers.remove_single_quotes_from_string(repr_); // TODO wont work with triple quoted string
	}

	@Override
	public String repr_() {
		return repr_;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public void setLeft(final CiExpression iexpression) {
		throw new IllegalArgumentException("Should use set()");
	}

	@Override
	public void set(final String g) {
		repr_ = g;
	}

	@Override
	public String toString() {
		return String.format("<StringExpression %s>", repr_);
	}

}

//
//
//
