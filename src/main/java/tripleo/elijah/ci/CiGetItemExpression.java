package tripleo.elijah.ci;

import antlr.Token;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;

public interface CiGetItemExpression extends CiExpression {
	CiExpression index();

	void parens(Token lb, Token rb);

	@Override CiExpressionKind getKind();

	@Override boolean is_simple();
}
