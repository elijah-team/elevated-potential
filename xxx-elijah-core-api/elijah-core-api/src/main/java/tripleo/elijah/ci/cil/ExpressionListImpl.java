package tripleo.elijah.ci.cil;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Curiously, not an expression
 */
public class ExpressionListImpl implements CiExpressionList {
	private final List<CiExpression> exprs = new ArrayList<CiExpression>();

	@Override
	public void add(final CiExpression aExpr) {
		exprs.add(aExpr);
	}

	@Override
	public @NotNull Collection<CiExpression> expressions() {
		return exprs;
	}

	@Override
	public Iterator<CiExpression> iterator() {
		return exprs.iterator();
	}

	@Override
	public CiExpression next(final CiExpression aExpr) {
//		assert aExpr != null;
		if (aExpr == null)
			throw new IllegalArgumentException("expression cannot be null");
		//
		/* exprs. */
		add(aExpr);
		return aExpr;
	}

	@Override
	public int size() {
		return exprs.size();
	}

	@Override
	public String toString() {
		return exprs.toString();
	}
}
