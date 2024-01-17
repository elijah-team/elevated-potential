package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface CaseConditional extends OS_Element, StatementItem, FunctionItem {
	void addScopeFor(IExpression expression, CaseScope caseScope);

	void expr(IExpression expr);

	@Override
	Context getContext();

	IExpression getExpr();

	@Override
	OS_Element getParent();

	HashMap<IExpression, CaseScope> getScopes();

	void postConstruct();

	void scope(Scope3 aSco, IExpression aExpr1);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(ICaseContext ctx);

	void setDefault();

	@Override
	void visitGen(ElElementVisitor visit);

	String getIterName();

	void getIterNameToken();

	interface CaseScope extends Scope {
		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#add(tripleo.elijah.lang.i.
		 * OS_Element)
		 */
		void add(OS_Element anElement);

		void addScopeFor(IExpression expression, CaseConditional caseScope);

		void expr(IExpression expr);

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#getContext()
		 */
		Context getContext();

		@Nullable IExpression getExpr();

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#items()
		 */
		List<OS_Element2> items();

		void scope(Scope3 aSco, IExpression aExpr1);

		void serializeTo(SmallWriter sw);

		void setContext(ICaseContext ctx);

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#getItems()
		 */
		List<OS_Element> getItems();

		/*
		 * (non-Javadoc)
		 *
		 * @see tripleo.elijah.lang.impl.CaseConditional#setDefault()
		 */
		void setDefault();

		void visitGen(@NotNull ElElementVisitor visit);

		String getIterName();

		void getIterNameToken();
	}
}
