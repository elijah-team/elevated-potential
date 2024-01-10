package tripleo.elijah.ci_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.lang.i.*;

public class CiProcedureCallExpressionImpl implements CiProcedureCallExpression {
	private IExpression      _left;
	private CiExpressionList expressionList = new CiExpressionListImpl();

	/**
	 * Get the argument list
	 *
	 * @return the argument list
	 */
	@Override
	public CiExpressionList exprList() {
		return expressionList;
	}

	@Override
	public CiExpressionList getExpressionList() {
		return expressionList;
	}

	/**
	 * change then argument list all at once
	 *
	 * @param ael the new value
	 */
	@Override
	public void setExpressionList(final CiExpressionList ael) {
		expressionList = ael;
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.PROCEDURE_CALL;
	}

	@Override
	public void setKind(final ExpressionKind aExpressionKind) {
		throw new UnintendedUseException();
	}

	@Override
	public IExpression getLeft() {
		return _left;
	}

	/**
	 * @see #identifier()
	 */
	@Override
	public void setLeft(final IExpression iexpression) {
		_left = iexpression;
	}

	@Override
	public boolean is_simple() {
		throw new UnintendedUseException();
	}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name might come as DotExpression or IdentExpression
	 */
	@Override
	public void identifier(final IExpression xyz) {
		setLeft(xyz);
	}

	@Override
	public String printableString() {
		return String.format("%s%s", getLeft(), expressionListPrintableString());
	}

	private String expressionListPrintableString() {
		return expressionList != null ? expressionList.toString() : "()";
	}

	@Override
	public String repr_() {
		return "ProcedureCallExpression{%s %s}".formatted(getLeft(), expressionListPrintableString());
	}

	@Override
	public String toString() {
		return repr_();
	}

	@Override
	public void setArgs(CiExpressionList aEl) {
		throw new UnintendedUseException();
	}
}
