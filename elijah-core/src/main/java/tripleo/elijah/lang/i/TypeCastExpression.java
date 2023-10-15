package tripleo.elijah.lang.i;

public interface TypeCastExpression extends IExpression {

	TypeName getTypeName();

	@Override
	boolean is_simple();

	void setTypeName(TypeName typeName);

	TypeName typeName();
}
