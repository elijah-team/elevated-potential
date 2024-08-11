package tripleo.elijah.stages.deduce;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.stages.deduce.declarations.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.stages.logging.*;

public class DeduceClient4 {
	private final DeduceTypes2 deduceTypes2;

	public DeduceClient4(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

	public @Nullable OS_Element _resolveAlias(final @NotNull AliasStatement aAliasStatement) {
		return DeduceLookupUtils._resolveAlias(aAliasStatement, deduceTypes2);
	}

	public @Nullable OS_Element _resolveAlias2(final @NotNull AliasStatementImpl aAliasStatement)
			throws ResolveError {
		return DeduceLookupUtils._resolveAlias2(aAliasStatement, deduceTypes2);
	}

	public @NotNull DeferredMemberFunction deferred_member_function(final OS_Element aParent,
			final IInvocation aInvocation, final @NotNull FunctionDef aFunctionDef,
			final @NotNull FunctionInvocation aFunctionInvocation) {
		return deduceTypes2.deferred_member_function(aParent, aInvocation, aFunctionDef, aFunctionInvocation);
	}

	public void forFunction(final @NotNull FunctionInvocation aFunctionInvocation,
			final @NotNull ForFunction aForFunction) {
		deduceTypes2.forFunction(aFunctionInvocation, aForFunction);
	}

	public void found_element_for_ite(final BaseEvaFunction aGeneratedFunction,
			final @NotNull IdentTableEntry aEntry, final OS_Element aE, final Context aCtx) {
		deduceTypes2.found_element_for_ite(aGeneratedFunction, aEntry, aE, aCtx, deduceTypes2.central());
	}

	public ClassInvocation genCI(final @NotNull GenType aType, final TypeName aGenericTypeName) {
		return aType.genCI(aGenericTypeName, deduceTypes2, __errSink(), deduceTypes2.phase);
	}

	public DeduceTypes2 get() {
		return deduceTypes2;
	}

	public ErrSink getErrSink() {
		return __errSink();
	}

	public IInvocation getInvocation(final @NotNull EvaFunction aGeneratedFunction) {
		return deduceTypes2.getInvocation(aGeneratedFunction);
	}

	public @NotNull ElLog getLOG() {
		return deduceTypes2.LOG;
	}

	public @NotNull OS_Module getModule() {
		return deduceTypes2.module;
	}

	public @NotNull DeducePhase getPhase() {
		return deduceTypes2.phase;
	}

	public @NotNull List<TypeTableEntry> getPotentialTypesVte(final @NotNull EvaFunction aGeneratedFunction,
			final @NotNull InstructionArgument aVte_ia) {
		return deduceTypes2.getPotentialTypesVte(aGeneratedFunction, aVte_ia);
	}

	public OS_Type gt(final @NotNull GenType aType) {
		return deduceTypes2.gt(aType);
	}

	public void implement_calls(final @NotNull BaseEvaFunction aGeneratedFunction, final @NotNull Context aParent,
			final @NotNull InstructionArgument aArg, final @NotNull ProcTableEntry aPte,
			final int aInstructionIndex) {
		if (aGeneratedFunction.deferred_calls.contains(aInstructionIndex)) {
			deduceTypes2.LOG.err("Call is deferred "/* +gf.getInstruction(pc) */ + " " + aPte);
			return;
		}
		Implement_Calls_ ic = deduceTypes2._inj().new_Implement_Calls_(aGeneratedFunction, aParent, aArg, aPte,
				aInstructionIndex, deduceTypes2);
		ic.action();
	}

	public @Nullable OS_Element lookup(final @NotNull IdentExpression aElement, final @NotNull Context aContext)
			throws ResolveError {
		return DeduceLookupUtils.lookup(aElement, aContext, deduceTypes2);
	}

	public LookupResultList lookupExpression(final @NotNull IExpression aExpression,
			final @NotNull Context aContext) throws ResolveError {
		return DeduceLookupUtils.lookupExpression(aExpression, aContext, deduceTypes2);
	}

	public @NotNull FunctionInvocation newFunctionInvocation(final FunctionDef aElement, final ProcTableEntry aPte,
			final @NotNull IInvocation aInvocation) {
		return deduceTypes2.newFunctionInvocation(aElement, aPte, aInvocation, deduceTypes2.phase);
	}

	public void onFinish(final Runnable aRunnable) {
		deduceTypes2.onFinish(aRunnable);
	}

	public <T> @NotNull PromiseExpectation<T> promiseExpectation(final BaseEvaFunction aGeneratedFunction,
			final String aName) {
		return deduceTypes2.promiseExpectation(aGeneratedFunction, aName);
	}

	public void register_and_resolve(final @NotNull VariableTableEntry aVte,
			final @NotNull ClassStatement aClassStatement) {
		deduceTypes2.register_and_resolve(aVte, aClassStatement);
	}

	public @NotNull ClassInvocation registerClassInvocation(final @NotNull ClassInvocation aCi) {
		return deduceTypes2.phase.registerClassInvocation(aCi);
	}

	public @Nullable ClassInvocation registerClassInvocation(final @NotNull ClassStatement aClassStatement,
			final String constructorName) {
		return deduceTypes2.phase.registerClassInvocation(aClassStatement, constructorName,
				new ReadySupplier_1<>(deduceTypes2));
	}

	public NamespaceInvocation registerNamespaceInvocation(final NamespaceStatement aNamespaceStatement) {
		return deduceTypes2.phase.registerNamespaceInvocation(aNamespaceStatement);
	}

	public void reportDiagnostic(final ResolveError aResolveError) {
		__errSink().reportDiagnostic(aResolveError);
	}

	private ErrSink __errSink() {
		return deduceTypes2._errSink();
	}

	public @NotNull GenType resolve_type(final @NotNull OS_Type aTy, final Context aCtx) throws ResolveError {
		return deduceTypes2.resolve_type(aTy, aCtx);
	}

	public void resolveIdentIA_(final @NotNull Context aCtx, final @NotNull IdentIA aIdentIA,
			final @NotNull BaseEvaFunction aGeneratedFunction, final @NotNull FoundElement aFoundElement) {
		deduceTypes2.resolveIdentIA_(aCtx, aIdentIA, aGeneratedFunction, aFoundElement);
	}
}
