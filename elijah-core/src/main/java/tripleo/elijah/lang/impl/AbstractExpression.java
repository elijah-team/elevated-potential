/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

public abstract class AbstractExpression implements IExpression {

	public @Nullable ExpressionKind _kind;

	private ExpressionList args;

	public @Nullable IExpression left;

	public AbstractExpression() {
		left = null;
		_kind = null;
	}

	public AbstractExpression(final IExpression aLeft, final ExpressionKind aType) {
		left = aLeft;
		_kind = aType;
	}

	public @NotNull ExpressionList getArgs() {
		return args;
	}

	@Override
	public ExpressionKind getKind() {
		return _kind;
	}

	@Override
	public IExpression getLeft() {
		return left;
	}

	@Override
	public String repr_() {
		return String.format("<Expression %s %s>", left, _kind);
	}

	public void setArgs(ExpressionList ael) {
		args = ael;
	}

	@Override
	public void setKind(final ExpressionKind type1) {
		_kind = type1;
	}

	@Override
	public void setLeft(final IExpression aLeft) {
		left = aLeft;
	}
}

//
//
//
