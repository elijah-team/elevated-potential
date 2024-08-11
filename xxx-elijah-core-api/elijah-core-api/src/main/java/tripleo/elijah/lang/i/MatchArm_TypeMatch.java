package tripleo.elijah.lang.i;

import java.util.*;

import org.jetbrains.annotations.*;

import antlr.*;

public interface MatchArm_TypeMatch extends MC1 {
	@Override
	void add(FunctionItem aItem);

	@Override
	void addDocString(Token text);

	@Override
	@NotNull
	Context getContext();

	IdentExpression getIdent();

	@Override
	@NotNull
	List<FunctionItem> getItems();

	@Override
	@NotNull
	OS_Element getParent();

	TypeName getTypeName();

	void ident(IdentExpression i1);

	void scope(Scope3 sco);

	@Override
	void serializeTo(SmallWriter sw);

	void setTypeName(TypeName typeName);
}
