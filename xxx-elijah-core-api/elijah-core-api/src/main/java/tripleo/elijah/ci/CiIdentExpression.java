package tripleo.elijah.ci;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci_impl.Helpers;
import tripleo.elijah.ci_impl.IdentExpressionImpl;
import tripleo.elijah.diagnostic.Locatable;

public interface CiIdentExpression extends CiExpression, /*Resolvable,*/ Locatable {
	@Contract("_ -> new")
	static @NotNull CiIdentExpression forString(String string) {
		return new IdentExpressionImpl(Helpers.makeToken(string));
	}

	@Override
	ExpressionKind getKind();

	@Override
	CiExpression getLeft();

	@NotNull
	String getText();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	//void setContext(Context context);

	@Override
	void setKind(ExpressionKind aIncrement);

	@Override
	void setLeft(CiExpression iexpression);
}
