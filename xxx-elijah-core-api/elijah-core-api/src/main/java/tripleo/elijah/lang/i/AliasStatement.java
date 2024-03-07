package tripleo.elijah.lang.i;

public interface AliasStatement extends OS_Element, ModuleItem, ClassItem, FunctionItem, OS_NamedElement {
	Qualident getExpression();

	@Override
	void serializeTo(SmallWriter sw);

	void setExpression(Qualident aXy);

	void setName(IdentExpression aI1);
}
