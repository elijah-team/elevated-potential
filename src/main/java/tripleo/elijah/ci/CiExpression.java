package tripleo.elijah.ci;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;

public interface CiExpression {
	@NotNull CiExpressionKind getKind();

	CiExpression getLeft();

	boolean is_simple();

	default String printableString() {
		throw new UnintendedUseException();
	}

	String repr_();

	void setKind(CiExpressionKind aCiExpressionKind);

	void setLeft(CiExpression iexpression);

	@Override
	String toString();
}
