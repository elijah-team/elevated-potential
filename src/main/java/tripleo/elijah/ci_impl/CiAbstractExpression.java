package tripleo.elijah.ci_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;

public abstract class CiAbstractExpression implements CiExpression {
	public CiExpressionKind _kind;

	private CiExpressionList args;

	public CiExpression left;

	public CiAbstractExpression() {
		left  = null;
		_kind = null;
	}

	public CiAbstractExpression(final CiExpression aLeft, final CiExpressionKind aType) {
		left  = aLeft;
		_kind = aType;
	}

	public @NotNull CiExpressionList getArgs() {
		return args;
	}

	@Override
	public CiExpressionKind getKind() {
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
	public void setKind(final CiExpressionKind type1) {
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
