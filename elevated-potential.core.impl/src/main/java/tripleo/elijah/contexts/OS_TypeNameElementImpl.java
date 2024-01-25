package tripleo.elijah.contexts;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;

/**
 * An Element that only holds a {@link TypeName}.
 * <p>
 * NOTE: It seems to be connected to {@link ClassContext}
 */
public class OS_TypeNameElementImpl implements OS_TypeNameElement {
	private final ClassContext classContext;
	private final TypeName     typeName;

	public OS_TypeNameElementImpl(final ClassContext aClassContext, TypeName aTypeName) {
		classContext = aClassContext;
		typeName     = aTypeName;
	}

	@Override
	public @NotNull Context getContext() {
		return classContext;
	}

	@Override
	public OS_Element getParent() {
		return classContext.getCarrier();
	}

	@Override
	public TypeName getTypeName() {
		return typeName;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void visitGen(@NotNull ElElementVisitor visit) {
		visit.visitTypeNameElement(this);
	}
}
