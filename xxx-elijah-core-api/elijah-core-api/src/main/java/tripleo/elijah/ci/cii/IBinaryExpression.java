package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;

public interface IBinaryExpression extends CiExpression {

	CiExpression getRight();

	void set(IBinaryExpression ex);

	void setRight(CiExpression right);
}
