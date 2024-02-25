package tripleo.elijah.ci;

public interface CiBasicBinaryExpression extends CiBinaryExpression {
	@Override
	void setKind(ExpressionKind aKind);

	@Override
	void setLeft(CiExpression aLeft);

	void shift(ExpressionKind aType);

	@Override
	String toString();
}
