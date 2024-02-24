package tripleo.elijah.ci;

public interface CiTypeCastExpression extends CiExpression {
	CiTypeName getTypeName();

	@Override
	boolean is_simple();

	void setTypeName(CiTypeName typeName);

	CiTypeName typeName();
}
