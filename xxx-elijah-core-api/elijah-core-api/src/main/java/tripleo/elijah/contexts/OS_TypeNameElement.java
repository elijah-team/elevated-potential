package tripleo.elijah.contexts;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;

public interface OS_TypeNameElement extends OS_Element {
	@Override
	@NotNull Context getContext();

	@Override
	OS_Element getParent();

	TypeName getTypeName();

	@Override
	void serializeTo(SmallWriter sw);

	@Override
	void visitGen(@NotNull ElElementVisitor visit);
}
