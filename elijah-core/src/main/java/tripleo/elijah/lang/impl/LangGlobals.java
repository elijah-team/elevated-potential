package tripleo.elijah.lang.impl;

import tripleo.elijah.lang.i.*;

import org.jetbrains.annotations.NotNull;

public enum LangGlobals {
	;
	public static final IExpression UNASSIGNED = new BasicBinaryExpressionImpl() {
		@Override
		public @NotNull String toString() {
			return "<UNASSIGNED expression>";
		}
	};

	public static boolean isConstant(final IExpression expression) {
		return expression instanceof StringExpression || expression instanceof CharLitExpression
				|| expression instanceof FloatExpression || expression instanceof NumericExpression;
	}
}
