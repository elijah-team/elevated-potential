package tripleo.elijah.lang.i;

import java.util.*;

import org.jetbrains.annotations.*;

import antlr.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

public interface CaseScope extends OS_Container, OS_Element, CaseConditional {
	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CaseConditional#add(tripleo.elijah.lang.i.
	 * OS_Element)
	 */
	@Override
	void add(OS_Element anElement);

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CaseConditional#addDocString(antlr.Token)
	 */
	@Override
	void addDocString(Token s1);

	@Override
	void addScopeFor(IExpression expression, CaseScope caseScope);

	@Override
	void expr(IExpression expr);

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CaseConditional#getContext()
	 */
	@Override
	Context getContext();

	@Override
	@Nullable IExpression getExpr();

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CaseConditional#getItems()
	 */
	List<OS_Element> getItems();

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CaseConditional#getParent()
	 */
	@Override
	@NotNull
	OS_Element getParent();

	@Override
	@Nullable HashMap<IExpression, CaseScope> getScopes();

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CaseConditional#items()
	 */
	@Override
	List<OS_NamedElement> items();

	@Override
	void postConstruct();

	@Override
	void scope(Scope3 aSco, IExpression aExpr1);

	@Override
	void serializeTo(SmallWriter sw);

	void setContext(ICaseContext ctx);

	/*
	 * (non-Javadoc)
	 *
	 * @see tripleo.elijah.lang.impl.CaseConditional#setDefault()
	 */
	@Override
	void setDefault();

	@Override
	void visitGen(@NotNull ElElementVisitor visit);
}
