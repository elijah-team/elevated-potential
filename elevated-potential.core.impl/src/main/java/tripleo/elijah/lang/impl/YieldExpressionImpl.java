package tripleo.elijah.lang.impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.util.*;
import tripleo.elijah_elevated.anno.MarkerInterface;

public class YieldExpressionImpl
		extends BasicBinaryExpressionImpl
		implements OS_Element, StatementItem, YieldExpression {

	private final IExpression expr;

	public YieldExpressionImpl(final IExpression aExpr) {
		this.expr = aExpr;
	}

	@Override
	public Context getContext() {
		throw new UnintendedUseException("niy");
	}

	@Override
	public OS_Element getParent() {
		throw new UnintendedUseException("niy");
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnintendedUseException("niy");
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitYield(this);
	}

	@Override
	public String asString() {
		final String s = "<YieldExpression "+expr.asString()+">";
		return s;
	}
}
