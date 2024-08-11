/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.ci.cil;

import tripleo.elijah.ci.*;
import tripleo.elijah.ci.cii.*;

// TODO is ExpressionList an CiExpression?
public class ProcedureCallExpressionImpl implements ProcedureCallExpression {
	private CiExpression _left;

	// region right-side
	private CiExpressionList args = new ExpressionListImpl();

	/**
	 * Make sure you call {@link #identifier} or {@link #setLeft(CiExpression)}
	 * and {@link #setArgs(CiExpressionList)}
	 */
	public ProcedureCallExpressionImpl() {
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

	// endregion

//	@Override
//	public void visitGen(ICodeGen visit) {
//		// TODO Auto-generated method stub
//		NotImplementedException.raise();
//	}

	// region kind

//	@Override
// public @NotNull List<FormalArgListItem> getArgs() {
//		return args;
//	}

	@Override
	public CiExpressionList getArgs() {
		return args;
	}

	// endregion

	// region left-side

	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.PROCEDURE_CALL;
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
	public String printableString() {
		return String.format("%s%s", getLeft(), args != null ? args.toString() : "()");
	}

	// endregion

	@Override
	public boolean is_simple() {
		return false; // TODO is this correct?
	}

	/*
	 * public OS_Element getParent() { return null; }
	 */

	// region representation

	/**
	 * change then argument list all at once
	 *
	 * @param ael the new value
	 */
	@Override
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
	public void identifier(final Qualident xyz) {
		setLeft(xyz);
	}

	// endregion

	// region type (to remove)

	@Override
	public String repr_() {
		return toString();
	}

	@Override
	public void setKind(final ExpressionKind aIncrement) {
		throw new IllegalArgumentException();
	}

	/**
	 * @see #identifier(Qualident)
	 */
	@Override
	public void setLeft(final CiExpression iexpression) {
		_left = iexpression;
	}

	@Override
	public String toString() {
		return String.format("ProcedureCallExpression{%s %s}", getLeft(), args != null ? args.toString() : "()");
	}

	// endregion
}
