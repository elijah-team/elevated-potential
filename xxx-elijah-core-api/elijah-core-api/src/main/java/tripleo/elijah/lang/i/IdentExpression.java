package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.nextgen.names.i.*;
import tripleo.elijah_fluffy.diagnostic.Locatable;

public interface IdentExpression extends IExpression, OS_Element, Locatable {

	@Override
	ExpressionKind getKind();

	@Override
	IExpression getLeft();

	EN_Name getName();

	@NotNull
	String getText();

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

}

//
//
//
