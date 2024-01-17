package tripleo.elijah.lang.i;

public interface SubExpression extends IExpression {
	IExpression getExpression();

	@Override
	ExpressionKind getKind();

	@Override
	boolean is_simple();

}
