package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;

import java.util.*;

public interface FuncExpr extends IExpression, OS_Element {
	//Collection<FormalArgListItem> getArgs();

	FormalArgList fal();

	List<FormalArgListItem> falis();

	@NotNull List<FormalArgListItem> getArgs();

	@Override
	Context getContext();

	List<FunctionItem> getItems();

	@Override
	ExpressionKind getKind();

	@Override
	OS_Element getParent();

	Scope3 getScope();

	void postConstruct();

	@Nullable TypeName returnType();

	void scope(Scope3 aSco);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setArgList(FormalArgList argList);

	void setContext(FuncExprContext ctx);

	void setHeader(FunctionHeader aFunctionHeader);

	void setReturnType(TypeName tn);

	void type(TypeModifiers modifier);
}
