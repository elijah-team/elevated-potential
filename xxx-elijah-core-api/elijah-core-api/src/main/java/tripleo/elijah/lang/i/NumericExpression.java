package tripleo.elijah.lang.i;

import tripleo.elijah_fluffy.diagnostic.Locatable;

public interface NumericExpression extends IExpression, Locatable {
	@Override
	// IExpression
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	@Override
	int getLine();

	int getValue();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override // IExpression
	void setKind(ExpressionKind aExpressionKind);

	@Override
	void setLeft(IExpression aLeft);

	@Override
	String toString();
}
