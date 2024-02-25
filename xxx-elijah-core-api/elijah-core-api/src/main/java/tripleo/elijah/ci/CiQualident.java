package tripleo.elijah.ci;

import antlr.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CiQualident extends CiExpression {
	static boolean equivalentTokens(Token token1, Token token2) {
		return token2.getText().equals(token1.getText()) && token2.getLine() == token1.getLine()
				&& token2.getColumn() == token1.getColumn() && token2.getType() == token1.getType();
	}

	void append(CiIdentExpression r1);

	void appendDot(Token d1);

	@NotNull
	String asSimpleString();

	@Override
	ExpressionKind getKind();

	@Override
	CiExpression getLeft();

	@Override
	boolean is_simple();

	List<CiIdentExpression> parts();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aIncrement);

	@Override
	void setLeft(CiExpression iexpression);
}
