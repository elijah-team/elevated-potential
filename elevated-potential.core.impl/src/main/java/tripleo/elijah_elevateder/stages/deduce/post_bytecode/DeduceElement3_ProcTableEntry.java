package tripleo.elijah_elevateder.stages.deduce.post_bytecode;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.types.OS_FuncType;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_elevated_durable.lang_impl.*;
import tripleo.elijah_elevateder.stages.deduce.*;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_elevateder.stages.instructions.*;
import tripleo.elijah_elevateder.work.EDL_WorkList;
import tripleo.elijah_fluffy.util.*;
import tripleo.elijah_prolific.v.V;

import java.util.*;
import java.util.function.Consumer;

public class DeduceElement3_ProcTableEntry implements IDeduceElement3 {

	@Nullable
	private final DeduceTypes2 deduceTypes2;
	private final BaseEvaFunction generatedFunction;
	private final ProcTableEntry  principal;
	private       Instruction     instruction;

	public DeduceElement3_ProcTableEntry(final ProcTableEntry aProcTableEntry,
										 final DeduceTypes2 aDeduceTypes2,
										 final BaseEvaFunction aGeneratedFunction) {
		principal         = aProcTableEntry;
		deduceTypes2      = aDeduceTypes2;
		generatedFunction = aGeneratedFunction;
	}

	public void _action_002_no_resolved_element(final InstructionArgument _backlink,
												final @NotNull ProcTableEntry backlink,
												final DeduceTypes2.@NotNull DeduceClient3 dc,
												final @NotNull IdentTableEntry ite,
												final @NotNull ErrSink errSink,
												final @NotNull DeducePhase phase) {
		final OS_Element resolvedElement = backlink.getResolvedElement();

		if (resolvedElement == null)
			return; // throw new AssertionError(); // TODO feb 20

		try {
			final LookupResultList lrl2 = dc.lookupExpression(ite.getIdent(), resolvedElement.getContext());
			@Nullable final OS_Element best = lrl2.chooseBest(null);
			assert best != null;
			ite.setStatus(BaseTableEntry.Status.KNOWN, dc._deduceTypes2()._inj().new_GenericElementHolder(best));
		} catch (final ResolveError aResolveError) {
			errSink.reportDiagnostic(aResolveError);
			assert false;
		}

		action_002_1(principal, ite, false, phase, dc);
	}

	DeduceTypes2.DeduceTypes2Injector _inj() {
		return Objects.requireNonNull(deduceTypes2())._inj();
	}

	private void action_002_1(@NotNull final ProcTableEntry pte, @NotNull final IdentTableEntry ite,
							  final boolean setClassInvocation, final @NotNull DeducePhase phase,
							  final DeduceTypes2.@NotNull DeduceClient3 dc) {
		final OS_Element resolvedElement = ite.getResolvedElement();

		assert resolvedElement != null;

		ClassInvocation ci = null;

		var dt2 = dc._deduceTypes2();

		if (pte.getFunctionInvocation() == null) {
			@NotNull final FunctionInvocation fi;

			if (resolvedElement instanceof ClassStatement) {
				// assuming no constructor name or generic parameters based on function syntax
				ci = _inj().new_ClassInvocation((ClassStatement) resolvedElement, null, new ReadySupplier_1<>(dt2));
				ci = phase.registerClassInvocation(ci);
				fi = phase.newFunctionInvocation(null, pte, ci);
			} else if (resolvedElement instanceof final @NotNull FunctionDef functionDef) {
				final IInvocation invocation = dc.getInvocation((EvaFunction) generatedFunction);
				fi = phase.newFunctionInvocation((BaseFunctionDef) functionDef, pte, invocation);
				if (functionDef.getParent() instanceof ClassStatement) {
					final ClassStatement classStatement = (ClassStatement) fi.getFunction().getParent();
					ci = _inj().new_ClassInvocation(classStatement, null, new ReadySupplier_1<>(dt2)); // TODO generics
					ci = phase.registerClassInvocation(ci);
				}
			} else {
				throw new IllegalStateException();
			}

			if (setClassInvocation) {
				if (ci != null) {
					pte.setClassInvocation(ci);
				} else
					SimplePrintLoggerToRemoveSoon.println_err2("542 Null ClassInvocation");
			}

			pte.setFunctionInvocation(fi);
		}

//        el   = resolvedElement;
//        ectx = el.getContext();
	}

	@Override
	public @Nullable DeduceTypes2 deduceTypes2() {
		return deduceTypes2;
	}

	@Override
	public @NotNull DED elementDiscriminator() {
		return new DED.DED_PTE(principal);
	}

	@Override
	public BaseEvaFunction generatedFunction() {
		return generatedFunction;
	}

	@Override
	public GenType genType() {
		throw new UnsupportedOperationException("no type for PTE");
	} // TODO check correctness

	@Override
	public OS_Element getPrincipal() {
		// return principal.getDeduceElement3(deduceTypes2,
		// generatedFunction).getPrincipal(); // README infinite loop

		return principal.getResolvedElement();// getDeduceElement3(deduceTypes2, generatedFunction).getPrincipal();
	}

	@Override
	public @NotNull DeduceElement3_Kind kind() {
		return DeduceElement3_Kind.GEN_FN__PTE;
	}

	@Override
	public void resolve(final Context aContext, final DeduceTypes2 dt2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resolve(final IdentIA aIdentIA, final Context aContext, final FoundElement aFoundElement) {
		throw new UnsupportedOperationException();
	}

	public void doFunctionInvocation() {
		final FunctionInvocation fi = principal.getFunctionInvocation();

		if (fi == null) {
			if (principal.__debug_expression instanceof final @NotNull ProcedureCallExpression exp) {
				final IExpression left = exp.getLeft();

				if (left instanceof final @NotNull DotExpression dotleft) {

					if (dotleft.getLeft() instanceof final @NotNull IdentExpression rl
							&& dotleft.getRight() instanceof final @NotNull IdentExpression rr) {

						if (rl.getText().equals("a1")) {
							final EvaClass[] gc = new EvaClass[1];

							final InstructionArgument vrl = generatedFunction.vte_lookup(rl.getText());

							assert vrl != null;

							final VariableTableEntry vte = ((IntegerIA) vrl).getEntry();

							vte.typePromise().then(left_type -> {
								final ClassStatement cs = left_type.getResolved().getClassOf(); // TODO we want a
								// DeduceClass here.
								// EvaClass may suffice

								final ClassInvocation ci = deduceTypes2._phase().registerClassInvocation(cs, deduceTypes2);
								ci.resolvePromise().then(gc2 -> {
									gc[0] = gc2;
								});

								final LookupResultList lrl = cs.getContext().lookup(rr.getText());
								@Nullable final OS_Element best = lrl.chooseBest(null);

								if (best != null) {
									final FunctionDef fun = (FunctionDef) best;

									final FunctionInvocation fi2 = deduceTypes2._phase().newFunctionInvocation(fun,
																											   null, ci); // TODO pte??

									principal.setFunctionInvocation(fi2); // TODO pte above

									final WlGenerateFunction j = fi2.generateFunction(deduceTypes2, best);
									j.run(null);

									final @NotNull IdentTableEntry ite = ((IdentIA) principal.expression_num)
											.getEntry();
									final OS_Type attached = ite.type.getAttached();

									fi2.generatePromise().then(gf -> {
										final int y4 = 4;
									});

									if (attached instanceof final @NotNull OS_FuncType funcType) {

										final EvaClass x = gc[0];

										fi2.generatePromise().then(gf -> {
											final int y4 = 4;
										});

										final int y = 2;
									}
									final int yy = 2;
								}
							});
							final int y = 2;
						} else {
//							System.err.println("=== 399-147 ================================");
							V.asv(V.e.d399_147, rl.getText());
						}
					}
				}
			}
		}
	}

	public BaseEvaFunction getGeneratedFunction() {
		return generatedFunction;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(final Instruction aInstruction) {
		instruction = aInstruction;
	}

	public ProcTableEntry getTablePrincipal() {
		return principal;
	}

	public String getPTEString() {
		final var aProcTableEntry  =  principal;
		String pte_string;
		if (aProcTableEntry == null)
			pte_string = "[]";
		else {
			pte_string = aProcTableEntry.getLoggingString(deduceTypes2());
		}
		return pte_string;
	}

	public static final class LFOE_Action_Results {
		private final @NotNull DeduceTypes2                 aDeduceTypes2;
		private final @NotNull WorkList                     wl;
		private final @NotNull Consumer<WorkList>           addJobs;
		private final @NotNull List<? extends Object>       actualResults;
		private final @NotNull Eventual<FunctionInvocation> createdFunctionInvocation;

		public LFOE_Action_Results(@NotNull DeduceTypes2 aDeduceTypes2, // input/not really needed
								   @NotNull WorkList wl,                // input/not really needed
								   @NotNull Consumer<WorkList> addJobs, // input/not really needed
								   @NotNull List<? extends Object> actualResults,
								   @NotNull Eventual<FunctionInvocation> createdFunctionInvocation
								  ) {
			this.aDeduceTypes2             = aDeduceTypes2;
			this.wl                        = wl;
			this.addJobs                   = addJobs;
			this.actualResults             = actualResults;
			this.createdFunctionInvocation = createdFunctionInvocation;
		}

		public @NotNull DeduceTypes2 aDeduceTypes2() {
			return aDeduceTypes2;
		}

		public @NotNull WorkList wl() {
			return wl;
		}

		public @NotNull Consumer<WorkList> addJobs() {
			return addJobs;
		}

		public @NotNull List<? extends Object> actualResults() {
			return actualResults;
		}

		public @NotNull Eventual<FunctionInvocation> createdFunctionInvocation() {
			return createdFunctionInvocation;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (LFOE_Action_Results) obj;
			return Objects.equals(this.aDeduceTypes2, that.aDeduceTypes2) &&
					Objects.equals(this.wl, that.wl) &&
					Objects.equals(this.addJobs, that.addJobs) &&
					Objects.equals(this.actualResults, that.actualResults) &&
					Objects.equals(this.createdFunctionInvocation, that.createdFunctionInvocation);
		}

		@Override
		public int hashCode() {
			return Objects.hash(aDeduceTypes2, wl, addJobs, actualResults, createdFunctionInvocation);
		}

		@Override
		public String toString() {
			return "LFOE_Action_Results[" +
					"aDeduceTypes2=" + aDeduceTypes2 + ", " +
					"wl=" + wl + ", " +
					"addJobs=" + addJobs + ", " +
					"actualResults=" + actualResults + ", " +
					"createdFunctionInvocation=" + createdFunctionInvocation + ']';
		}
	}

	public void lfoe_action(final @NotNull DeduceTypes2 aDeduceTypes2,
							final @NotNull WorkList wl,
							final @NotNull Consumer<WorkList> addJobs,
							final @NotNull Consumer<LFOE_Action_Results> resultconsumer) {
		//assert aDeduceTypes2 == deduceTypes2;
		if (aDeduceTypes2 != deduceTypes2) {
			SimplePrintLoggerToRemoveSoon.println_err_4("503262 deduceTypes divergence");
			//throw new AssertionError();
		}

		final __LFOE_Q                     q                = new __LFOE_Q(aDeduceTypes2.wm, wl, aDeduceTypes2);
		final Eventual<FunctionInvocation> efi              = new Eventual<>();
		efi.then(new lfoe_action__FunctionInvocationDoneCallback(this, aDeduceTypes2, addJobs, q, wl));

		final List<? extends Object>       actualResultList = new ArrayList<>();
		final LFOE_Action_Results          virtualResult    = new LFOE_Action_Results(aDeduceTypes2, wl, addJobs, actualResultList, efi);
		final FunctionInvocation fi2 = principal.getFunctionInvocation();

		try {
			if (fi2 == null) {
				__lfoe_action__getFunctionInvocation(principal, aDeduceTypes2).then(efi::resolve);

				//if (fi == null)
				//	return;
			} else {
				efi.resolve(fi2);
			}
		} finally {
			resultconsumer.accept(virtualResult);
		}
	}


	public static final class _1 {
		private final          FunctionInvocation functionInvocation;
		private final          NamespaceStatement parentNamespace;
		private final          WorkList           workList;
		private final          DeducePhase        deducePhase;
		private final          Consumer<WorkList> addJobs;
		private final @NotNull __LFOE_Q           q;

		public _1(
				FunctionInvocation functionInvocation,
				NamespaceStatement parentNamespace,
				WorkList workList,
				DeducePhase deducePhase,
				Consumer<WorkList> addJobs,
				@NotNull __LFOE_Q q
				 ) {
			this.functionInvocation = functionInvocation;
			this.parentNamespace    = parentNamespace;
			this.workList           = workList;
			this.deducePhase        = deducePhase;
			this.addJobs            = addJobs;
			this.q                  = q;
		}

		public FunctionInvocation functionInvocation() {
			return functionInvocation;
		}

		public NamespaceStatement parentNamespace() {
			return parentNamespace;
		}

		public WorkList workList() {
			return workList;
		}

		public DeducePhase deducePhase() {
			return deducePhase;
		}

		public Consumer<WorkList> addJobs() {
			return addJobs;
		}

		public @NotNull __LFOE_Q q() {
			return q;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (_1) obj;
			return Objects.equals(this.functionInvocation, that.functionInvocation) &&
					Objects.equals(this.parentNamespace, that.parentNamespace) &&
					Objects.equals(this.workList, that.workList) &&
					Objects.equals(this.deducePhase, that.deducePhase) &&
					Objects.equals(this.addJobs, that.addJobs) &&
					Objects.equals(this.q, that.q);
		}

		@Override
		public int hashCode() {
			return Objects.hash(functionInvocation, parentNamespace, workList, deducePhase, addJobs, q);
		}

		@Override
		public String toString() {
			return "_1[" +
					"functionInvocation=" + functionInvocation + ", " +
					"parentNamespace=" + parentNamespace + ", " +
					"workList=" + workList + ", " +
					"deducePhase=" + deducePhase + ", " +
					"addJobs=" + addJobs + ", " +
					"q=" + q + ']';
		}
	}

	public static final class _0 { // TODO 10/18 $env.process($this)
		private final @NotNull FunctionInvocation functionInvocation;
		private final          ClassInvocation    classInvocation;
		private final          ClassStatement     parentClass;
		private final @NotNull DeducePhase        deducePhase;
		private final          Consumer<WorkList> addJobs;
		private final @NotNull _LFOE_Q            q;

		public _0(
				@NotNull FunctionInvocation functionInvocation,
				ClassInvocation classInvocation,
				ClassStatement parentClass,
				@NotNull DeducePhase deducePhase,
				Consumer<WorkList> addJobs,
				@NotNull _LFOE_Q q
				 ) {
			this.functionInvocation = functionInvocation;
			this.classInvocation    = classInvocation;
			this.parentClass        = parentClass;
			this.deducePhase        = deducePhase;
			this.addJobs            = addJobs;
			this.q                  = q;
		}

		public @NotNull FunctionInvocation functionInvocation() {
			return functionInvocation;
		}

		public ClassInvocation classInvocation() {
			return classInvocation;
		}

		public ClassStatement parentClass() {
			return parentClass;
		}

		public @NotNull DeducePhase deducePhase() {
			return deducePhase;
		}

		public Consumer<WorkList> addJobs() {
			return addJobs;
		}

		public @NotNull _LFOE_Q q() {
			return q;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (_0) obj;
			return Objects.equals(this.functionInvocation, that.functionInvocation) &&
					Objects.equals(this.classInvocation, that.classInvocation) &&
					Objects.equals(this.parentClass, that.parentClass) &&
					Objects.equals(this.deducePhase, that.deducePhase) &&
					Objects.equals(this.addJobs, that.addJobs) &&
					Objects.equals(this.q, that.q);
		}

		@Override
		public int hashCode() {
			return Objects.hash(functionInvocation, classInvocation, parentClass, deducePhase, addJobs, q);
		}

		@Override
		public String toString() {
			return "_0[" +
					"functionInvocation=" + functionInvocation + ", " +
					"classInvocation=" + classInvocation + ", " +
					"parentClass=" + parentClass + ", " +
					"deducePhase=" + deducePhase + ", " +
					"addJobs=" + addJobs + ", " +
					"q=" + q + ']';
		}
	}

	void __lfoe_action__proceed(_0 o0) {
		@NotNull FunctionInvocation fi = o0.functionInvocation();
		ClassInvocation ci = o0.classInvocation();
		ClassStatement aParent = o0.parentClass();
		final Consumer<WorkList> addJobs = o0.addJobs();
		final @NotNull _LFOE_Q q = o0.q();
		final @NotNull DeducePhase phase = o0.deducePhase();

		ci = phase.registerClassInvocation(ci);

		@NotNull ClassStatement kl = ci.getKlass(); // TODO Don't you see aParent??
		assert kl != null;

		final FunctionDef fd2   = fi.getFunction();
		int               state = 0;

		if (fd2 == LangGlobals.defaultVirtualCtor) {
			if (fi.pte.getArgs().size() == 0)
				state = 1;
			else
				state = 2;
		} else if (fd2 instanceof ConstructorDef) {
			if (fi.getClassInvocation().getConstructorName() != null)
				state = 3;
			else
				state = 2;
		} else {
			if (fi.getFunction() == null && fi.getClassInvocation() != null)
				state = 3;
			else
				state = 4;
		}

		final GenerateFunctions generateFunctions = null;

		switch (state) {
		case 1:
			assert fi.pte.getArgs().size() == 0;
			// default ctor
			q.enqueue_default_ctor(generateFunctions, fi, null);
			break;
		case 2:
			q.enqueue_ctor(generateFunctions, fi, fd2.getNameNode());
			break;
		case 3:
			// README this is a special case to generate constructor
			// TODO should it be GenerateDefaultCtor? (check args size and ctor-name)
			final String constructorName = fi.getClassInvocation().getConstructorName();
			final @NotNull IdentExpression constructorName1 = constructorName != null
					? IdentExpressionImpl.forString(constructorName)
					: null;
			q.enqueue_ctor(generateFunctions, fi, constructorName1);
			break;
		case 4:
			q.enqueue_function(generateFunctions, fi, phase.getCodeRegistrar());
			break;
		default:
			throw new NotImplementedException();
		}

		// addJobs.accept(wl);
	}

	void __lfoe_action__proceed(_1 o1) {
		final @NotNull FunctionInvocation fi      = o1.functionInvocation();
		final @NotNull NamespaceStatement aParent = o1.parentNamespace();
		final @NotNull WorkList           wl      = o1.workList();
		final @NotNull DeducePhase        phase   = o1.deducePhase();
		final @NotNull Consumer<WorkList> addJobs = o1.addJobs();
		final @NotNull _LFOE_Q            q       = o1.q();

		// ci = phase.registerClassInvocation(ci);

		final @NotNull OS_Module module1 = aParent.getContext().module();

		final NamespaceInvocation nsi = phase.registerNamespaceInvocation(aParent);

		final ICodeRegistrar cr = phase.getCodeRegistrar();

		q.enqueue_namespace(() -> phase.generatePhase.getGenerateFunctions(module1), nsi, phase.generatedClasses, cr);
		q.enqueue_function(() -> phase.generatePhase.getGenerateFunctions(module1), fi, cr);

		// addJobs.accept(wl); // TODO 10/17 should we remove this or uncomment it?
	}

	// TODO class Action<FunctionInvocation>
	private static @NotNull Eventual<FunctionInvocation> __lfoe_action__getFunctionInvocation(final @NotNull ProcTableEntry pte,
																							  final @NotNull DeduceTypes2 aDeduceTypes2) {
		final Eventual<FunctionInvocation> efi = new Eventual<>();

		// Action<FunctionInvocation> action = new ...
		// action.provide(ClassStatement.class, (left-is-class ...|left-is-function), ...
		// action.fail(e-is-null)
		// action.fail(e-is-not-class-or-function)
		FunctionInvocation fi;
		if (pte.__debug_expression != null && pte.expression_num != null) {
			if (pte.__debug_expression instanceof final @NotNull ProcedureCallExpression exp) {
				if (exp.getLeft() instanceof final @NotNull IdentExpression expLeft) {
					final @NotNull String           left = expLeft.getText();
					final @NotNull LookupResultList lrl  = expLeft.getContext().lookup(left);
					final @Nullable OS_Element      e    = lrl.chooseBest(null);
					if (e != null) {
						if (e instanceof ClassStatement classStatement) {
							final ClassInvocation ci = aDeduceTypes2.phase.registerClassInvocation(classStatement, aDeduceTypes2);
							pte.setClassInvocation(ci);
						} else if (e instanceof final @NotNull FunctionDef functionDef) {
							final OS_Element parent = functionDef.getParent();

							if (parent instanceof ClassStatement classStatement) {
								final ClassInvocation ci = aDeduceTypes2.phase.registerClassInvocation(classStatement, aDeduceTypes2);
								pte.setClassInvocation(ci);
							}
						} else {
							throw new NotImplementedException();
						}
					}
				}
			}
		}

		ClassInvocation invocation = pte.getClassInvocation();

		if (invocation == null && pte
				.getFunctionInvocation() != null/* never true if we are in this function (check only use guard)! */) {
			invocation = pte.getFunctionInvocation().getClassInvocation();
		}
		if (invocation == null) {
			final Diagnostic diagnostic = Diagnostic.withMessage("523523",
														"can't find invocation in __lfoe_action__getFunctionInvocation",
														Diagnostic.Severity.WARN); // "WARN" !!
			efi.fail(diagnostic);
			return efi;
		}

		final DeduceElement3_ProcTableEntry de3_pte = pte.getDeduceElement3(aDeduceTypes2, pte.__gf); // !! pte.__gf
		//de3_pte.();

		@NotNull FunctionInvocation fi2 = aDeduceTypes2._phase().newFunctionInvocation(LangGlobals.defaultVirtualCtor, pte,
																					   invocation);

		final _LFOE_Q q = new __LFOE_Q(aDeduceTypes2.wm, new EDL_WorkList(), aDeduceTypes2);

		final GenerateFunctions generateFunctions = aDeduceTypes2.getGenerateFunctions(invocation.getKlass().getContext().module());

		final ClassInvocation finalInvocation = invocation;
		q.enqueue_default_ctor(generateFunctions, fi2, (Eventual<BaseEvaFunction> aBaseEvaFunctionEventual) -> {
			aBaseEvaFunctionEventual.then(ef2 -> {
				DeduceElement3_ProcTableEntry zp = aDeduceTypes2.zeroGet(pte, ef2);

				var fi3 = aDeduceTypes2.newFunctionInvocation(ef2.getFD(), pte, finalInvocation, aDeduceTypes2.phase);
				efi.resolve(fi3);
			});
		});

		return efi;
	}
	public boolean sneakResolve_IDTE(@NotNull OS_Element el,
									 @NotNull DeduceElement3_IdentTableEntry aDeduceElement3IdentTableEntry) {
		boolean b = false;

		final IExpression left = principal.__debug_expression.getLeft();
		if (left == aDeduceElement3IdentTableEntry.principal.getIdent()) {
			// TODO is this duplication ok??
			final DeduceElement3_IdentTableEntry.DE3_ITE_Holder de3_ite_holder = _inj().new_DE3_ITE_Holder(el,
																										   aDeduceElement3IdentTableEntry);
			if (principal.getStatus() == BaseTableEntry.Status.UNCHECKED) {
				principal.setStatus(BaseTableEntry.Status.KNOWN, de3_ite_holder);
				de3_ite_holder.commitGenTypeActions();
			}
			b = true; // TODO include this in block above??
		}

		// if (principal.expression_num instanceof IdentIA identIA) {
		// var ite = identIA.getEntry();
		if (principal.expression.getEntry() instanceof IdentTableEntry ite) {
			var ident1 = ite.getIdent();

			assert ident1 instanceof IdentExpression; // README null check

			var ident_name = ident1.getName();

			var u = ident_name.getUnderstandings();

			if (u.size() == 0 || u.size() == 2) {
			} else {
				NotImplementedException.raise();
			}

			// ident_name.addUnderstanding(_inj().new_ENU_LookupResult(...));
			if (el instanceof ClassStatement) {
				ident_name.addUnderstanding(_inj().new_ENU_ClassName());
				ident_name.addUnderstanding(_inj().new_ENU_ConstructorCallTarget()); // FIXME 07/20 look here later
			} else if (el instanceof FunctionDef) {
				ident_name.addUnderstanding(_inj().new_ENU_FunctionName());
			}
		}

		// principal.getIdent().getName().addUnderstanding(_inj().new_ENU_ConstructorCallTarget());
		return b;
	}

	@Override
	public @NotNull String toString() {
		return "DeduceElement3_ProcTableEntry{" + "principal=" + principal + '}';
	}
}
