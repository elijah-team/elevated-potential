package tripleo.elijah.ci.cil;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.util2.UnintendedUseException;

public abstract class CiAbstractExpression implements CiExpression {
	public ExpressionKind _kind;

	private CiExpressionList args;

	public CiExpression left;

	public CiAbstractExpression() {
		left  = null;
		_kind = null;
	}

	public CiAbstractExpression(final CiExpression aLeft, final ExpressionKind aType) {
		left  = aLeft;
		_kind = aType;
	}

	public @NotNull CiExpressionList getArgs() {
		return args;
	}

	@Override
	public ExpressionKind getKind() {
		return _kind;
	}

	@Override
	public CiExpression getLeft() {
		return left;
	}

	public void setArgs(CiExpressionList ael) {
		args = ael;
	}

	@Override
	public String repr_() {
		return String.format("<Expression %s %s>", left, _kind);
	}

	@Override
	public void setKind(final ExpressionKind type1) {
		_kind = type1;
	}

	@Override
	public void setLeft(final CiExpression aLeft) {
		left = aLeft;
	}

	@Override
	public String printableString() {
		throw new UnintendedUseException();
	}
}
