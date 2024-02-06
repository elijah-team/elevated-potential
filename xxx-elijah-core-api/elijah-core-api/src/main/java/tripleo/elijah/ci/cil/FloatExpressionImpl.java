/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/*
 * Created on May 19, 2019 23:47
 *
 * $Id$
 *
 */
package tripleo.elijah.ci.cil;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.cii.FloatExpression;
import tripleo.elijah.util.NotImplementedException;


import java.util.List;

public class FloatExpressionImpl implements FloatExpression {
	private final Token n;
	float   carrier;

	public FloatExpressionImpl(final Token n) {
		this.n  = n;
		carrier = Float.parseFloat(n.getText());
	}

	//public @NotNull List<FormalArgListItem> getArgs() {
	//	return null;
	//}

	public void setArgs(final CiExpressionList ael) {
		throw new UnintendedUseException();
	}

	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.FLOAT; // TODO
	}

	@Override
	public CiExpression getLeft() {
		return this;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String printableString() {
		throw new UnintendedUseException();
	}

	@Override
	public String repr_() {
		return toString();
	}

	@Override
	public String toString() {
		return String.format("FloatExpression (%f)", carrier);
	}

	@Override
	public void setLeft(final CiExpression aLeft) {
		throw new NotImplementedException(); // TODO
	}

	@Override
	public void setKind(final ExpressionKind aType) {
		// log and ignore
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_2("Trying to set ExpressionType of FloatExpression to " + aType.toString());
	}
}
