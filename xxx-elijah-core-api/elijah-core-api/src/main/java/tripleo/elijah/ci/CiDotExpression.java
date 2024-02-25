package tripleo.elijah.ci;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;

public interface CiDotExpression extends CiExpression {
	@NotNull
	CiExpression getRight();

	@Override
	boolean is_simple();

	@Override
	String toString();
}
