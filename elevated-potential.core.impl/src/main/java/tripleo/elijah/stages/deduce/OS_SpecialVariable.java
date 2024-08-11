package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.util.*;

public class OS_SpecialVariable implements OS_Element {
	private final BaseEvaFunction generatedFunction;
	private final VariableTableType type;
	private final VariableTableEntry variableTableEntry;
	public DeduceLocalVariable.MemberInvocation memberInvocation;

	public OS_SpecialVariable(final VariableTableEntry aVariableTableEntry, final VariableTableType aType,
			final BaseEvaFunction aGeneratedFunction) {
		variableTableEntry = aVariableTableEntry;
		type = aType;
		generatedFunction = aGeneratedFunction;
	}

	@Override
	public Context getContext() {
		return generatedFunction.getFD().getContext();
	}

	@Override
	public @NotNull OS_Element getParent() {
		return generatedFunction.getFD();
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public void visitGen(final ElElementVisitor visit) {
		throw new IllegalArgumentException("not implemented");
	}

	@Nullable
	public IInvocation getInvocation(final @NotNull DeduceTypes2 aDeduceTypes2) {
		final @Nullable IInvocation aInvocation;
		final OS_SpecialVariable specialVariable = this;
		assert specialVariable.type == VariableTableType.SELF;
		// first parent is always a function
		switch (DecideElObjectType.getElObjectType(specialVariable.getParent().getParent())) {
			case CLASS:
				final ClassStatement classStatement = (ClassStatement) specialVariable.getParent().getParent();
				aInvocation = aDeduceTypes2.phase.registerClassInvocation(classStatement, null,
						new ReadySupplier_1<>(aDeduceTypes2)); // TODO generics
				// ClassInvocationMake.withGenericPart(classStatement, null, null, this);
				break;
			case NAMESPACE:
				throw new NotImplementedException(); // README ha! implemented in
			default:
				throw new IllegalArgumentException("Illegal object type for parent");
		}
		return aInvocation;
	}
}
