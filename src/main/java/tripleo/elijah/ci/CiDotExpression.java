package tripleo.elijah.ci;

import org.jetbrains.annotations.NotNull;

public interface CiDotExpression extends CiExpression {
	@NotNull CiExpression getRight();

	@Override boolean is_simple();

	@Override String toString();
}
