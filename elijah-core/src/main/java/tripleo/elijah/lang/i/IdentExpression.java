package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.nextgen.names.i.*;
import tripleo.elijah.util.*;

public interface IdentExpression extends IExpression, OS_Element, Locatable {
	@Contract("_ -> new")
	static @NotNull IdentExpression forString(String string) {
		return new IdentExpressionImpl(Helpers0.makeToken(string), "<inline-absent2>");
	}

	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	EN_Name getName();

	@NotNull
	String getText();

	@Override
	OS_Type getType();

	@Override
	boolean is_simple();

	@Override
	String repr_();

	@Override
	void serializeTo(SmallWriter sw);

	void setContext(Context context);

	@Override
	void setKind(ExpressionKind aExpressionKind);

	@Override
	void setLeft(IExpression iexpression);

	@Override
	void setType(OS_Type deducedExpression);
}
