package tripleo.elijah.lang.i;

import tripleo.elijah.lang2.*;

public interface ConstructorDef extends FunctionDef {
	@Override
	OS_Element getParent();

	@Override
	String name();

	@Override
	void postConstruct();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	@Override
	void setFal(FormalArgList aFal);

	@Override
	void setHeader(FunctionHeader aFunctionHeader);

	@Override
	String toString();

	@Override
	void visitGen(ElElementVisitor visit); // OS_Element
}
