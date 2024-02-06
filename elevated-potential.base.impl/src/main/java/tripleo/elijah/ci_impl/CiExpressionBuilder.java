package tripleo.elijah.ci_impl;

import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.*;

public class CiExpressionBuilder {
	public static CiExpression build(final CiExpression left, final CiExpressionKind aType) {
		return new CiAbstractExpression(left, aType) {
			@Override
			public boolean is_simple() {
				return false; // TODO whoa
			}

			@Override
			public String printableString() {
				throw new UnintendedUseException();
			}

			@Override
			public void setKind(final CiExpressionKind aCiExpressionKind) {
				throw new UnintendedUseException("just fill it out");
			}
		};
	}

	public static CiBinaryExpression build(final CiExpression left,
										   final CiExpressionKind aType,
										   final CiExpression aExpression) {
		return new CiBasicBinaryExpressionImpl(left, aType, aExpression);
	}

	public static CiBinaryExpression buildPartial(final CiExpression left, final CiExpressionKind aType) {
		return new CiBasicBinaryExpressionImpl(left, aType, null);
	}
}
