package tripleo.elijah.ci;

public interface CiBinaryExpression extends CiExpression {
	void set(CiBinaryExpression ex);

	void setRight(CiExpression right);

	CiExpression getRight();
}
