package tripleo.elijah.lang.i;

import java.util.*;

import org.jetbrains.annotations.*;

import antlr.*;

public interface MatchConditionalPart3 extends MC1 {
	@Override
	void add(FunctionItem aItem);

	@Override
	void addDocString(Token text);

	void expr(IdentExpression expr);

	@Override
	@NotNull
	Context getContext();

	@Override
	@NotNull
	List<FunctionItem> getItems();

	@Override
	@NotNull
	OS_Element getParent();

	void scope(Scope3 sco);

	@Override
	void serializeTo(SmallWriter sw);
}
