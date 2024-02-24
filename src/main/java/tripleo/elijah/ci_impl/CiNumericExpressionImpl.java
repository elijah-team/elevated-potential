package tripleo.elijah.ci_impl;

import antlr.CommonToken;
import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.CiNumericExpression;
import tripleo.elijah.util.NotImplementedException;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.xlang.LocatableString;

import java.io.File;

public class CiNumericExpressionImpl implements CiNumericExpression {
	private int     carrier;
	private final LocatableString n;

	//public NumericExpressionImpl(final int aCarrier) {
	//	carrier = aCarrier;
	//}

	public CiNumericExpressionImpl(final @NotNull Token n) {
		this.n  = LocatableString.of(n);
		this.carrier = Integer.parseInt(n.getText());
	}

	//public @NotNull List<FormalArgListItem> getArgs() {
	//	return null;
	//}

	@Override
	public int getColumn() {
		if (token() != null)
			return token().getColumn();
		return 0;
	}

	@Override
	public int getColumnEnd() {
		if (token() != null)
			return token().getColumn();
		return 0;
	}

	// region kind

	@Override
	public File getFile() {
		if (token() != null) {
			String filename = token().getFilename();
			if (filename != null)
				return new File(filename);
		}
		return null;
	}

	@Override // CiExpression
	public CiExpressionKind getKind() {
		return CiExpressionKind.NUMERIC; // TODO
	}

	// endregion

	// region representation

	@Override
	public CiExpression getLeft() {
		return this;
	}

	@Override
	public int getLineEnd() {
		if (token() != null)
			return token().getLine();
		return 0;
	}

	// endregion

	@Override
	public int getLine() {
		if (token() != null)
			return token().getLine();
		return 0;
	}

	// region type


	@Override
	public int getValue() {
		return carrier;
	}

	@Override
	public boolean is_simple() {
		return true;
	}

	@Override
	public String printableString() {
		throw new UnintendedUseException();
	}

	// endregion

	@Override
	public String repr_() {
		return toString();
	}

	// region Locatable

	@Override // CiExpression
	public void setKind(final CiExpressionKind aType) {
		// log and ignore
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon
				.println_err_2("Trying to set ExpressionType of NumericExpression to " + aType.toString());
	}

	@Override
	public void setLeft(final CiExpression aLeft) {
		throw new NotImplementedException(); // TODO
	}

	@Override
	public String toString() {
		return String.format("NumericExpression (%d)", carrier);
	}

	private Token token() {
		return new CommonToken(n.asLocatableString());
	}

	public void setArgs(final CiExpressionList ael) {
		throw new UnintendedUseException();
	}

	// endregion
}
