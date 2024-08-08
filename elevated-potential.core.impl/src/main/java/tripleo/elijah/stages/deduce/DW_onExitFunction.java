package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.deduce.nextgen.*;
import tripleo.elijah.stages.deduce.post_bytecode.*;
import tripleo.elijah.stages.deduce.tastic.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.instructions.*;
import tripleo.elijah.work.*;

import java.util.*;
import java.util.function.*;

class DW_onExitFunction {
	private final DeduceTypes2    deduceTypes2;
	private final BaseEvaFunction generatedFunction;
	private final Context         fdCtx;
	private final Context         context;

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
		for (@NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			vte.resolve_var_table_entry_for_exit_function();
		}

	}

	public void runRunnables() {
		for (@NotNull Runnable runnable : deduceTypes2.onRunnables) {
			runnable.run();
		}
	}

	void attachVTEs(final @NotNull BaseEvaFunction generatedFunction, final DeduceTypes2 aDeduceTypes2) {
		//
		// ATTACH A TYPE TO VTE'S
		// CONVERT USER TYPES TO USER_CLASS TYPES
		//
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
//                                              LOG.info("704 "+vte.type.attached+" "+vte.potentialTypes());
			final DeduceElement3_VariableTableEntry vte_de = vte.getDeduceElement3();
			vte_de.setDeduceTypes2(aDeduceTypes2, generatedFunction);
			vte_de.mvState(null, DeduceElement3_VariableTableEntry.ST.EXIT_CONVERT_USER_TYPES);
		}
	}

	void checkVteList(final @NotNull BaseEvaFunction generatedFunction, final DeduceTypes2 aDeduceTypes2) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			__checkVteList_each(vte, aDeduceTypes2);
		}
	}

	void attachIDTEs(final @NotNull BaseEvaFunction generatedFunction, final Context aFd_ctx, final Context aContext, final DeduceTypes2 aDeduceTypes2) {
		//
		// ATTACH A TYPE TO IDTE'S
		//
		for (@NotNull final IdentTableEntry ite : generatedFunction.idte_list) {
			final DeduceElement3_IdentTableEntry ite_de = ite.getDeduceElement3(aDeduceTypes2, generatedFunction);
			ite_de._ctxts(aFd_ctx, aContext);
			ite_de.mvState(null, DeduceElement3_IdentTableEntry.ST.EXIT_GET_TYPE);
		}
	}

	void resolveEachTypename(final @NotNull BaseEvaFunction generatedFunction, final @NotNull DeduceTypes2 aDeduceTypes2) {
		// TODO why are we doing this?
		final DeduceTypes2.Resolve_each_typename ret = aDeduceTypes2._inj().new_Resolve_each_typename(aDeduceTypes2.phase, aDeduceTypes2, aDeduceTypes2._errSink());
		for (final TypeTableEntry typeTableEntry : generatedFunction.tte_list) {
			ret.action(typeTableEntry);
		}
	}

	void doDependencySubscriptions(final @NotNull BaseEvaFunction generatedFunction, final @NotNull DeduceTypes2 aDeduceTypes2) {
		final @NotNull WorkManager               workManager = aDeduceTypes2.wm;// _inj().new_WorkManager();
		@NotNull final DeduceTypes2.Dependencies deps        = aDeduceTypes2._inj().new_Dependencies(/* this, *//* phase, this, errSink */workManager, aDeduceTypes2);
		deps.subscribeTypes(generatedFunction.dependentTypesSubject());
		deps.subscribeFunctions(generatedFunction.dependentFunctionSubject());
//                                              for (@NotNull GenType genType : generatedFunction.dependentTypes()) {
//                                                      deps.action_type(genType, workManager);
//                                              }
//                                              for (@NotNull FunctionInvocation dependentFunction : generatedFunction.dependentFunctions()) {
//                                                      deps.action_function(dependentFunction, workManager);
//                                              }
		final int x = workManager.totalSize();

		// FIXME 06/14
		workManager.drain();

		aDeduceTypes2.phase.addDrs(generatedFunction, generatedFunction.drs);

		aDeduceTypes2.phase.doneWait(aDeduceTypes2, generatedFunction);
	}

	void resolveFunctionReturnType(final @NotNull BaseEvaFunction generatedFunction, final @NotNull DeduceTypes2 aDeduceTypes2) {
		//
		// RESOLVE FUNCTION RETURN TYPES
		//
		aDeduceTypes2.resolve_function_return_type(generatedFunction);
	}

	void doExitPostVteSomething(final @NotNull BaseEvaFunction generatedFunction, final Context aFd_ctx, final DeduceTypes2 aDeduceTypes2) {
		__on_exit__post_vte_something(generatedFunction, aFd_ctx, aDeduceTypes2);
	}

	void doLoookupFunctions(final @NotNull BaseEvaFunction generatedFunction, final @NotNull DeduceTypes2 aDeduceTypes2) {
		//
		// LOOKUP FUNCTIONS
		//
		{
			@NotNull
			WorkList wl = aDeduceTypes2._inj().new_WorkList();

			for (@NotNull
			ProcTableEntry pte : generatedFunction.prte_list) {
				final DeduceElement3_ProcTableEntry de3_pte = convertPTE(generatedFunction, pte, aDeduceTypes2);
				de3_pte.lfoe_action(aDeduceTypes2, wl, (j) -> aDeduceTypes2.wm.addJobs(j), new Consumer<DeduceElement3_ProcTableEntry.LFOE_Action_Results>() {
					@Override
					public void accept(final DeduceElement3_ProcTableEntry.LFOE_Action_Results aLFOEActionResults) {
						int y = 2;
					}
				});
			}

			aDeduceTypes2.wm.addJobs(wl);
			// wm.drain();
		}
	}

	void doCheckEvaClassVarTable(final @NotNull BaseEvaFunction generatedFunction, final DeduceTypes2 aDeduceTypes2) {
		checkEvaClassVarTable(generatedFunction, aDeduceTypes2);
	}

	void doCheckExpectations(final @NotNull DeduceTypes2 aDeduceTypes2) {
		aDeduceTypes2._expectations().check();
	}

	void addActivesToPhase(final DeducePhase phase2, final @NotNull DeduceTypes2 aDeduceTypes2) {
		// TODO 24/08/08 May be too much indirection for my taste
		aDeduceTypes2._a_active.addToPhase(phase2);
	}

	void addDrIdents(final @NotNull BaseEvaFunction generatedFunction, final DeduceTypes2 aDeduceTypes2) {
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			generatedFunction.drs.add(aDeduceTypes2._inj().new_DR_Ident(identTableEntry, generatedFunction, aDeduceTypes2));
		}
	}

	void addDrVTEs(final @NotNull BaseEvaFunction generatedFunction) {
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			generatedFunction.drs.add(DR_Ident.create(variableTableEntry, generatedFunction));
		}
	}

	void addDrsToPhase(final @NotNull BaseEvaFunction generatedFunction, final DeducePhase phase2) {
		var dww = this;
		phase2.addDrs(generatedFunction, generatedFunction.drs);
	}

	void phaseResolveModulePromises(final DeduceTypes2 aDeduceTypes2) {
		for (DT_External external : aDeduceTypes2._externals()) {
			// TODO there is a plugin for this...
			// external.onTargetModule(tm -> {phase.modulePromise(tm, external::actualise);}); //[T1160118]
			aDeduceTypes2.phase.modulePromise(external.targetModule(), external::actualise);
		}
	}

	private void __checkVteList_each(final @NotNull VariableTableEntry vte, final DeduceTypes2 aDeduceTypes2) {
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
					aDeduceTypes2._errSink().reportError("369 ARG USER type (not deduced) " + vte);
				}
			} else {
				// 08/13 errSink.reportError("457 ARG type not deduced/attached " + vte);
			}
		}
	}

	void __on_exit__post_vte_something(final @NotNull BaseEvaFunction generatedFunction,
									   final Context aFd_ctx, final DeduceTypes2 aDeduceTypes2) {
		int y = 2;
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			final @NotNull Collection<TypeTableEntry> pot = variableTableEntry.potentialTypes();
			if (pot.size() == 1 && variableTableEntry.getGenType().isNull()) {
				final OS_Type x = pot.iterator().next().getAttached();
				if (x != null)
					if (x.getType() == OS_Type.Type.USER_CLASS) {
						try {
							final @NotNull GenType yy = aDeduceTypes2.resolve_type(x, aFd_ctx);
							// HACK TIME
							if (yy.getResolved() == null && yy.getTypeName().getType() == OS_Type.Type.USER_CLASS) {
								yy.setResolved(yy.getTypeName());
								yy.setTypeName(null);
							}

							yy.genCIForGenType2(aDeduceTypes2);
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

	@NotNull DeduceElement3_ProcTableEntry convertPTE(final @NotNull BaseEvaFunction generatedFunction,
													  final @NotNull ProcTableEntry pte, final DeduceTypes2 aDeduceTypes2) {
		final DeduceElement3_ProcTableEntry de3_pte = pte.getDeduceElement3(aDeduceTypes2, generatedFunction);
		return de3_pte;
	}

	private void checkEvaClassVarTable(final @NotNull BaseEvaFunction generatedFunction, final DeduceTypes2 aDeduceTypes2) {
		// for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
		// variableTableEntry.setDeduceTypes2(this, aContext, generatedFunction);
		// }
		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			identTableEntry.getDeduceElement3(aDeduceTypes2, generatedFunction).mvState(
					null,
					DeduceElement3_IdentTableEntry.ST.CHECK_EVA_CLASS_VAR_TABLE);
			// identTableEntry.setDeduceTypes2(this, aContext, generatedFunction);

		}
	}
}
