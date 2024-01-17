package tripleo.elijah.lang.i;

import antlr.Token;

public interface GetItemExpression extends IExpression {
	@Override
	ExpressionKind getKind();

	@Override
	boolean is_simple();

	IExpression index();

	void parens(Token lb, Token rb);
}
