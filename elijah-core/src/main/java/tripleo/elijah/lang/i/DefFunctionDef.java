package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;

import java.util.*;

public interface DefFunctionDef extends FunctionDef {
	@Override
	List<FunctionItem> getItems();

	@Override
	OS_Element getParent();

	@Override
	void postConstruct();

	@Override
	@Nullable TypeName returnType();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	@Override
	void setAnnotations(List<AnnotationClause> aAs);

	void setBody(IExpression aExpression);

	// wont use parent scope.items.add so this is ok
	void setExpr(IExpression aExpr);

	@Override
	void setFal(FormalArgList aOp);

	@Override
	void setHeader(FunctionHeader aFunctionHeader);

	@Override
	void setReturnType(TypeName tn);

	//@Override
	//void visitGen(ElElementVisitor visit);

	//@Override
	//Context getContext();
}
