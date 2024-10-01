package tripleo.elijah.ci.cii;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.cil.Helpers;
import tripleo.elijah.ci.cil.IdentExpressionImpl;
import tripleo.elijah_fluffy.diagnostic.Locatable;

public interface IdentExpression extends CiExpression, /*Resolvable,*/ Locatable {
	@Contract("_ -> new")
	static @NotNull IdentExpression forString(String string) {
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
