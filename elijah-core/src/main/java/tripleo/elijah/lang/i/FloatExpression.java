package tripleo.elijah.lang.i;

public interface FloatExpression extends IExpression {
	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aExpressionKind);

	@Override
	void setLeft(IExpression aLeft);

	@Override
	String toString();
}
