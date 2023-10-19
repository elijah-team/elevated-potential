package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface MatchConditional extends OS_Element, StatementItem, FunctionItem {
	public interface MC1 extends OS_Element, Documentable {
		void add(FunctionItem aItem);

		@Override
		Context getContext();

		Iterable<? extends FunctionItem> getItems();

		@Override
		default void serializeTo(SmallWriter sw) {

		}

		@Override
		default void visitGen(@NotNull ElElementVisitor visit) {
			visit.visitMC1(this);
		}
	}

	void expr(IExpression expr);

	@Override
	Context getContext();

	IExpression getExpr();

	@Override
	OS_Element getParent();

	List<MC1> getParts();

	MatchConditionalImpl.MatchConditionalPart2 normal();

	void postConstruct();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(MatchContext ctx);

	void setParent(OS_Element aParent);

	//
	//
	//
	MatchConditionalImpl.MatchArm_TypeMatch typeMatch();

	MatchConditionalImpl.MatchConditionalPart3 valNormal();

	@Override
	void visitGen(ElElementVisitor visit);
}
