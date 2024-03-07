package tripleo.elijah.lang.i;

import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface IfConditional extends StatementItem, FunctionItem, OS_Element {
	IfConditional else_();

	IfConditional elseif();

	void expr(IExpression expr);

	@Override
	Context getContext();

	Context getCtx();

	IExpression getExpr();

	List<OS_Element> getItems();

	@Override
	OS_Element getParent();

	List<IfConditional> getParts();

	void scope(Scope3 aSco);

	//@Override
	//default void serializeTo(SmallWriter sw) {
	//
	//}

	void setContext(IfConditionalContext ifConditionalContext);

	@Override
	void visitGen(ElElementVisitor visit);

}
