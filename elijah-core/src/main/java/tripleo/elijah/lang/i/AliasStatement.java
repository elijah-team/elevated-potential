package tripleo.elijah.lang.i;

public interface AliasStatement extends OS_Element, ModuleItem, ClassItem, FunctionItem, OS_Element2 {
	Qualident getExpression();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void setExpression(Qualident aXy);

	void setName(IdentExpression aI1);
}
