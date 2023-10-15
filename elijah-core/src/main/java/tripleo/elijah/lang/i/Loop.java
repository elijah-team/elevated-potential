package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface Loop extends StatementItem, FunctionItem, OS_Element {
	void expr(IExpression aExpr);

	void frompart(IExpression aExpr);

	@Override
	Context getContext();

	@NotNull
	IExpression getFromPart();

	List<StatementItem> getItems();

	String getIterName();

	IdentExpression getIterNameToken();

	@Override
	OS_Element getParent();

	@NotNull
	IExpression getToPart();

	LoopTypes getType();

	void iterName(IdentExpression s);

	void scope(Scope3 aSco);

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setContext(LoopContext ctx);

	void topart(IExpression aExpr);

	void type(LoopTypes aType);

	@Override
	// OS_Element
	void visitGen(ElElementVisitor visit);
}
