package tripleo.elijah.ci_impl;

import antlr.Token;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.*;
import tripleo.elijah.util.NotImplementedException;

public class CiStringExpressionImpl extends CiAbstractExpression implements CiStringExpression {
	private String repr_;

	public CiStringExpressionImpl(final Token g) { // TODO List<Token>
		set(g.getText());
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.STRING_LITERAL;
	}

	@Override
	public CiExpression getLeft() {
//		assert false;
//		return this;
		throw new NotImplementedException();
	}

	@Override
	public String getText() {
		return __Ci__Helpers.remove_single_quotes_from_string(repr_); // TODO wont work with triple quoted string
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
