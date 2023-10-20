package tripleo.elijah.lang.i;

import antlr.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;

import java.util.*;

public interface IMatchConditionalPart2 extends MC1 {
	void add(FunctionItem aItem);

	void addDocString(Token text);

	void expr(IExpression expr);

	@Override
	@NotNull Context getContext();

	@Override
	@NotNull List<FunctionItem> getItems();

	IExpression getMatchingExpression();

	@NotNull OS_Element getParent();

	void scope(Scope3 sco);

	@Override
	void serializeTo(SmallWriter sw);

	@Override
	default void visitGen(@NotNull ElElementVisitor visit) {
		MC1.super.visitGen(visit);
	}
}
