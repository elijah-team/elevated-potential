package tripleo.elijah.ci;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;
import tripleo.elijah.diagnostic.Locatable;

public interface CiNumericExpression extends CiExpression, Locatable {
	@Override
    CiExpressionKind getKind();

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
	void setKind(CiExpressionKind aType);

	@Override
	void setLeft(CiExpression aLeft);

	@Override
	String toString();
}
