/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.lang.impl;

import tripleo.elijah.lang.i.*;

import org.jetbrains.annotations.NotNull;

// TODO is ExpressionList an IExpression?
public class ProcedureCallExpressionImpl implements ProcedureCallExpression {
	private OS_Type        _type;
	private IExpression    _left;
	private ExpressionList args = new ExpressionListImpl();

	///**
	// * Make sure you call {@link #identifier} or {@link #setLeft(IExpression)} and
	// * {@link #setArgs(ExpressionList)}
	// */
	//public ProcedureCallExpressionImpl() {
	//}

	//	public ProcedureCallExpression(final Token aToken, final ExpressionList aExpressionList, final Token aToken1) {
	//		throw new NotImplementedException();
	//	}

	/**
	 * Get the argument list
	 *
	 * @return the argument list
	 */
	@Override
	public ExpressionList getArgs() {
		return args;
	}

	public OS_Type getType() {
		return _type;
	}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name might come as DotExpression or IdentExpression
	 */
	@Override
	public void identifier(final IExpression xyz) {
		//Preconditions.checkArgument(xyz instanceof IdentExpression);
		setLeft(xyz);
	}

	@Override
	public String printableString() {
		return String.format("%s%s", getLeft(), args != null ? args.toString() : "()");
	}

	/**
	 * change the argument list all at once
	 *
	 * @param ael the new value
	 */
	@Override
	public void setArgs(final ExpressionList ael) {
		args = ael;
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.PROCEDURE_CALL;
	}

	@Override
	public IExpression getLeft() {
		return _left;
	}

	@Override
	public boolean is_simple() {
		return false; // TODO is this correct?
	}

	@Override
	public String repr_() {
		return String.format("ProcedureCallExpression{%s %s}", getLeft(), args != null ? args.toString() : "()");
	}

	@Override
	public String toString() {
		return repr_();
	}

	/**
	 * @see #identifier(Qualident)
	 */
	@Override
	public void setLeft(final IExpression iexpression) {
		_left = iexpression;
	}

	@Override
	public void setKind(final ExpressionKind aExpressionKind) {
		throw new IllegalArgumentException();
	}

	//public @NotNull String getReturnTypeString() {
	//	return "int"; // TODO hardcoded
	//}

	/**
	 * Set the left hand side of the procedure call expression, ie the method name
	 *
	 * @param xyz a method name in Qualident form (might come as DotExpression in
	 *            future)
	 */
	public void identifier(final Qualident xyz) {
		setLeft(xyz);
	}
}

//
//
//
