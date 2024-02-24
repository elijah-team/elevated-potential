package tripleo.elijah.ci;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;

public interface CiStringExpression extends CiExpression {
	@Override
    CiExpressionKind getKind();

	@Override
	CiExpression getLeft();

	String getText();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	void set(String g);

	@Override
	void setLeft(CiExpression iexpression);

	@Override
	String toString();
}
