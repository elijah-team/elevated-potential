package tripleo.elijah.ci;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;

public interface CiCharLitExpression extends CiExpression {
	@Override
    CiExpressionKind getKind();

	@Override
	CiExpression getLeft();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	void setKind(CiExpressionKind aIncrement);

	@Override
	void setLeft(CiExpression iexpression);

	@Override
	String toString();
}
