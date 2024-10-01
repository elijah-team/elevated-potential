package tripleo.elijah_elevateder.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.nextgen.names.i.EN_Understanding;
import tripleo.elijah.lang.nextgen.names.i.EN_Usage;
import tripleo.elijah_fluffy.util.NotImplementedException;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_elevated_durable.names_impl.ENU_ResolveToFunction;
import tripleo.elijah_elevated_durable.names_impl.EN_DeduceUsage;
import tripleo.elijah_elevateder.lang.types.OS_UnitType;
import tripleo.elijah_elevateder.stages.deduce.nextgen.DR_Ident;
import tripleo.elijah_elevateder.stages.deduce.post_bytecode.*;
import tripleo.elijah_elevateder.stages.deduce.tastic.*;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.*;

import java.util.List;
import java.util.function.Consumer;

class _DT_Function implements DT_Function {

	private final DeduceTypes2    deduceTypes2;
	private final BaseEvaFunction carrier;

	public _DT_Function(final DeduceTypes2 aDeduceTypes2, BaseEvaFunction aGeneratedFunction) {
		deduceTypes2 = aDeduceTypes2;
		carrier      = aGeneratedFunction;
	}

	@Override
	public List<VariableTableEntry> vte_list() {
		return carrier.vte_list;
	}

	@Override
	public List<IdentTableEntry> idte_list() {
		return carrier.idte_list;
	}

	@Override
	public List<ProcTableEntry> prte_list() {
		return carrier.prte_list;
	}

	@Override
	public BaseEvaFunction get() {
		return carrier;
	}

	@Override
	public List<ConstantTableEntry> cte_list() {
		return carrier.cte_list;
	}

	@Override
	public void add_proc_table_listeners() {
		final @NotNull BaseEvaFunction   generatedFunction = get();
		final __Add_Proc_Table_Listeners aptl              = deduceTypes2._inj().new___Add_Proc_Table_Listeners();

		for (final @NotNull ProcTableEntry pte : generatedFunction.prte_list) {
			aptl.add_proc_table_listeners(generatedFunction, pte, deduceTypes2);
		}
	}

	@Override
	public void resolve_ident_table(final Context aContext) {
		for (@NotNull IdentTableEntry ite : this.idte_list()) {
			ite.resolveExpectation = deduceTypes2.promiseExpectation(ite, "Element Resolved");
			ite.addResolver(deduceTypes2._inj().new_Unnamed_ITE_Resolver1(deduceTypes2, ite, this.get(), aContext));
		}

		for (@NotNull IdentTableEntry ite : this.idte_list()) {
			ite.resolvers_round();
		}
	}

	@Override
	public void resolve_arguments_table(final Context aContext) {
		final @NotNull Resolve_Variable_Table_Entry    rvte      = deduceTypes2._inj().new_Resolve_Variable_Table_Entry(carrier, aContext, deduceTypes2);
		final @NotNull DeduceTypes2.IVariableConnector connector = getVariableConnector();

		for (final @NotNull VariableTableEntry vte : vte_list()) {
			rvte.action(vte, connector);
		}
	}

	private @NotNull DeduceTypes2.IVariableConnector getVariableConnector() {
		final @NotNull DeduceTypes2.IVariableConnector connector;
		if (carrier instanceof EvaConstructor) {
			final IEvaConstructor evaConstructor = (IEvaConstructor) carrier;
			connector = deduceTypes2._inj().new_CtorConnector(evaConstructor, deduceTypes2);
		} else {
			connector = deduceTypes2._inj().new_NullConnector();
		}
		return connector;
	}

	@Override
	public void do_vte(Context aContext, DeduceTypes2 aDeduceTypes2) {
		for (VariableTableEntry variableTableEntry : vte_list()) {
			variableTableEntry.setDeduceTypes2(aDeduceTypes2, aContext, get());
		}
	}

	@Override
	public void do_idte(Context aContext, DeduceTypes2 aDeduceTypes2) {
		for (IdentTableEntry identTableEntry : idte_list()) {
			identTableEntry.setDeduceTypes2(aDeduceTypes2, aContext, get());
			// identTableEntry._fix_table(this, generatedFunction);
		}
	}

	@Override
	public void do_prte(Context aContext, DeduceTypes2 aDeduceTypes2) {
		for (ProcTableEntry procTableEntry : prte_list()) {
			procTableEntry.setDeduceTypes2(aDeduceTypes2, aContext, get(), aDeduceTypes2.errSink);
		}
	}

	@Override
	public void resolve_cte(Context aContext, DeduceTypes2 aDeduceTypes2) {
		//
		// resolve all cte expressions
		//
		for (final @NotNull ConstantTableEntry cte : cte_list()) {
			aDeduceTypes2.resolve_cte_expression(cte, aContext);
		}
	}

	@NotNull
	public DT_Function add_pte_listeners() {
		//
		// add proc table listeners
		//
		add_proc_table_listeners();
		return this;
	}

	public void resolve_args(Context aContext) {
		//
		// resolve arguments table
		//
		resolve_arguments_table(aContext);
	}

	@NotNull
	public DT_Function ressolve_ident_table(Context aContext) {
		//
		// resolve ident table
		//
		resolve_ident_table(aContext);
		return this;
	}

	@Override
	public void onEnterFunction(Context aContext, DeduceTypes2 aDeduceTypes2) {
		do_vte(aContext, aDeduceTypes2);
		do_idte(aContext, aDeduceTypes2);
		do_prte(aContext, aDeduceTypes2);
		resolve_cte(aContext, aDeduceTypes2);
		add_pte_listeners();
		ressolve_ident_table(aContext);
		resolve_args(aContext);
	}

	@Override
	public void onExitFunction2(@NotNull BaseEvaFunction generatedFunction, Context aFd_ctx, Context aContext, DeduceTypes2 aDeduceTypes2) {
		//
		// resolve var table. moved from `E'
		//
		for (@NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			vte.resolve_var_table_entry_for_exit_function();
		}
		for (@NotNull Runnable runnable : aDeduceTypes2.onRunnables) {
			runnable.run();
		}
//					LOG.info("167 "+generatedFunction);
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
		aDeduceTypes2.__checkVteList(generatedFunction);
		//
		// ATTACH A TYPE TO IDTE'S
		//
		for (@NotNull final IdentTableEntry ite : generatedFunction.idte_list) {
			final DeduceElement3_IdentTableEntry ite_de = ite.getDeduceElement3(aDeduceTypes2, generatedFunction);
			ite_de._ctxts(aFd_ctx, aContext);
			ite_de.mvState(null, DeduceElement3_IdentTableEntry.ST.EXIT_GET_TYPE);
		}
		{
			// TODO why are we doing this?
			final DeduceTypes2.Resolve_each_typename ret = aDeduceTypes2._inj().new_Resolve_each_typename(aDeduceTypes2.phase, aDeduceTypes2, aDeduceTypes2.errSink);
			for (final TypeTableEntry typeTableEntry : generatedFunction.tte_list) {
				ret.action(typeTableEntry);
			}
		}
		{
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

		//
		// RESOLVE FUNCTION RETURN TYPES
		//
		aDeduceTypes2.resolve_function_return_type(generatedFunction);

		aDeduceTypes2.__on_exit__post_vte_something(generatedFunction, aFd_ctx);

		//
		// LOOKUP FUNCTIONS
		//
		{
			@NotNull
			WorkList wl = aDeduceTypes2._inj().new_WorkList();

			for (@NotNull
			ProcTableEntry pte : generatedFunction.prte_list) {
				final DeduceElement3_ProcTableEntry de3_pte = aDeduceTypes2.convertPTE(generatedFunction, pte);
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

		aDeduceTypes2.checkEvaClassVarTable(generatedFunction);

		aDeduceTypes2.expectations.check();

		aDeduceTypes2.phase.addActives(aDeduceTypes2._actives);

		for (IdentTableEntry identTableEntry : generatedFunction.idte_list) {
			generatedFunction.drs.add(aDeduceTypes2._inj().new_DR_Ident(identTableEntry, generatedFunction, aDeduceTypes2));
		}
		for (VariableTableEntry variableTableEntry : generatedFunction.vte_list) {
			generatedFunction.drs.add(DR_Ident.create(variableTableEntry, generatedFunction));
		}

		aDeduceTypes2.phase.addDrs(generatedFunction, generatedFunction.drs);

		for (DT_External external : aDeduceTypes2.externals) {
			// external.onTargetModule(tm -> {phase.modulePromise(tm, external::actualise);}); //[T1160118]
			aDeduceTypes2.phase.modulePromise(external.targetModule(), external::actualise);
		}
	}

	@Override
	public void do_assign_normal(@NotNull BaseEvaFunction generatedFunction, @NotNull Context aFd_ctx,
	                             @NotNull Instruction instruction, @NotNull Context aContext, DeduceTypes2 aDeduceTypes2) {
		// TODO doesn't account for __assign__
		final InstructionArgument agn_lhs = instruction.getArg(0);
		if (agn_lhs instanceof IntegerIA lhs_integerIA) {
			final @NotNull VariableTableEntry vte = generatedFunction.getVarTableEntry(lhs_integerIA.getIndex());

			OS_Element name = null;
			{
				final OS_Element[] el1 = new OS_Element[1];

				switch (vte.getVtt()) {
				case ARG -> {
					vte.elementPromise(el0 -> el1[0] = el0, null);
					name = el1[0];
				}
				case VAR -> {
					vte.elementPromise(el0 -> el1[0] = el0, null);
					name = el1[0];
				}
				}
			}

			if (name != null) {
				int y = 2;
			}

			final InstructionArgument i2 = instruction.getArg(1);
			if (i2 instanceof IntegerIA) {
				final @NotNull VariableTableEntry vte2 = generatedFunction.getVarTableEntry(DeduceTypes2.to_int(i2));
				vte.addPotentialType(instruction.getIndex(), vte2.getTypeTableEntry());
			} else if (i2 instanceof final @NotNull FnCallArgs fca) {
				final @Nullable ITastic fcat = aDeduceTypes2.tasticFor(fca);
				assert fcat != null;
				fcat.do_assign_call(generatedFunction, aContext, vte, instruction, name);
			} else if (i2 instanceof ConstTableIA) {
				if (vte.getTypeTableEntry().getAttached() != null) {
					// TODO check types
				}
				final @NotNull ConstantTableEntry cte = generatedFunction
						.getConstTableEntry(((ConstTableIA) i2).getIndex());
				if (cte.type.getAttached() == null) {
					aDeduceTypes2.LOG.info("Null type in CTE " + cte);
				}
//		vte.type = cte.type;
				vte.addPotentialType(instruction.getIndex(), cte.type);
				DebugPrint.addPotentialType(vte, cte);
			} else if (i2 instanceof IdentIA identIA) {
				@NotNull
				IdentTableEntry idte = identIA.getEntry();

				var de3_ite = idte.getDeduceElement3();

				de3_ite.dan(generatedFunction, instruction, aContext, vte, identIA, idte, aDeduceTypes2);
			} else if (i2 instanceof ProcIA) {
				throw new NotImplementedException();
			} else
				throw new NotImplementedException();
		} else if (agn_lhs instanceof IdentIA arg) {
			final @NotNull IdentTableEntry agn_lhs_ite = arg.getEntry();
			final InstructionArgument      agn_rhs_ia  = instruction.getArg(1);
			if (agn_rhs_ia instanceof IntegerIA) {
				final @NotNull VariableTableEntry vte2 = generatedFunction.getVarTableEntry(DeduceTypes2.to_int(agn_rhs_ia));
				agn_lhs_ite.addPotentialType(instruction.getIndex(), vte2.getTypeTableEntry());
			} else if (agn_rhs_ia instanceof final @NotNull FnCallArgs fca) {
				aDeduceTypes2.tasticFor(agn_rhs_ia).do_assign_call(generatedFunction, aFd_ctx, agn_lhs_ite, null, instruction);
			} else if (agn_rhs_ia instanceof IdentIA identIA) {
				if (agn_lhs_ite.getResolvedElement() instanceof VariableStatement) {
					aDeduceTypes2.do_assign_normal_ident_deferred(generatedFunction, aFd_ctx, agn_lhs_ite);
				}
				@NotNull
				IdentTableEntry agn_rhs_ite = identIA.getEntry();
				aDeduceTypes2.do_assign_normal_ident_deferred(generatedFunction, aFd_ctx, agn_rhs_ite);
				agn_lhs_ite.addPotentialType(instruction.getIndex(), agn_rhs_ite.type);
			} else if (agn_rhs_ia instanceof ConstTableIA) {
				aDeduceTypes2.do_assign_constant(generatedFunction, instruction, agn_lhs_ite, (ConstTableIA) agn_rhs_ia);
			} else if (agn_rhs_ia instanceof ProcIA) {
				throw new NotImplementedException();
			} else
				throw new NotImplementedException();
		}
	}

	@Override
	public void do_agnk(@NotNull BaseEvaFunction generatedFunction, @NotNull Instruction instruction, DeduceTypes2 aDeduceTypes2) {
		final @NotNull IntegerIA          arg  = (IntegerIA) instruction.getArg(0);
		final @NotNull VariableTableEntry vte  = generatedFunction.getVarTableEntry(arg.getIndex());
		final InstructionArgument         i2   = instruction.getArg(1);
		final @NotNull ConstTableIA       ctia = (ConstTableIA) i2;

		{
			if (vte.getTypeTableEntry().getAttached() != null) {
				// TODO check types
			}
			final @NotNull ConstantTableEntry cte = generatedFunction.getConstTableEntry(ctia.getIndex());
			if (cte.type.getAttached() == null) {
				aDeduceTypes2.LOG.info("Null type in CTE " + cte);
			}
//		vte.type = cte.type;
			vte.addPotentialType(instruction.getIndex(), cte.type);
			DebugPrint.addPotentialType(vte, cte);
		}
	}

	@Override
	public void do_call(@NotNull BaseEvaFunction generatedFunction, @NotNull FunctionDef fd,
	                    @NotNull Instruction instruction, @NotNull Context context, DeduceTypes2 aDeduceTypes2) {
		final int                     pte_num = ((ProcIA) instruction.getArg(0)).index();
		final @NotNull ProcTableEntry pte     = generatedFunction.getProcTableEntry(pte_num);
//				final InstructionArgument i2 = (instruction.getArg(1));
		{
			final @NotNull IdentIA identIA = (IdentIA) pte.expression_num;

			final IdentTableEntry identTableEntry = identIA.getEntry();
			var                   idnt            = generatedFunction.getIdent(identTableEntry);

			{
				ENU_ResolveToFunction rtf = null;
				var                   x   = identTableEntry.getIdent().getName();

				for (EN_Understanding understanding : x.getUnderstandings()) {
					if (understanding instanceof ENU_ResolveToFunction rtf1) {
						rtf = rtf1;
					}
				}
				for (EN_Usage usage : x.getUsages()) {
					if (usage instanceof EN_DeduceUsage edu) {
						final ProcTableEntry callable_pte = ((IdentTableEntry) edu.getBte())._callable_pte();
						var                  e            = callable_pte.__debug_expression;
						if (e instanceof DotExpression de) {
							var r = de.getRight();
							if (r instanceof IdentExpression ie) {
								ie.getName().addUnderstanding(aDeduceTypes2._inj().new_ENU_FunctionInvocation(callable_pte));
							}
						}
					}
				}
			}

			var          xx = generatedFunction._getIdentIAResolvable(identIA);
			final String x  = xx.getNormalPath(generatedFunction, identIA);
			aDeduceTypes2.LOG.info("298 Calling " + x);
			aDeduceTypes2.resolveIdentIA_(context, identIA, generatedFunction, new FoundElement(aDeduceTypes2.phase) {

				@SuppressWarnings("unused")
				final String xx = x;

				@Override
				public void foundElement(OS_Element e) {
					aDeduceTypes2.found_element_for_ite(generatedFunction, identIA.getEntry(), e, context, aDeduceTypes2.central());
//							identIA.getEntry().setCallablePTE(pte); // TODO ??

					pte.setStatus(BaseTableEntry.Status.KNOWN, aDeduceTypes2._inj().new_ConstructableElementHolder(e, identIA));
					if (fd instanceof DefFunctionDef) {
						final IInvocation invocation = aDeduceTypes2.getInvocation((EvaFunction) generatedFunction);
						aDeduceTypes2.forFunction(aDeduceTypes2.newFunctionInvocation((FunctionDef) e, pte, invocation, aDeduceTypes2.phase), new ForFunction() {
							@Override
							public void typeDecided(@NotNull GenType aType) {
								@Nullable
								InstructionArgument x = generatedFunction.vte_lookup("Result");
								assert x != null;
								((IntegerIA) x).getEntry().getTypeTableEntry().setAttached(aDeduceTypes2.gt(aType));
							}
						});
					}
				}

				@Override
				public void noFoundElement() {
					aDeduceTypes2.errSink.reportError("370 Can't find callsite " + x);
					// TODO don't know if this is right
					@NotNull
					IdentTableEntry entry = identIA.getEntry();
					if (entry.getStatus() != BaseTableEntry.Status.UNKNOWN)
						entry.setStatus(BaseTableEntry.Status.UNKNOWN, null);
				}
			});
		}
	}

	@Override
	public void do_calls(@NotNull BaseEvaFunction generatedFunction, @NotNull Context fd_ctx,
	                     @NotNull Instruction instruction, DeduceTypes2 aDeduceTypes2) {
		final int                     i1  = DeduceTypes2.to_int(instruction.getArg(0));
		final InstructionArgument     i2  = (instruction.getArg(1));
		final @NotNull ProcTableEntry fn1 = generatedFunction.getProcTableEntry(i1);
		{
			final int pc = instruction.getIndex();
			if (generatedFunction.deferred_calls.contains(pc)) {
				aDeduceTypes2.LOG.err("Call is deferred "/* +gf.getInstruction(pc) */ + " " + fn1);
			} else {
				DeduceTypes2.Implement_Calls_ ic = aDeduceTypes2._inj().new_Implement_Calls_(generatedFunction, fd_ctx, i2, fn1, pc, aDeduceTypes2);
				ic.action();
			}
		}
		/*
		 * if (i2 instanceof IntegerIA) { int i2i = to_int(i2); VariableTableEntry vte =
		 * generatedFunction.getVarTableEntry(i2i); int y =2; } else throw new
		 * NotImplementedException();
		 */
	}

	@Override
	public void implement_is_a(@NotNull BaseEvaFunction gf, @NotNull Instruction instruction, DeduceTypes2 aDeduceTypes2) {
		final IntegerIA testing_var_  = (IntegerIA) instruction.getArg(0);
		final IntegerIA testing_type_ = (IntegerIA) instruction.getArg(1);
		final Label     target_label  = ((LabelIA) instruction.getArg(2)).label;

		final VariableTableEntry testing_var    = gf.getVarTableEntry(testing_var_.getIndex());
		final TypeTableEntry     testing_type__ = gf.getTypeTableEntry(testing_type_.getIndex());

		GenType genType = testing_type__.genType;
		if (genType.getResolved() == null) {
			try {
				genType.setResolved(aDeduceTypes2.resolve_type(genType.getTypeName(), gf.getFD().getContext()).getResolved());
			} catch (ResolveError aResolveError) {
//				aResolveError.printStackTrace();
				aDeduceTypes2.errSink.reportDiagnostic(aResolveError);
				return;
			}
		}
		if (genType.getCi() == null) {
			genType.genCI(genType.getNonGenericTypeName(), aDeduceTypes2, aDeduceTypes2.errSink, aDeduceTypes2.phase);
		}
		if (genType.getNode() == null) {
			if (genType.getCi() instanceof ClassInvocation) {
				WlGenerateClass gen = aDeduceTypes2._inj().new_WlGenerateClass(
						aDeduceTypes2.getGenerateFunctions(aDeduceTypes2.module),
						(ClassInvocation) genType.getCi(),
						aDeduceTypes2.phase.generatedClasses,
						aDeduceTypes2.phase.getCodeRegistrar()
				                                                              );

				gen.setConsumer(genType::setNode);

				gen.run(null);
			} else if (genType.getCi() instanceof NamespaceInvocation) {
				WlGenerateNamespace gen = aDeduceTypes2._inj().new_WlGenerateNamespace(aDeduceTypes2.getGenerateFunctions(aDeduceTypes2.module),
				                                                                       (NamespaceInvocation) genType.getCi(), aDeduceTypes2.phase.generatedClasses, aDeduceTypes2.phase.getCodeRegistrar());
				gen.run(null);
				genType.setNode(gen.getResult());
			}
		}
		// EvaNode testing_type = testing_type__.resolved();
		// assert testing_type != null;
		assert testing_type__.isResolved();
	}

	@Override
	public void implement_construct(BaseEvaFunction generatedFunction, Instruction instruction, Context aContext, DeduceTypes2 aDeduceTypes2) {
		final @NotNull Implement_construct ic = aDeduceTypes2._inj().new_Implement_construct(aDeduceTypes2, generatedFunction, instruction);
		try {
			ic.action(aContext);
		} catch (FCA_Stop e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public DT_Function prelude(@NotNull BaseEvaFunction generatedFunction, @NotNull FunctionDef fd, DeduceTypes2 aDeduceTypes2) {
		ProcTableEntry        pte        = generatedFunction.fi.pte;
		final @NotNull String pte_string = aDeduceTypes2.getPTEString(pte);
		aDeduceTypes2.LOG.info("** deduce_generated_function " + fd.name() + " " + pte_string);// +"
		// "+((OS_Container)((FunctionDef)fd).getParent()).name());
		return this;
	}

	@Override
	public void fix_tables(@NotNull BaseEvaFunction evaFunction, DeduceTypes2 aDeduceTypes2) {
		for (VariableTableEntry variableTableEntry : evaFunction.vte_list) {
			variableTableEntry._fix_table(aDeduceTypes2, evaFunction);
		}
		for (IdentTableEntry identTableEntry : evaFunction.idte_list) {
			identTableEntry._fix_table(aDeduceTypes2, evaFunction);
		}
		for (TypeTableEntry typeTableEntry : evaFunction.tte_list) {
			typeTableEntry._fix_table(aDeduceTypes2, evaFunction);
		}
		for (ProcTableEntry procTableEntry : evaFunction.prte_list) {
			procTableEntry._fix_table(aDeduceTypes2, evaFunction);
		}
	}

	@Override
	public void __post_vte_list_001(@NotNull BaseEvaFunction generatedFunction) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			vte.typeResolvePromise().then(gt -> {
				var xx = vte.resolvedType();

				if (xx instanceof EvaClass evaClass) {
					if (gt.getCi() == null) {
						gt.setCi(evaClass.ci);
					}

					gt.setResolved(evaClass.getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					vte.getTypeTableEntry().setAttached(gt);
				} else if (xx instanceof EvaConstructor evaConstructor) {
					if (gt.getCi() == null) {
//						gt.ci = evaConstructor.ci;
					}

					gt.setResolved(evaConstructor.fi.getClassInvocation().getKlass().getOS_Type());
					gt.setTypeName(gt.getResolved());

					vte.getTypeTableEntry().setAttached(gt);
				}
			});
		}
	}

	@Override
	public void __post_vte_list_002(@NotNull BaseEvaFunction generatedFunction, Context fd_ctx, DeduceTypes2 aDeduceTypes2) {
		for (final @NotNull VariableTableEntry vte : generatedFunction.vte_list) {
			if (vte.getTypeTableEntry().getAttached() == null) {
				int potential_size = vte.potentialTypes().size();
				if (potential_size == 1)
					vte.getTypeTableEntry().setAttached(aDeduceTypes2.getPotentialTypesVte(vte).get(0).getAttached());
				else if (potential_size > 1) {
					// TODO Check type compatibility
					aDeduceTypes2.LOG.err("703 " + vte.getName() + " " + vte.potentialTypes());
					aDeduceTypes2.errSink.reportDiagnostic(aDeduceTypes2._inj().new_CantDecideType(vte, vte.potentialTypes()));
				} else {
					// potential_size == 0
					// Result is handled by phase.typeDecideds, self is always valid
					if (/* vte.getName() != null && */ !(vte.getVtt() == VariableTableType.RESULT
							|| vte.getVtt() == VariableTableType.SELF))
						aDeduceTypes2.errSink.reportDiagnostic(aDeduceTypes2._inj().new_CantDecideType(vte, vte.potentialTypes()));
				}
			} else if (vte.getVtt() == VariableTableType.RESULT) {

				int           state    = 0;
				final OS_Type attached = vte.getTypeTableEntry().getAttached();
				if (attached.getType() == OS_Type.Type.USER) {
					try {
						// FIXME 07/03 HACK

						if (attached.getTypeName() instanceof RegularTypeName rtn) {
							if (rtn.getName().equals("Unit")) {
								state = 1;
							}
						} else if (attached instanceof OS_UnitType) {
							state = 1;
						}

						if (state == 0)
							vte.getTypeTableEntry().setAttached(aDeduceTypes2.resolve_type(attached, fd_ctx));
					} catch (ResolveError aResolveError) {
						aResolveError.printStackTrace();
						assert false;
					}
				}
			}
		}
	}

	@Override
	public void __post_deferred_calls(@NotNull BaseEvaFunction generatedFunction,
	                                  @NotNull Context fd_ctx, DeduceTypes2 aDeduceTypes2) {
		//
		// NOW CALCULATE DEFERRED CALLS
		//
		for (final Integer deferred_call : generatedFunction.deferred_calls) {
			final Instruction instruction = generatedFunction.getInstruction(deferred_call);

			final int                     i1  = DeduceTypes2.to_int(instruction.getArg(0));
			final InstructionArgument     i2  = (instruction.getArg(1));
			final @NotNull ProcTableEntry fn1 = generatedFunction.getProcTableEntry(i1);
			{
//				generatedFunction.deferred_calls.remove(deferred_call);
				DeduceTypes2.Implement_Calls_ ic = aDeduceTypes2._inj().new_Implement_Calls_(generatedFunction, fd_ctx, i2, fn1,
				                                                                             instruction.getIndex(), aDeduceTypes2);
				ic.action();
			}
		}
	}

	@Override
	public void deduce_generated_function_base(@NotNull BaseEvaFunction generatedFunction, @NotNull FunctionDef fd, DeduceTypes2 aDeduceTypes2) {
		final Context fd_ctx = fd.getContext();

		fix_tables(generatedFunction, aDeduceTypes2);
		prelude(generatedFunction, fd, aDeduceTypes2);

		for (final @NotNull Instruction instruction : generatedFunction.instructions()) {
			final Context context = generatedFunction.getContextFromPC(instruction.getIndex());
//			LOG.info("8006 " + instruction);
			switch (instruction.getName()) {
			case E:
				onEnterFunction(context, aDeduceTypes2);
				break;
			case X:
				onExitFunction2(generatedFunction, fd_ctx, context, aDeduceTypes2);
				break;
			case ES:
				break;
			case XS:
				break;
			case AGN:
				do_assign_normal(generatedFunction, fd_ctx, instruction, context, aDeduceTypes2);
				break;
			case AGNK:
				do_agnk(generatedFunction, instruction, aDeduceTypes2);
				break;
			case AGNT:
				break;
			case AGNF:
				aDeduceTypes2.LOG.info("292 Encountered AGNF");
				break;
			case JE:
				aDeduceTypes2.LOG.info("296 Encountered JE");
				break;
			case JNE:
				break;
			case JL:
				break;
			case JMP:
				break;
			case CALL:
				do_call(generatedFunction, fd, instruction, context, aDeduceTypes2);
				break;
			case CALLS:
				do_calls(generatedFunction, fd_ctx, instruction, aDeduceTypes2);
				break;
			case RET:
				break;
			case YIELD:
				break;
			case TRY:
				break;
			case PC:
				break;
			case CAST_TO:
				// README potentialType info is already added by MatchConditional
				break;
			case DECL:
				// README for GenerateC, etc: marks the spot where a declaration should go.
				// Wouldn't be necessary if we had proper Range's
				break;
			case IS_A:
				implement_is_a(generatedFunction, instruction, aDeduceTypes2);
				break;
			case NOP:
				break;
			case CONSTRUCT:
				implement_construct(generatedFunction, instruction, context, aDeduceTypes2);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + instruction.getName());
			}
		}
		__post_vte_list_001(generatedFunction);
		__post_vte_list_002(generatedFunction, fd_ctx, aDeduceTypes2);
		__post_deferred_calls(generatedFunction, fd_ctx, aDeduceTypes2);
	}
}
