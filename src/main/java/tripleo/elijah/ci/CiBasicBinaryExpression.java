package tripleo.elijah.ci;

public interface CiBasicBinaryExpression extends CiBinaryExpression {
	@Override void setKind(CiExpressionKind aKind);

	@Override void setLeft(CiExpression aLeft);

	void shift(CiExpressionKind aType);

	@Override String toString();
}
