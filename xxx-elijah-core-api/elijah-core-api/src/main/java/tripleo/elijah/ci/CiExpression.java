package tripleo.elijah.ci;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;

public interface CiExpression {
	@NotNull ExpressionKind getKind();

	CiExpression getLeft();

	boolean is_simple();

	default String printableString() {
		throw new UnintendedUseException();
	}

	String repr_();

	void setKind(ExpressionKind aExpressionKind);

	void setLeft(CiExpression iexpression);

	@Override
	String toString();
}
