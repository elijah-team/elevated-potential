package tripleo.elijah.ci.cii;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;

public interface StringExpression extends CiExpression {
	@Override
	ExpressionKind getKind();

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
