package tripleo.elijah.ci;

import java.util.*;

import org.jetbrains.annotations.*;

public interface CiExpressionList {
	void add(CiExpression aExpr);

	@NotNull
	Collection<CiExpression> expressions();

	@NotNull
	Iterator<CiExpression> iterator();

	@NotNull
	CiExpression next(/* @NotNull */ CiExpression aExpr);

	int size();

	@Override
	String toString();
}
