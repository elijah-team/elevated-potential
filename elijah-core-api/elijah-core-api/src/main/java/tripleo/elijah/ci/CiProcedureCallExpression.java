package tripleo.elijah.ci;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;

// FIXME wrap IExpression and ExpressionList and ExpressionKind too
public interface CiProcedureCallExpression extends IExpression {
	CiExpressionList exprList();

	CiExpressionList getExpressionList();

	@Override
	@NotNull ExpressionKind getKind();

	@Override
	IExpression getLeft();

	@Override
	boolean is_simple();

	void identifier(IExpression ee);

	String printableString();

	@Override
	String repr_();

	@Override
	void setKind(ExpressionKind aExpressionKind);

	void setExpressionList(CiExpressionList ael);

	@Override
	void setLeft(IExpression iexpression);

	@Override
	String toString();

	void setArgs(CiExpressionList aEl);
}
