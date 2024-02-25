package tripleo.elijah.ci;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.diagnostic.Locatable;

public interface CiNumericExpression extends CiExpression, Locatable {
	@Override
	ExpressionKind getKind();

	@Override
	CiExpression getLeft();

	@Override
	int getLine();

	int getValue();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
		// CiExpression
	void setKind(ExpressionKind aType);

	@Override
	void setLeft(CiExpression aLeft);

	@Override
	String toString();
}
