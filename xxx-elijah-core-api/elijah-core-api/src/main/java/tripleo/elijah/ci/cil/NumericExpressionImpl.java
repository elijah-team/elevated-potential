package tripleo.elijah.ci.cil;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.cii.NumericExpression;
import tripleo.elijah.util.NotImplementedException;


import java.io.File;

public class NumericExpressionImpl implements NumericExpression {

	int     carrier;
	private final Token n;

	//public NumericExpressionImpl(final int aCarrier) {
	//	carrier = aCarrier;
	//}

	public NumericExpressionImpl(final @NotNull Token n) {
		this.n  = n;
		carrier = Integer.parseInt(n.getText());
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
	public ExpressionKind getKind() {
		return ExpressionKind.NUMERIC; // TODO
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
	public void setKind(final ExpressionKind aType) {
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
		return n;
	}

	public void setArgs(final CiExpressionList ael) {
		throw new UnintendedUseException();
	}

	// endregion
}
