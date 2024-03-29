package tripleo.elijah.lang.i;

import tripleo.elijah.lang2.*;

public interface PropertyStatement extends OS_Element, OS_NamedElement, ClassItem {
	void addGet();

	void addSet();

	FunctionDef get_fn();

	void get_scope(Scope3 aSco);

	@Override
	Context getContext(); // OS_Element

	@Override
	OS_Element getParent();

	TypeName getTypeName();

	@Override
	String name();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	FunctionDef set_fn();

	void set_scope(Scope3 aSco);

	void setName(IdentExpression prop_name);

	void setTypeName(TypeName typeName);

	// OS_Element
	@Override
	void visitGen(ElElementVisitor visit); // OS_Element
}
