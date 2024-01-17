package tripleo.elijah.lang.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang2.*;

public interface VariableStatement extends @NotNull Locatable, OS_Element {
	@Override
	Context getContext();

	String getName();

	IdentExpression getNameToken();

	TypeModifiers getTypeModifiers();

	void initial(IExpression aExpr);

	@NotNull
	IExpression initialValue();

	@Override
	default void serializeTo(SmallWriter sw) {

	}

	void set(TypeModifiers y);

	void setName(IdentExpression s);

	void setTypeName(@NotNull TypeName tn);

	@NotNull
	TypeName typeName();

	@Override
	void visitGen(ElElementVisitor visit);
}
