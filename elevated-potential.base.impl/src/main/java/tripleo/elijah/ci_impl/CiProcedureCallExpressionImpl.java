package tripleo.elijah.ci_impl;

import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.*;

// TODO is ExpressionList an CiExpression?
public class CiProcedureCallExpressionImpl implements CiProcedureCallExpression {
	private CiExpression _left;

	private CiExpressionList args = new CiExpressionListImpl();

	/**
	 * Make sure you call {@link #identifier} or {@link #setLeft(CiExpression)}
	 * and {@link #setArgs(CiExpressionList)}
	 */
	public CiProcedureCallExpressionImpl() {
	}

	/**
	 * Get the argument list
	 *
	 * @return the argument list
	 */
	@Override
	public CiExpressionList exprList() {
		return args;
	}

	@Override
	public CiExpressionList getArgs() {
		return args;
	}

	@Override
	public CiExpressionKind getKind() {
		return CiExpressionKind.PROCEDURE_CALL;
	}

	@Override
	public CiExpression getLeft() {
		return _left;
	}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name might come as DotExpression or IdentExpression
	 */
	@Override
	public void identifier(final CiExpression xyz) {
		setLeft(xyz);
	}

	@Override
	public void setExpressionList(final CiExpressionList ael) {
		throw new UnintendedUseException("begng lazy");
	}

	@Override
	public CiExpressionList getExpressionList() {
		throw new UnintendedUseException("begng lazy");
	}

	@Override
	public String printableString() {
		return String.format("%s%s", getLeft(), args != null ? args.toString() : "()");
	}

	@Override
	public boolean is_simple() {
		return false; // TODO is this correct?
	}

	/**
	 * change then argument list all at once
	 *
	 * @param ael the new value
	 */
	//@Override
	public void setArgs(final CiExpressionList ael) {
		args = ael;
	}

	public String getReturnTypeString() {
		return "int"; // TODO hardcoded
	}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name in Qualident form (might come as DotExpression in
	 *            future)
	 */
	public void identifier(final CiQualident xyz) {
		setLeft(xyz);
	}

	@Override
	public String repr_() {
		return toString();
	}

	@Override
	public void setKind(final CiExpressionKind aIncrement) {
		throw new IllegalArgumentException();
	}

	/**
	 * @see #identifier(CiQualident)
	 */
	@Override
	public void setLeft(final CiExpression iexpression) {
		_left = iexpression;
	}

	@Override
	public String toString() {
		return String.format("ProcedureCallExpression{%s %s}", getLeft(), args != null ? args.toString() : "()");
	}
}
