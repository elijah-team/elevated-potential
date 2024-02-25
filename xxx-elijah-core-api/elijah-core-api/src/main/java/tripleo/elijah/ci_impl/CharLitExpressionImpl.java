package tripleo.elijah.ci_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci.CiCharLitExpression;
import tripleo.elijah.util.UnintendedUseException;


/**
 * @author Tripleo(sb)
 */
public class CharLitExpressionImpl implements CiCharLitExpression {
	private final Token            char_lit_raw;
	private       CiExpressionList args;

	public CharLitExpressionImpl(final Token c) {
		char_lit_raw = c;
	}

	public @NotNull CiExpressionList getArgs() {
		return args;
	}

	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.CHAR_LITERAL;
	}

	@Override
	public CiExpression getLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setArgs(CiExpressionList ael) {
		args = ael;
	}

	@Override
	public String repr_() {
		return String.format("<CharLitExpression %s>", char_lit_raw);
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public void setKind(final ExpressionKind aIncrement) {
		throw new UnintendedUseException();
	}

	@Override
	public void setLeft(final CiExpression iexpression) {
throw new UnintendedUseException();
	}

	@Override
	public String toString() {
		return Helpers.remove_single_quotes_from_string(char_lit_raw.getText());
	}
}
