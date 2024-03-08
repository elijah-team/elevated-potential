package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface MatchConditional extends OS_Element, StatementItem, FunctionItem {

	void expr(IExpression expr);

	@Override
	Context getContext();

	IExpression getExpr();

	@Override
	OS_Element getParent();

	List<MC1> getParts();

	@NotNull IMatchConditionalPart2 normal();

	void postConstruct();

	void setContext(MatchContext ctx);

	void setParent(OS_Element aParent);

	//
	//
	//
	@NotNull MatchArm_TypeMatch typeMatch();

	MatchConditionalPart3 valNormal();

	@Override
	void visitGen(ElElementVisitor visit);
}
