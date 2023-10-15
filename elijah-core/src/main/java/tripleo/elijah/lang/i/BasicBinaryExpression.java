package tripleo.elijah.lang.i;

public interface BasicBinaryExpression extends IBinaryExpression {
	@Override
	void setKind(ExpressionKind aExpressionKind);

	@Override
	void setLeft(IExpression aLeft);

	//void shift(ExpressionKind aType);

	@Override
	String toString();
}
