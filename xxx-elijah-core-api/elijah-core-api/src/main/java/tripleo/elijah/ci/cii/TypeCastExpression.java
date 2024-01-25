package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;

public interface TypeCastExpression extends CiExpression {
	TypeName getTypeName();

	@Override
	boolean is_simple();

	void setTypeName(TypeName typeName);

	TypeName typeName();
}
