package tripleo.elijah_elevated_durable.lang_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;

public class StatementWrapperImpl implements StatementItem, FunctionItem, OS_Element, StatementWrapper {

	private final Context _ctx;
	private final OS_Element _parent;
	private final IExpression expr;

	public StatementWrapperImpl(final IExpression aExpression, final Context aContext, final OS_Element aParent) {
		expr = aExpression;
		_ctx = aContext;
		_parent = aParent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.StatementWrapper#getContext()
	 */
	@Override
	public Context getContext() {
		return _ctx;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.StatementWrapper#getExpr()
	 */
	@Override
	public IExpression getExpr() {
		return expr;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.StatementWrapper#getParent()
	 */
	@Override
	public OS_Element getParent() {
		return _parent;
	}

	@Override
	public void serializeTo(final @NotNull SmallWriter sw) {
		sw.fieldExpression("expr", expr);
		sw.fieldElement("parent", _parent);
		sw.fieldString("context", _ctx.toString());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.StatementWrapper#toString()
	 */
	@Override
	public String toString() {
		return expr.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.StatementWrapper#visitGen(tripleo.elijah.lang2.
	 * ElElementVisitor)
	 */
	@Override
	public void visitGen(final @NotNull ElElementVisitor visit) {
		visit.visitStatementWrapper(this);
	}

}

//
//
//
