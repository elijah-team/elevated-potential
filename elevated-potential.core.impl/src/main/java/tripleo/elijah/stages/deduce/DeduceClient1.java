package tripleo.elijah.stages.deduce;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.declarations.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;

public class DeduceClient1 {
	private final DeduceTypes2 dt2;

	@Contract(pure = true)
	public DeduceClient1(DeduceTypes2 aDeduceTypes2) {
		dt2 = aDeduceTypes2;
	}

	public DeduceTypes2 _deduceTypes2() {
		return dt2;
	}

	public DeduceTypes2Injector _inj() {
		return dt2._inj();
	}

	public @Nullable OS_Element _resolveAlias(@NotNull AliasStatementImpl aAliasStatement) {
		return DeduceLookupUtils._resolveAlias(aAliasStatement, dt2);
	}

	public @NotNull DeferredMember deferred_member(DeduceElementWrapper aParent, IInvocation aInvocation,
			VariableStatementImpl aVariableStatement, @NotNull IdentTableEntry aIdentTableEntry) {
		return dt2.deferred_member(aParent, aInvocation, aVariableStatement, aIdentTableEntry);
	}

	public void found_element_for_ite(BaseEvaFunction aGeneratedFunction, @NotNull IdentTableEntry aIte,
			OS_Element aX, Context aCtx) {
		dt2.found_element_for_ite(aGeneratedFunction, aIte, aX, aCtx, dt2.central());
	}

	public void genCI(final @NotNull GenType aResult, final TypeName aNonGenericTypeName) {
		aResult.genCI(aNonGenericTypeName, dt2, dt2.errSink, dt2.phase);
	}

	public void genCIForGenType2(final @NotNull GenType genType) {
		genType.genCIForGenType2(dt2);
	}

	public @Nullable IInvocation getInvocationFromBacklink(InstructionArgument aInstructionArgument) {
		return dt2.getInvocationFromBacklink(aInstructionArgument);
	}

	public @NotNull List<TypeTableEntry> getPotentialTypesVte(@NotNull VariableTableEntry aVte) {
		return dt2.getPotentialTypesVte(aVte);
	}

	public void LOG_err(String aS) {
		dt2.LOG.err(aS);
	}

	public @Nullable ClassInvocation registerClassInvocation(final @NotNull ClassStatement aClassStatement,
			final String aS) {
		return dt2.phase.registerClassInvocation(aClassStatement, aS, new ReadySupplier_1<>(dt2));
	}

	public @NotNull GenType resolve_type(@NotNull OS_Type aType, Context aCtx) throws ResolveError {
		return dt2.resolve_type(aType, aCtx);
	}
}
