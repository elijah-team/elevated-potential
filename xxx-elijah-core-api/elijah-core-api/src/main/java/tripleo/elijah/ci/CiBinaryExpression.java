package tripleo.elijah.ci;

public interface CiBinaryExpression extends CiExpression {

	CiExpression getRight();

	void set(CiBinaryExpression ex);

	void setRight(CiExpression right);
}
