package tripleo.elijah.ci_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.CiCharLitExpression;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;

/**
 * @author Tripleo(sb)
 */
public class CiCharLitExpressionImpl implements CiCharLitExpression {
	private final Token            char_lit_raw;
	private       CiExpressionList args;

	public CiCharLitExpressionImpl(final Token c) {
		char_lit_raw = c;
	}

	public @NotNull CiExpressionList getArgs() {
		return args;
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.CHAR_LITERAL;
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
	public void setKind(final CiExpressionKind aIncrement) {
		throw new UnintendedUseException();
	}

	@Override
	public void setLeft(final CiExpression iexpression) {
throw new UnintendedUseException();
	}

	@Override
	public String toString() {
		return __Ci__Helpers.remove_single_quotes_from_string(char_lit_raw.getText());
	}
}
