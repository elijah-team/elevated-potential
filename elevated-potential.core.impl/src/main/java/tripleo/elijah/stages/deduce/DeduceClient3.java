package tripleo.elijah.stages.deduce;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.work.*;

public class DeduceClient3 {
	final DeduceTypes2 deduceTypes2;

	public DeduceClient3(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	public DeduceTypes2 _deduceTypes2() {
		return deduceTypes2;
	}

	public void addJobs(final WorkList aWl) {
		deduceTypes2.wm.addJobs(aWl);
	}

	public void found_element_for_ite(final BaseEvaFunction generatedFunction, final @NotNull IdentTableEntry ite,
			final @Nullable OS_Element y, final Context ctx) {
		deduceTypes2.found_element_for_ite(generatedFunction, ite, y, ctx, deduceTypes2.central());
	}

	public void genCIForGenType2(final @NotNull GenType genType) {
		genType.genCIForGenType2(deduceTypes2);
	}

	public @NotNull GenerateFunctions getGenerateFunctions(final @NotNull OS_Module aModule) {
		return deduceTypes2.getGenerateFunctions(aModule);
	}

	public IInvocation getInvocation(final @NotNull EvaFunction aGeneratedFunction) {
		return deduceTypes2.getInvocation(aGeneratedFunction);
	}

	public @NotNull ElLog getLOG() {
		return deduceTypes2.LOG;
	}

	public @NotNull DeducePhase getPhase() {
		return deduceTypes2.phase;
	}

	public @NotNull List<TypeTableEntry> getPotentialTypesVte(final @NotNull VariableTableEntry aVte) {
		return deduceTypes2.getPotentialTypesVte(aVte);
	}

	public LookupResultList lookupExpression(final @NotNull IExpression aExp, final @NotNull Context aContext)
			throws ResolveError {
		return DeduceLookupUtils.lookupExpression(aExp, aContext, deduceTypes2);
	}

	public @NotNull FunctionInvocation newFunctionInvocation(final BaseFunctionDef aFunctionDef,
			final ProcTableEntry aPte, final @NotNull IInvocation aInvocation) {
		return deduceTypes2.newFunctionInvocation(aFunctionDef, aPte, aInvocation, deduceTypes2.phase);
	}

	public @NotNull IElementHolder newGenericElementHolderWithType(final @NotNull OS_Element aElement,
			final @NotNull TypeName aTypeName) {
		final OS_Type typeName;
		if (aTypeName.isNull())
			typeName = null;
		else
			typeName = this.deduceTypes2._inj().new_OS_UserType(aTypeName);
		return this.deduceTypes2._inj().new_GenericElementHolderWithType(aElement, typeName, deduceTypes2);
	}

	public @NotNull GenType resolve_type(final @NotNull OS_Type aType, final Context aContext) throws ResolveError {
		return deduceTypes2.resolve_type(aType, aContext);
	}

	public void resolveIdentIA2_(final @NotNull Context aEctx, final IdentIA aIdentIA,
			final @Nullable List<InstructionArgument> aInstructionArgumentList,
			final @NotNull BaseEvaFunction aGeneratedFunction, final @NotNull FoundElement aFoundElement) {
		deduceTypes2.resolveIdentIA2_(aEctx, aIdentIA, aInstructionArgumentList, aGeneratedFunction, aFoundElement);
	}
}
