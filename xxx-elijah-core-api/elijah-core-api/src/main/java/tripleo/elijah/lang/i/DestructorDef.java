package tripleo.elijah.lang.i;

public interface DestructorDef extends OS_Element {
	void postConstruct();

	void scope(Scope3 aSco);

	//@Override
	//default void serializeTo(SmallWriter sw) {
	//
	//}

	void setFal(FormalArgList aFal);

	void setHeader(FunctionHeader aFunctionHeader);
}
