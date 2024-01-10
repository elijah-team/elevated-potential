package tripleo.elijah.lang.i;

public interface StringExpression extends IExpression {
	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	String getText();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	void set(String g);

	@Override
	void setLeft(IExpression iexpression);

	@Override
	String toString();
}
