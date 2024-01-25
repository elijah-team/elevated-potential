package tripleo.elijah.ci.cii;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;

public interface DotExpression extends CiExpression {
	@NotNull
	CiExpression getRight();

	@Override
	boolean is_simple();

	@Override
	String toString();
}
