package tripleo.elijah.stages.deduce;

import java.util.*;
import java.util.function.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.deduce.post_bytecode.*;
import tripleo.elijah.stages.deduce.tastic.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.work.*;

class DW_onExitFunction {
	private final DeduceTypes2    deduceTypes2;
	private final BaseEvaFunction generatedFunction;
	private final Context         fdCtx;
	private       Context         context;

	public DW_onExitFunction(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction, final Context aFdCtx, final Context aContext) {
		deduceTypes2      = aDeduceTypes2;
		generatedFunction = aGeneratedFunction;
		fdCtx             = aFdCtx;
		context           = aContext;
	}

	public void resolve_var_table() {
		//
		// resolve var table. moved from `E'
		//
		for (@NotNull
		VariableTableEntry vte : generatedFunction.vte_list) {
			vte.resolve_var_table_entry_for_exit_function();
		}

	}

	public void runRunnables() {
		for (@NotNull
		Runnable runnable : deduceTypes2.onRunnables) {
			runnable.run();
		}
	}

	void attachVTEs() {
		//
		// ATTACH A TYPE TO VTE'S
		// CONVERT USER TYPES TO USER_CLASS TYPES
		//
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
//                                              LOG.info("704 "+vte.type.attached+" "+vte.potentialTypes());
			final DeduceElement3_VariableTableEntry vte_de = vte.getDeduceElement3();
			vte_de.setDeduceTypes2(deduceTypes2, generatedFunction);
			vte_de.mvState(null, DeduceElement3_VariableTableEntry.ST.EXIT_CONVERT_USER_TYPES);
		}
	}

	void checkVteList() {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			__checkVteList_each(vte);
		}
	}

	void attachIDTEs(final Context aFd_ctx, final Context aContext) {
		//
		// ATTACH A TYPE TO IDTE'S
		//
		for (@NotNull
		final IdentTableEntry ite : generatedFunction.idte_list) {
			final DeduceElement3_IdentTableEntry ite_de = ite.getDeduceElement3(deduceTypes2, generatedFunction);
			ite_de._ctxts(aFd_ctx, aContext);
			ite_de.mvState(null, DeduceElement3_IdentTableEntry.ST.EXIT_GET_TYPE);
		}
	}

	void resolveEachTypename() {
		// TODO why are we doing this?
		final Resolve_each_typename ret = deduceTypes2._inj().new_Resolve_each_typename(deduceTypes2.phase,
				deduceTypes2, deduceTypes2._errSink());
		for (final TypeTableEntry typeTableEntry : generatedFunction.tte_list) {
			ret.action(typeTableEntry);
		}
	}

	void doDependencySubscriptions() {
		final @NotNull WorkManager workManager = deduceTypes2.wm;// _inj().new_WorkManager();
		@NotNull
		final Dependencies deps = deduceTypes2._inj().new_Dependencies(/* this, *//* phase, this, errSink */workManager,
				deduceTypes2);
		deps.subscribeTypes(generatedFunction.dependentTypesSubject());
		deps.subscribeFunctions(generatedFunction.dependentFunctionSubject());
		// for (@NotNull GenType genType : generatedFunction.dependentTypes()) {
//                                                      deps.action_type(genType, workManager);
//                                              }
// for (@NotNull FunctionInvocation dependentFunction :
// generatedFunction.dependentFunctions()) {
//                                                      deps.action_function(dependentFunction, workManager);
//                                              }
		final int x = workManager.totalSize();

		// FIXME 06/14
		workManager.drain();

		deduceTypes2.phase.addDrs(generatedFunction, generatedFunction.drs);

		deduceTypes2.phase.doneWait(deduceTypes2, generatedFunction);
	}

	void resolveFunctionReturnType() {
		//
		// RESOLVE FUNCTION RETURN TYPES
		//
		deduceTypes2.resolve_function_return_type(generatedFunction);
	}

	void doExitPostVteSomething(final Context aFd_ctx) {
		__on_exit__post_vte_something(aFd_ctx);
	}

	void doLoookupFunctions() {
		//
		// LOOKUP FUNCTIONS
		//
		{
			@NotNull
			WorkList wl = deduceTypes2._inj().new_WorkList();

			for (@NotNull
			ProcTableEntry pte : generatedFunction.prte_list) {
				final DeduceElement3_ProcTableEntry de3_pte = convertPTE(pte);
				de3_pte.lfoe_action(deduceTypes2, wl, (j) -> deduceTypes2.wm.addJobs(j), new Consumer<DeduceElement3_ProcTableEntry.LFOE_Action_Results>() {
					@Override
					public void accept(final DeduceElement3_ProcTableEntry.LFOE_Action_Results aLFOEActionResults) {
						// FIXME ReactiveDimension??
						int y = 2;
					}
				});
			}

			deduceTypes2.wm.addJobs(wl);
			// wm.drain();
		}
	}

	void doCheckEvaClassVarTable() {
		checkEvaClassVarTable();
	}

	void doCheckExpectations() {
		deduceTypes2._expectations().check();
	}

	void addActivesToPhase(final DeducePhase phase2) {
		// TODO 24/08/08 May be too much indirection for my taste
		deduceTypes2._a_active.addToPhase(phase2);
	}

	void addDrIdents() {
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			generatedFunction.drs.add(deduceTypes2._inj().new_DR_Ident(identTableEntry, generatedFunction, deduceTypes2));
		}
	}

	void addDrVTEs() {
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			generatedFunction.drs.add(DR_Ident.create(variableTableEntry, generatedFunction));
		}
	}

	void addDrsToPhase(final DeducePhase phase2) {
		phase2.addDrs(generatedFunction, generatedFunction.drs);
	}

	void phaseResolveModulePromises() {
		for (DT_External external : deduceTypes2._externals()) {
			// TODO there is a plugin for this...
			// external.onTargetModule(tm -> {phase.modulePromise(tm, external::actualise);}); //[T1160118]
			deduceTypes2.phase.modulePromise(external.targetModule(), external::actualise);
		}
	}

	private void __checkVteList_each(final @NotNull VariableTableEntry vte) {
		if (vte.getVtt() == VariableTableType.ARG) {
			final TypeTableEntry vteType = vte.getTypeTableEntry();

			if (vteType.genType instanceof ForwardingGenType fgt)
				fgt.unsparkled();

			if (vteType.genType != null) {
				var vte_genType = vte.getGenType();
				if (vte_genType.getNode() != null)
					return;
			}

			final OS_Type attached = vteType.getAttached();
			if (attached != null) {
				if (attached.getType() == OS_Type.Type.USER) {
					// throw new AssertionError();
					deduceTypes2._errSink().reportError("369 ARG USER type (not deduced) " + vte);
				}
			} else {
				// 08/13 errSink.reportError("457 ARG type not deduced/attached " + vte);
			}
		}
	}

	void __on_exit__post_vte_something(final Context aFd_ctx) {
		int y = 2;
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			final @NotNull Collection<TypeTableEntry> pot = variableTableEntry.potentialTypes();
			if (pot.size() == 1 && variableTableEntry.getGenType().isNull()) {
				final OS_Type x = pot.iterator().next().getAttached();
				if (x != null)
					if (x.getType() == OS_Type.Type.USER_CLASS) {
						try {
							final @NotNull GenType yy = deduceTypes2.resolve_type(x, aFd_ctx);
							// HACK TIME
							if (yy.getResolved() == null && yy.getTypeName().getType() == OS_Type.Type.USER_CLASS) {
								yy.setResolved(yy.getTypeName());
								yy.setTypeName(null);
							}

							yy.genCIForGenType2(deduceTypes2);
							variableTableEntry.resolveType(yy);
							variableTableEntry.resolveTypeToClass(yy.getNode());
//								variableTableEntry.dlv.type.resolve(yy);
						} catch (ResolveError aResolveError) {
							aResolveError.printStackTrace();
						}
					}
			}
		}
	}

	@NotNull
	DeduceElement3_ProcTableEntry convertPTE(final @NotNull ProcTableEntry pte) {
		final DeduceElement3_ProcTableEntry de3_pte = pte.getDeduceElement3(deduceTypes2, generatedFunction);
		return de3_pte;
	}

	private void checkEvaClassVarTable() {
		// for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
		// variableTableEntry.setDeduceTypes2(this, aContext, generatedFunction);
		// }
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			identTableEntry.getDeduceElement3(deduceTypes2, generatedFunction).mvState(
					null,
					DeduceElement3_IdentTableEntry.ST.CHECK_EVA_CLASS_VAR_TABLE);
			// identTableEntry.setDeduceTypes2(this, aContext, generatedFunction);

		}
	}

	public void adviseContext(final Context aContext) {
		context = aContext;
	}
}
