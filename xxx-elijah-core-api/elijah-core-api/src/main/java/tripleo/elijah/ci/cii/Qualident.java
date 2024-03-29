package tripleo.elijah.ci.cii;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

import java.util.List;

public interface Qualident extends CiExpression {
	static boolean equivalentTokens(Token token1, Token token2) {
		return token2.getText().equals(token1.getText()) && token2.getLine() == token1.getLine()
				&& token2.getColumn() == token1.getColumn() && token2.getType() == token1.getType();
	}

	void append(IdentExpression r1);

	void appendDot(Token d1);

	@NotNull
	String asSimpleString();

	@Override
	ExpressionKind getKind();

	@Override
	CiExpression getLeft();

	@Override
	boolean is_simple();

	List<IdentExpression> parts();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aIncrement);

	@Override
	void setLeft(CiExpression iexpression);
}
