package tripleo.elijah.ci;

//import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionKind;

//import tripleo.elijah.ci.cil.Helpers;
//import tripleo.elijah.ci.cil.IdentExpressionImpl;

import tripleo.elijah.diagnostic.Locatable;

public interface CiIdentExpression extends CiExpression, /*Resolvable,*/ Locatable {
//	@Contract("_ -> new")
//	static @NotNull CiIdentExpression forString(String string) {
//		return new IdentExpressionImpl(Helpers.makeToken(string));
//	}

	@Override
    CiExpressionKind getKind();

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
	void setKind(CiExpressionKind aIncrement);

	@Override
	void setLeft(CiExpression iexpression);
}
