/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.deduce;

import com.google.common.collect.*;
import lombok.*;
import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.stateful.*;
import tripleo.elijah.work.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevateder.lang.types.OS_UnknownType;
import tripleo.elijah_elevateder.nextgen.ClassDefinition__;
import tripleo.elijah_elevateder.nextgen.diagnostic.CouldntGenerateClass;
import tripleo.elijah_elevateder.stages.deduce.declarations.DeferredMember;
import tripleo.elijah_elevateder.stages.deduce.declarations.DeferredMemberFunction;
import tripleo.elijah_elevateder.stages.deduce.nextgen.*;
import tripleo.elijah_elevateder.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.ICodeRegistrar;
import tripleo.elijah_elevateder.stages.logging.ElLog_;
import tripleo.elijah_elevateder.stages.post_deduce.DefaultCodeRegistrar;
import tripleo.elijah_elevateder.work.EDL_WorkList;
import tripleo.elijah_elevateder.work.EDL_WorkManager;
import tripleo.elijah_elevateder.world.i.WorldModule;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import static tripleo.elijah_fluffy.util.Helpers.*;

/**
 * Created 12/24/20 3:59 AM
 */
public class DeducePhase extends _RegistrationTarget implements ReactiveDimension, EventualRegister {
	public final @NotNull  GeneratedClasses                             generatedClasses;
	public final @NotNull  GeneratePhase                                generatePhase;
	final                  Multimap<OS_Module, Consumer<DeduceTypes2>>  iWantModules            = ArrayListMultimap.create();
	private final          String                                       PHASE                   = "DeducePhase";
	private @NotNull
	final                  DeducePhaseInjector                          __inj                   = new DeducePhaseInjector();
	@NotNull
	public final           List<IFunctionMapHook> functionMapHooks        = _inj().new_ArrayList__IFunctionMapHook();
	@Getter
	private final @NotNull ICodeRegistrar         codeRegistrar;
	private final @NotNull ICompilationAccess     ca;
	private final          Map<NamespaceStatement, NamespaceInvocation> namespaceInvocationMap  = _inj()
			.new_HashMap__NamespaceInvocationMap();
	private final          ExecutorService                              classGenerator          = Executors.newCachedThreadPool();
	private final          Country1                                  country                 = _inj().new_Country1(this);
	private final          List<DeferredMemberFunction>              deferredMemberFunctions = _inj()
			.new_ArrayList__DeferredaMemberFunction();
	private final List<FoundElement>                     foundElements       = _inj().new_ArrayList__FoundElement();
	private final Multimap<FunctionDef, BaseEvaFunction> functionMap         = ArrayListMultimap.create();
	private final Map<IdentTableEntry, OnType>           idte_type_callbacks = _inj().new_HashMap__IdentTableEntry();
	private final @NotNull ElLog            LOG;
	private final @NotNull PipelineLogic    pipelineLogic;
	private final          List<DE3_Active> _actives                = _inj().new_ArrayList__DE3_Active();
	private final @NotNull Multimap<ClassStatement, ClassInvocation> classInvocationMultimap = ArrayListMultimap
			.create();
	private final @NotNull List<DeferredMember>                      deferredMembers         = _inj().new_ArrayList__DeferredMember();
	private final @NotNull Multimap<ClassStatement, OnClass>         onclasses               = ArrayListMultimap.create();
	private final @NotNull Multimap<OS_Element, ResolvedVariables>      resolved_variables      = ArrayListMultimap.create();
	private final @NotNull DRS                                          drs                     = _inj().new_DRS();
	private final @NotNull WAITS            waits                   = _inj().new_WAITS();
	public                 IPipelineAccess  pa;
	private                EventualRegister der = new DefaultEventualRegister();

	public DeducePhase(final @NotNull CompilationEnclosure ace) {
		this(ace.getCompilationAccess(), ace.getPipelineAccess(), ace.getPipelineLogic());
	}

	public DeducePhase(final @NotNull ICompilationAccess aca, final @NotNull IPipelineAccess pa0,
	                   final @NotNull PipelineLogic aPipelineLogic) {
		// given
		pipelineLogic = aPipelineLogic;
		ca            = aca;
		pa            = pa0;

		// transitive
		generatePhase = pipelineLogic.generatePhase;

		// created
		codeRegistrar = _inj().new_DefaultCodeRegistrar((Compilation) ca.getCompilation());
		LOG           = _inj().new_ElLog("(DEDUCE_PHASE)", pipelineLogic.getVerbosity(), PHASE);

		// using
		pa.getCompilationEnclosure().getPipelineLogic().addLog(LOG);
		pa.getCompilationEnclosure().addReactiveDimension(this);

		// create more
		generatedClasses = _inj().new_GeneratedClasses(this);

		//
		DeduceElement3_IdentTableEntry.ST.register(this);
	}

	public Compilation _compilation() {
		return (Compilation) ca.getCompilation();
	}

	public Multimap<FunctionDef, BaseEvaFunction> _functionMap() {
		return functionMap;
	}

	public @NotNull DeducePhaseInjector _inj() {
		return this.__inj;
	}

	public void addActives(@NotNull List<DE3_Active> activesList) {
		_actives.addAll(activesList);
	}

	public void addDeferredMember(final @NotNull DeferredMember aDeferredMember) {
		deferredMembers.add(aDeferredMember);
	}

	public void addDeferredMember(final DeferredMemberFunction aDeferredMemberFunction) {
		deferredMemberFunctions.add(aDeferredMemberFunction);
	}

	private void addDr(final Pair<BaseEvaFunction, DR_Item> drp) {
		drs.add(drp);
	}

	public void addDrs(final BaseEvaFunction aGeneratedFunction, final @NotNull List<DR_Item> aDrs) {
		for (DR_Item dr : aDrs) {
			addDr(Pair.of(aGeneratedFunction, dr));
		}
	}

	public void addFunction(@NotNull BaseEvaFunction generatedFunction, FunctionDef fd) {
		functionMap.put(fd, generatedFunction);
	}

	public void addFunctionMapHook(final IFunctionMapHook aFunctionMapHook) {
		functionMapHooks.add(aFunctionMapHook);
	}

	public void addLog(ElLog aLog) {
		// deduceLogs.add(aLog);
		pipelineLogic.addLog(aLog);
	}

	public @NotNull ICompilationAccess ca() {
		return ca;
	}

	public @NotNull Country country() {
		return country;
	}

	public @NotNull DeduceTypes2 deduceModule(final @NotNull WorldModule aMod) {
		return deduceModule(aMod, this.generatedClasses, EDL_Compilation.gitlabCIVerbosity());
	}

	public @NotNull DeduceTypes2 deduceModule(@NotNull WorldModule wm, @NotNull Iterable<EvaNode> lgf,
	                                          ElLog_.Verbosity verbosity) {
		var mod = wm.module();

		final @NotNull DeduceTypes2 deduceTypes2 = _inj().new_DeduceTypes2(mod, this, verbosity);
//		LOG.err("196 DeduceTypes "+deduceTypes2.getFileName());
		{
			final List<EvaNode> p = _inj().new_ArrayList__EvaNode();
			Iterables.addAll(p, lgf);
			LOG.info("197 lgf.size " + p.size());
		}
		deduceTypes2.deduceFunctions(lgf);
//		deduceTypes2.deduceClasses(generatedClasses.copy().stream()
//				.filter(c -> c.module() == m)
//				.collect(Collectors.toList()));

		for (EvaNode evaNode : generatedClasses.copy()) {
			if (evaNode.module() != mod)
				continue;

			if (evaNode instanceof final @NotNull EvaClass evaClass) {
				evaClass.fixupUserClasses(deduceTypes2, evaClass.getKlass().getContext());
				deduceTypes2.deduceOneClass(evaClass);
			}
		}

		for (EvaNode evaNode : lgf) {
			final BaseEvaFunction bef;

			if (evaNode instanceof BaseEvaFunction) {
				bef = (BaseEvaFunction) evaNode;
			} else
				continue;
			for (final IFunctionMapHook hook : functionMapHooks) {
				if (hook.matches(bef.getFD())) {
					hook.apply(List_of((EvaFunction) bef));
				}
			}
		}

		return deduceTypes2;
	}

	public void doneWait(final DeduceTypes2 aDeduceTypes2, final BaseEvaFunction aGeneratedFunction) {
		NotImplementedException.raise();
	}

	public boolean equivalentGenericPart(@NotNull ClassInvocation first, @NotNull ClassInvocation second) {
		final ClassInvocation.CI_GenericPart secondGenericPart1 = second.genericPart();
		final ClassInvocation.CI_GenericPart firstGenericPart1  = first.genericPart();

		if (second.getKlass() == first.getKlass() /* && secondGenericPart1 == null */)
			return true;

		final Map<TypeName, OS_Type> secondGenericPart = secondGenericPart1.getMap();
		final Map<TypeName, OS_Type> firstGenericPart  = firstGenericPart1.getMap();

		int i = secondGenericPart.entrySet().size();
		for (Map.@NotNull Entry<TypeName, OS_Type> entry : secondGenericPart.entrySet()) {
			final OS_Type entry_type = firstGenericPart.get(entry.getKey());
			// assert !(entry_type instanceof OS_UnknownType);

			if (entry_type instanceof OS_UnknownType)
				continue;

			if (entry_type.equals(entry.getValue()))
				i--;
//				else
//					return aClassInvocation;
		}
		return i == 0;
	}

	public void finish() {
		setGeneratedClassParents();
		/*
		 * for (GeneratedNode generatedNode : generatedClasses) { if (generatedNode
		 * instanceof EvaClass) { final EvaClass generatedClass = (EvaClass)
		 * generatedNode; final ClassStatement cs = generatedClass.getKlass();
		 * Collection<ClassInvocation> cis = classInvocationMultimap.get(cs); for
		 * (ClassInvocation ci : cis) { if (equivalentGenericPart(generatedClass.ci,
		 * ci)) { final DeferredObject<EvaClass, Void, Void> deferredObject =
		 * (DeferredObject<EvaClass, Void, Void>) ci.promise(); deferredObject.then(new
		 * DoneCallback<EvaClass>() {
		 *
		 * @Override public void onDone(EvaClass result) { assert result ==
		 * generatedClass; } }); // deferredObject.resolve(generatedClass); } } } }
		 */
		handleOnClassCallbacks();
		handleIdteTypeCallbacks();
		/*
		 * for (Map.Entry<EvaFunction, OS_Type> entry : typeDecideds.entrySet()) { for
		 * (Triplet triplet : forFunctions) { if (triplet.gf.getGenerated() ==
		 * entry.getKey()) { synchronized (triplet.deduceTypes2) {
		 * triplet.forFunction.typeDecided(entry.getValue()); } } } }
		 */
		/*
		 * for (Map.Entry<FunctionDef, EvaFunction> entry : functionMap.entries()) {
		 * FunctionInvocation fi = _inj().new_FunctionInvocation(entry.getKey(), null);
		 * for (Triplet triplet : forFunctions) { // Collection<EvaFunction> x =
		 * functionMap.get(fi); triplet.forFunction.finish(); } }
		 */
		handleFoundElements();
		handleResolvedVariables();
		resolveAllVariableTableEntries();
		handleDeferredMemberFunctions();
		handleDeferredMembers();
		sanityChecks();
		handleFunctionMapHooks();

		for (DE3_Active de3_Active : _actives) {
			pa.getCompilationEnclosure().addReactive(de3_Active);
		}

		for (Pair<BaseEvaFunction, DR_Item> pair : drs.iterator()) {
			final BaseEvaFunction ef = pair.getLeft();
			final DR_Item         dr = pair.getRight();

			// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("611a " + ef);
			// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("611b " + dr);

			if (dr instanceof DR_ProcCall drpc) {
				var fi = drpc.getFunctionInvocation();
				if (fi != null) {
					final BaseEvaFunction[] ef1 = new BaseEvaFunction[1];
					fi.generatePromise().then(x -> ef1[0] = x);

					if (ef1[0] == null) {
						// throw new AssertionError();
						SimplePrintLoggerToRemoveSoon.println_err_4("****************************** no function generated");
					} else {
						pa.activeFunction(ef1[0]);
					}
				}
			} else if (dr instanceof DR_Ident drid) {
				// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(String.format("***** 623623 -- %s %b", drid.name(),
				// drid.isResolved()));

				for (DR_Ident.Understanding understanding : drid.u) {
					// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(String.format("**** 623626 -- %s",
					// understanding.asString()));
				}
			}
		}

		for (DeduceTypes2 wait : waits.iterator()) {
			for (Map.Entry<OS_Module, Collection<Consumer<DeduceTypes2>>> entry : iWantModules.asMap().entrySet()) {
				if (entry.getKey() == wait.module) {
					for (Consumer<DeduceTypes2> deduceTypes2Consumer : entry.getValue()) {

						// README I like this, but by the time we get here, everything is already
						// done...
						// and I mean the callback to DT_External2::actualise
						// - everything being resolution and setStatus, etc

						deduceTypes2Consumer.accept(wait);
					}
				}
			}
		}

		waits.clear();

		checkFinishEventuals();
	}

	public void forFunction(@NotNull DeduceTypes2 deduceTypes2,
	                        @NotNull FunctionInvocation fi,
	                        @NotNull ForFunction forFunction) {
//		LOG.err("272 forFunction\n\t"+fi.getFunction()+"\n\t"+fi.pte);
		fi.generateDeferred().then(result -> result.typePromise().then(forFunction::typeDecided));
	}

	public @NotNull Eventual<ClassDefinition> generateClass2(final GenerateFunctions gf,
	                                                         final @NotNull ClassInvocation ci,
	                                                         final WorkManager wm) {
		final Eventual<ClassDefinition> ret = new Eventual<>();

		classGenerator.submit(() -> {
			WlGenerateClass gen = _inj().new_WlGenerateClass(gf, ci, generatedClasses, codeRegistrar);

			final EvaClass[] genclass1 = new EvaClass[1];

			gen.setConsumer(x -> {
				genclass1[0] = x;
			});

			gen.run(wm);

			final ClassDefinition cd = _inj().new_ClassDefinition(ci);

			if (genclass1[0] != null) {
				final EvaClass genclass = genclass1[0];

				cd.setNode(genclass);
				ret.resolve(cd);
			} else {
				ret.reject(_inj().new_CouldntGenerateClass(gen, DeducePhase.this));
			}
		});

		return ret;
	}

	public void handleDeferredMemberFunctions() {
		for (@NotNull final DeferredMemberFunction deferredMemberFunction : deferredMemberFunctions) {
			int              y      = 2;
			final OS_Element parent = deferredMemberFunction.getParent();// .getParent().getParent();

			if (parent instanceof ClassStatement) {
				final IInvocation invocation = deferredMemberFunction.getInvocation();

				final DeferredMemberFunctionParentIsClassStatement dmfpic = _inj()
						.new_DeferredMemberFunctionParentIsClassStatement(deferredMemberFunction, invocation, this);
				dmfpic.action();
			} else if (parent instanceof NamespaceStatement) {
//				final ClassStatement classStatement = (ClassStatement) deferredMemberFunction.getParent();
				final IInvocation invocation = deferredMemberFunction.getInvocation();

				final NamespaceInvocation namespaceInvocation;
				if (invocation instanceof ClassInvocation) {
					namespaceInvocation = _inj().new_NamespaceInvocation((NamespaceStatement) parent);
				} else {
					namespaceInvocation = (NamespaceInvocation) invocation;
				}

				namespaceInvocation.resolvePromise().then((final @NotNull EvaNamespace result) -> {
					final NamespaceInvocation             x  = namespaceInvocation;
					final @NotNull DeferredMemberFunction z  = deferredMemberFunction;
					int                                   yy = 2;
				});
			}
		}

		for (EvaNode evaNode : generatedClasses) {
			if (evaNode instanceof final @NotNull EvaContainerNC nc) {
				nc.noteDependencies(nc.getDependency()); // TODO is this right?

				for (EvaFunction generatedFunction : nc.functionMap.values()) {
					generatedFunction.noteDependencies(nc.getDependency());
				}
				if (nc instanceof final @NotNull EvaClass evaClass) {
					for (final IEvaConstructor evaConstructor : evaClass.constructors.values()) {
						evaConstructor.noteDependencies(nc.getDependency());
					}
				}
			}
		}
	}

	public void handleDeferredMembers() {
		for (@NotNull final DeferredMember deferredMember : deferredMembers) {
			if (deferredMember.getParent().isNamespaceStatement()) {
				final @NotNull NamespaceStatement parent = (NamespaceStatement) deferredMember.getParent().element();
				final NamespaceInvocation         nsi    = registerNamespaceInvocation(parent);
				nsi.resolveDeferred().done(result -> {
					@NotNull
					Maybe<EvaContainer.VarTableEntry> v_m = result
							.getVariable(deferredMember.getVariableStatement().getName());

					assert !v_m.isException();

					EvaContainer.VarTableEntry v = v_m.o;

					// TODO varType, potentialTypes and _resolved: which?
					// final OS_Type varType = v.varType;

					assert v != null;
					v.resolve_varType_cb((varType) -> {
						final @NotNull GenType genType = _inj().new_GenTypeImpl();
						genType.set(varType);

//								if (deferredMember.getInvocation() instanceof NamespaceInvocation) {
//									((NamespaceInvocation) deferredMember.getInvocation()).resolveDeferred().done(new DoneCallback<EvaNamespace>() {
//										@Override
//										public void onDone(EvaNamespace result) {
//											result;
//										}
//									});
//								}

						deferredMember.externalRefDeferred().resolve(result);
						/*
						 * if (genType.resolved == null) { // HACK need to resolve, but this shouldn't
						 * be here try {
						 *
						 * @NotNull OS_Type rt = DeduceTypes2.resolve_type(null, varType,
						 * varType.getTypeName().getContext()); genType.set(rt); } catch (ResolveError
						 * aResolveError) { aResolveError.printStackTrace(); } }
						 * deferredMember.typeResolved().resolve(genType);
						 */
					});
				});
			} else if (deferredMember.getParent().element() instanceof ClassStatement) {
				// TODO do something
				final ClassStatement parent = (ClassStatement) deferredMember.getParent().element();
				final String         name   = deferredMember.getVariableStatement().getName();

				// because deferredMember.invocation is null, we must create one here
				final @Nullable ClassInvocation ci = registerClassInvocation(parent, null, new NULL_DeduceTypes2());
				assert ci != null;
				ci.resolvePromise().then(result -> {
					final List<EvaContainer.VarTableEntry> vt = result.varTable;
					for (EvaContainer.VarTableEntry gc_vte : vt) {
						if (gc_vte.nameToken.getText().equals(name)) {
							// check connections
							// unify pot. types (prol. shuld be done already -- we don't want to be
							// reporting errors here)
							// call typePromises and externalRefPromisess

							// TODO just getting first element here (without processing of any kind); HACK
							final List<EvaContainer.VarTableEntry.ConnectionPair> connectionPairs = gc_vte.connectionPairs;
							if (!connectionPairs.isEmpty()) {
								final GenType ty = connectionPairs.get(0).vte.getTypeTableEntry().genType;
								assert ty.getResolved() != null;
								gc_vte.varType = ty.getResolved(); // TODO make sure this is right in all cases
								if (deferredMember.typeResolved().isPending())
									deferredMember.typeResolved().resolve(ty);
								break;
							} else {
								NotImplementedException.raise();
							}
						}
					}
				});
			} else
				throw new NotImplementedException();
		}
	}

	public void handleFoundElements() {
		for (@NotNull
		FoundElement foundElement : foundElements) {
			// TODO As we are using this, didntFind will never fail because
			// we call doFoundElement manually in resolveIdentIA
			// As the code matures, maybe this will change and the interface
			// will be improved, namely calling doFoundElement from here as well
			if (foundElement.didntFind()) {
				foundElement.doNoFoundElement();
			}
		}
	}

	public void handleFunctionMapHooks() {
		for (final Map.Entry<FunctionDef, Collection<BaseEvaFunction>> entry : functionMap.asMap().entrySet()) {
			for (final GFunctionMapHook functionMapHook1 : ca.functionMapHooks()) {
				IFunctionMapHook functionMapHook = (IFunctionMapHook) functionMapHook1;

				if (functionMapHook.matches(entry.getKey())) {
					functionMapHook.apply(entry.getValue());
				}
			}
		}
	}

	public void handleIdteTypeCallbacks() {
		for (Map.@NotNull Entry<IdentTableEntry, OnType> entry : idte_type_callbacks.entrySet()) {
			IdentTableEntry idte = entry.getKey();
			if (idte.type != null && // TODO make a stage where this gets set (resolvePotentialTypes)
			    idte.type.getAttached() != null) {
				entry.getValue().typeDeduced(idte.type.getAttached());
			} else {
				entry.getValue().noTypeFound();
			}
		}
	}

	public void handleOnClassCallbacks() {
		// TODO rewrite with classInvocationMultimap
		for (ClassStatement classStatement : onclasses.keySet()) {
			for (EvaNode evaNode : generatedClasses) {
				if (evaNode instanceof final @NotNull EvaClass evaClass) {
					if (evaClass.getKlass() == classStatement) {
						Collection<OnClass> ks = onclasses.get(classStatement);
						for (@NotNull
						OnClass k : ks) {
							k.classFound(evaClass);
						}
					} else {
						@NotNull
						Collection<EvaClass> cmv = evaClass.classMap.values();
						for (@NotNull
						EvaClass aClass : cmv) {
							if (aClass.getKlass() == classStatement) {
								Collection<OnClass> ks = onclasses.get(classStatement);
								for (@NotNull
								OnClass k : ks) {
									k.classFound(evaClass);
								}
							}
						}
					}
				}
			}
		}
	}

	public void handleResolvedVariables() {
		for (EvaNode evaNode : generatedClasses.copy()) {
			if (evaNode instanceof final @NotNull EvaContainer evaContainer) {
				Collection<ResolvedVariables> x = resolved_variables.get(evaContainer.getElement());
				for (@NotNull
				DeducePhase.ResolvedVariables resolvedVariables : x) {
					final @NotNull Maybe<EvaContainer.VarTableEntry> variable_m = evaContainer
							.getVariable(resolvedVariables.varName);

					assert !variable_m.isException();

					final @NotNull EvaContainer.VarTableEntry variable = variable_m.o;

					final TypeTableEntry type = resolvedVariables.identTableEntry.type;
					if (type != null)
						variable.addPotentialTypes(List_of(type));
					variable.addPotentialTypes(resolvedVariables.identTableEntry.potentialTypes());
					variable.updatePotentialTypes(evaContainer);
				}
			}
		}
	}

	public void modulePromise(final OS_Module aModule, final Consumer<DeduceTypes2> con) {
		iWantModules.put(aModule, con);
	}

	public @NotNull FunctionInvocation newFunctionInvocation(final FunctionDef f, final @Nullable ProcTableEntry aO,
	                                                         final @NotNull IInvocation ci) {
		return _inj().new_FunctionInvocation(f, aO, ci, this.generatePhase);
	}

	public void onClass(ClassStatement aClassStatement, OnClass callback) {
		onclasses.put(aClassStatement, callback);
	}

	public void onType(IdentTableEntry entry, OnType callback) {
		idte_type_callbacks.put(entry, callback);
	}

	public @NotNull ClassInvocation registerClassInvocation(@NotNull ClassInvocation aClassInvocation) {
		RegisterClassInvocation rci = _inj().new_RegisterClassInvocation(this);
		return rci.registerClassInvocation(aClassInvocation);
	}

	public @NotNull ClassInvocation registerClassInvocation(final @NotNull ClassStatement aParent, final DeduceTypes2 dtt) {
		Supplier<DeduceTypes2> dt2s;
		if (dtt == null) {
			dt2s= new NULL_DeduceTypes2();
		} else {
			dt2s=()->dtt;
		}

		return registerClassInvocation(_inj().new_ClassInvocation(aParent, null, dt2s));
	}

	// helper function. no generics!
	public @Nullable ClassInvocation registerClassInvocation(@NotNull ClassStatement aParent,
	                                                         final String aConstructorName,
	                                                         final Supplier<DeduceTypes2> aDeduceTypes2) {
		// @Nullable ClassInvocation classInvocation = _inj().new_ClassInvocation(aParent, aConstructorName, aDeduceTypes2);
		ClassInvocation ci = _inj().new_ClassInvocation(aParent, aConstructorName, aDeduceTypes2); // !! 08/28; 09/24 ??
		ci = registerClassInvocation(ci);
		return ci;
	}

	public ClassInvocation registerClassInvocation(final RegisterClassInvocation_env env) {
		var rci = env.deducePhaseSupplier().get().new RegisterClassInvocation();
		return rci.registerClassInvocation(env);
	}

	public void registerFound(FoundElement foundElement) {
		foundElements.add(foundElement);
	}

	public NamespaceInvocation registerNamespaceInvocation(NamespaceStatement aNamespaceStatement) {
		if (namespaceInvocationMap.containsKey(aNamespaceStatement))
			return namespaceInvocationMap.get(aNamespaceStatement);

		@NotNull
		NamespaceInvocation nsi = _inj().new_NamespaceInvocation(aNamespaceStatement);
		namespaceInvocationMap.put(aNamespaceStatement, nsi);
		return nsi;
	}

	public void registerResolvedVariable(IdentTableEntry identTableEntry, OS_Element parent, String varName) {
		resolved_variables.put(parent, _inj().new_ResolvedVariables(identTableEntry, parent, varName));
	}

	public void resolveAllVariableTableEntries() {
		@NotNull
		List<EvaClass> gcs = _inj().new_ArrayList__EvaClass();
		boolean all_resolve_var_table_entries = false;
		while (!all_resolve_var_table_entries) {
			if (generatedClasses.size() == 0)
				break;
			for (EvaNode evaNode : generatedClasses.copy()) {
				if (evaNode instanceof final @NotNull EvaClass evaClass) {
					all_resolve_var_table_entries = evaClass.resolve_var_table_entries(this); // TODO use a while loop
					// to get all classes
				}
			}
		}
	}

	private void sanityChecks() {
		for (EvaNode evaNode : generatedClasses) {
			if (evaNode instanceof final @NotNull EvaClass evaClass) {
				sanityChecks(evaClass.functionMap.values());
//				sanityChecks(generatedClass.constructors.values()); // TODO reenable
			} else if (evaNode instanceof final @NotNull EvaNamespace generatedNamespace) {
				sanityChecks(generatedNamespace.functionMap.values());
//				sanityChecks(generatedNamespace.constructors.values());
			}
		}
	}

	private void sanityChecks(@NotNull Collection<EvaFunction> aGeneratedFunctions) {
		for (@NotNull
		EvaFunction generatedFunction : aGeneratedFunctions) {
			for (@NotNull
			IdentTableEntry identTableEntry : generatedFunction.idte_list) {
				switch (identTableEntry.getStatus()) {
				case UNKNOWN:
					assert !identTableEntry.hasResolvedElement();
					LOG.err(String.format("250 UNKNOWN idte %s in %s", identTableEntry, generatedFunction));
					break;
				case KNOWN:
					assert identTableEntry.hasResolvedElement();
					if (identTableEntry.type == null) {
						LOG.err(String.format("258 null type in KNOWN idte %s in %s", identTableEntry,
						                      generatedFunction
						));
					}
					break;
				case UNCHECKED: {

					LOG.err(String.format("255 UNCHECKED idte %s in %s", identTableEntry, generatedFunction));
					break;
				}
				}
				for (@NotNull
				TypeTableEntry pot_tte : identTableEntry.potentialTypes()) {
					if (pot_tte.getAttached() == null) {
						LOG.err(String.format("267 null potential attached in %s in %s in %s", pot_tte, identTableEntry,
						                      generatedFunction
						));
					}
				}
			}
		}
	}

	public void setGeneratedClassParents() {
		// TODO all EvaFunction nodes have a genClass member
		for (EvaNode evaNode : generatedClasses) {
			if (evaNode instanceof final @NotNull EvaClass evaClass) {
				@NotNull
				Collection<EvaFunction> functions = evaClass.functionMap.values();
				for (@NotNull
				EvaFunction generatedFunction : functions) {
					generatedFunction.setParent(evaClass);
				}
			} else if (evaNode instanceof final @NotNull EvaNamespace generatedNamespace) {
				@NotNull
				Collection<EvaFunction> functions = generatedNamespace.functionMap.values();
				for (@NotNull
				EvaFunction generatedFunction : functions) {
					generatedFunction.setParent(generatedNamespace);
				}
			}
		}
	}

	public void waitOn(final DeduceTypes2 aDeduceTypes2) {
		waits.add(aDeduceTypes2);
	}

	public ClassInvocation registerClassInvocation(final ClassStatement aCs) {
		return registerClassInvocation(aCs, null);
	}

	@Override
	public void checkFinishEventuals() {
		der.checkFinishEventuals();
	}

	@Override
	public <P> void register(final Eventual<P> ev) {
		der.register(ev);
	}

	public interface Country {
		void sendClasses(Consumer<List<EvaNode>> ces);
	}

	static class DRS {
		private final List<Pair<BaseEvaFunction, DR_Item>> drs = new ArrayList<>();

		public void add(final Pair<BaseEvaFunction, DR_Item> aDrp) {
			drs.add(aDrp);
		}

		public Iterable<Pair<BaseEvaFunction, DR_Item>> iterator() {
			return drs;
		}
	}

	static class ResolvedVariables {
		final          IdentTableEntry identTableEntry;
		final @NotNull OS_Element      parent; // README tripleo.elijah.lang._CommonNC, but that's package-private
		final          String          varName;

		public ResolvedVariables(IdentTableEntry aIdentTableEntry, OS_Element aParent, String aVarName) {
			assert aParent instanceof ClassStatement || aParent instanceof NamespaceStatement;

			identTableEntry = aIdentTableEntry;
			parent          = aParent;
			varName         = aVarName;
		}
	}

	static class WAITS {
		private final Set<DeduceTypes2> waits = new HashSet<>();

		public void add(final DeduceTypes2 aDeduceTypes2) {
			waits.add(aDeduceTypes2);
		}

		public Iterable<DeduceTypes2> iterator() {
			return waits;
		}

		public void clear() {
			waits.clear();
		}
	}

	class Country1 implements Country {
		@Override
		public void sendClasses(final @NotNull Consumer<List<EvaNode>> ces) {
			ces.accept(generatedClasses.copy());
		}
	}

	public class DeducePhaseInjector {
		public List<DE3_Active> new_ArrayList__DE3_Active() {
			return new ArrayList<>();
		}

		public List<DeferredMemberFunction> new_ArrayList__DeferredaMemberFunction() {
			return new ArrayList<>();
		}

		public List<DeferredMember> new_ArrayList__DeferredMember() {
			return new ArrayList<>();
		}

		public List<EvaClass> new_ArrayList__EvaClass() {
			return new ArrayList<>();
		}

		public List<EvaNode> new_ArrayList__EvaNode() {
			return new ArrayList<>();
		}

		public List<EvaNode> new_ArrayList__EvaNode(final List<EvaNode> aGeneratedClasses) {
			return new ArrayList<>(aGeneratedClasses);
		}

		public List<FoundElement> new_ArrayList__FoundElement() {
			return new ArrayList<>();
		}

		public List<IFunctionMapHook> new_ArrayList__IFunctionMapHook() {
			return new ArrayList<>();
		}

		public List<State> new_ArrayList__State() {
			return new ArrayList<>();
		}

		public ClassDefinition new_ClassDefinition(final ClassInvocation aCi) {
			return new ClassDefinition__(aCi);
		}

		public @NotNull ClassInvocation new_ClassInvocation(final ClassStatement aParent, final String aConstructorName,
		                                                    final @NotNull Supplier<DeduceTypes2> aDeduceTypes2Supplier) {
			return new ClassInvocation(aParent, aConstructorName, aDeduceTypes2Supplier);
		}

		public Diagnostic new_CouldntGenerateClass(final ClassDefinition aCd, final GenerateFunctions aGf,
		                                           final ClassInvocation aCi) {
			return new CouldntGenerateClass(aCd, aGf, aCi);
		}

		public Diagnostic new_CouldntGenerateClass(WlGenerateClass gen, DeducePhase deducePhase) {
			return new CouldntGenerateClass(gen, deducePhase);
		}

		public Country1 new_Country1(final DeducePhase aDeducePhase) {
			return aDeducePhase.new Country1();
		}

		public DeduceTypes2 new_DeduceTypes2(final OS_Module aM, final DeducePhase aDeducePhase,
		                                     final ElLog_.Verbosity aVerbosity) {
			return new DeduceTypes2(aM, aDeducePhase, aVerbosity);
		}

		public ICodeRegistrar new_DefaultCodeRegistrar(final Compilation aCompilation) {
			return new DefaultCodeRegistrar(aCompilation);
		}

		public DeferredMemberFunctionParentIsClassStatement new_DeferredMemberFunctionParentIsClassStatement(
				final DeferredMemberFunction aDeferredMemberFunction, final IInvocation aInvocation,
				final DeducePhase aDeducePhase) {
			return aDeducePhase.new DeferredMemberFunctionParentIsClassStatement(aDeferredMemberFunction, aInvocation);
		}

		public DRS new_DRS() {
			return new DRS();
		}

		public ElLog new_ElLog(final String aS, final ElLog_.Verbosity aVerbosity, final String aDeducePhase) {
			return new ElLog_(aS, aVerbosity, aDeducePhase);
		}

		public FunctionInvocation new_FunctionInvocation(final FunctionDef aF,
		                                                 final ProcTableEntry aProcTableEntry,
		                                                 final IInvocation aCi,
		                                                 final GeneratePhase aGeneratePhase) {
			return new FunctionInvocation(aF, aProcTableEntry, aCi, aGeneratePhase);
		}

		public GeneratedClasses new_GeneratedClasses(final DeducePhase aDeducePhase) {
			return aDeducePhase.new GeneratedClasses();
		}

		public GenType new_GenTypeImpl() {
			return new GenTypeImpl();
		}

		public Function<EvaNode, Map<FunctionDef, EvaFunction>> new_GetFunctionMapClass() {
			return new DeferredMemberFunctionParentIsClassStatement.GetFunctionMapClass();
		}

		public Function<EvaNode, Map<FunctionDef, EvaFunction>> new_GetFunctionMapNamespace() {
			return new DeferredMemberFunctionParentIsClassStatement.GetFunctionMapNamespace();
		}

		public Map<IdentTableEntry, OnType> new_HashMap__IdentTableEntry() {
			return new HashMap<>();
		}

		public Map<NamespaceStatement, NamespaceInvocation> new_HashMap__NamespaceInvocationMap() {
			return new HashMap<NamespaceStatement, NamespaceInvocation>();
		}

		public NamespaceInvocation new_NamespaceInvocation(final NamespaceStatement aParent) {
			return new NamespaceInvocation(aParent);
		}

		public RegisterClassInvocation new_RegisterClassInvocation(final DeducePhase aDeducePhase) {
			return aDeducePhase.new RegisterClassInvocation();
		}

		public ResolvedVariables new_ResolvedVariables(final IdentTableEntry aIdentTableEntry, final OS_Element aParent,
		                                               final String aVarName) {
			return new ResolvedVariables(aIdentTableEntry, aParent, aVarName);
		}

		public WAITS new_WAITS() {
			return new WAITS();
		}

		public WlGenerateClass new_WlGenerateClass(final GenerateFunctions aGenerateFunctions,
		                                           final ClassInvocation aClassInvocation, final GeneratedClasses aGeneratedClasses,
		                                           final ICodeRegistrar aCodeRegistrar) {
			return new WlGenerateClass(aGenerateFunctions, aClassInvocation, aGeneratedClasses, aCodeRegistrar);
		}

		public WorkJob new_WlGenerateClass(final GenerateFunctions aGenerateFunctions,
		                                   final ClassInvocation aClassInvocation, final GeneratedClasses aGeneratedClasses,
		                                   final ICodeRegistrar aCodeRegistrar, final RegisterClassInvocation_env aEnv) {
			return new WlGenerateClass(aGenerateFunctions, aClassInvocation, aGeneratedClasses, aCodeRegistrar, aEnv);
		}

		public WorkList new_WorkList() {
			return new EDL_WorkList();
		}

		public WorkManager new_WorkManager() {
			return new EDL_WorkManager();
		}
	}

	/* static */ class DeferredMemberFunctionParentIsClassStatement {
		private final DeferredMemberFunction deferredMemberFunction;
		private final IInvocation            invocation;
		private final OS_Element             parent;

		public DeferredMemberFunctionParentIsClassStatement(final DeferredMemberFunction aDeferredMemberFunction,
		                                                    final IInvocation aInvocation) {
			deferredMemberFunction = aDeferredMemberFunction;
			invocation             = aInvocation;
			parent                 = deferredMemberFunction.getParent();// .getParent().getParent();
		}

		void action() {
			if (invocation instanceof ClassInvocation)
				((ClassInvocation) invocation).resolvePromise().then(new DoneCallback<EvaClass>() {
					@Override
					public void onDone(final EvaClass result) {
						defaultAction(result);
					}
				});
			else if (invocation instanceof NamespaceInvocation)
				((NamespaceInvocation) invocation).resolvePromise().then(new DoneCallback<EvaNamespace>() {
					@Override
					public void onDone(final EvaNamespace result) {
						defaultAction(result);
					}
				});
		}

		<T extends EvaNode> void defaultAction(final T result) {
			final OS_Element p = deferredMemberFunction.getParent();

			if (p instanceof final DeduceTypes2.@NotNull OS_SpecialVariable specialVariable) {
				onSpecialVariable(specialVariable);
				int y = 2;
			} else if (p instanceof ClassStatement) {
				final Function<EvaNode, Map<FunctionDef, EvaFunction>> x = getFunctionMap(result);

				// once again we need EvaFunction, not FunctionDef
				// we seem to have it below, but there can be multiple
				// specializations of each function

				final EvaFunction gf = x.apply(result).get(deferredMemberFunction.getFunctionDef());
				if (gf != null) {
					deferredMemberFunction.externalRefDeferred().resolve(gf);
					gf.typePromise().then(new DoneCallback<GenType>() {
						@Override
						public void onDone(final GenType result) {
							deferredMemberFunction.typeResolved().resolve(result);
						}
					});
				}
			} else
				throw new IllegalStateException("unknown parent");
		}

		@NotNull
		private <T extends EvaNode> Function<EvaNode, Map<FunctionDef, EvaFunction>> getFunctionMap(final T result) {
			final Function<EvaNode, Map<FunctionDef, EvaFunction>> x;
			if (result instanceof EvaNamespace)
				x = _inj().new_GetFunctionMapNamespace();
			else if (result instanceof EvaClass)
				x = _inj().new_GetFunctionMapClass();
			else
				throw new NotImplementedException();
			return x;
		}

		public void onSpecialVariable(final DeduceTypes2.@NotNull OS_SpecialVariable aSpecialVariable) {
			final DeduceLocalVariable.MemberInvocation mi = aSpecialVariable.memberInvocation;

			switch (mi.role) {
			case INHERITED:
				final FunctionInvocation functionInvocation = deferredMemberFunction.functionInvocation();
				functionInvocation.generatePromise().then(new DoneCallback<BaseEvaFunction>() {
					@Override
					public void onDone(final @NotNull BaseEvaFunction gf) {
						deferredMemberFunction.externalRefDeferred().resolve(gf);
						gf.typePromise().then(new DoneCallback<GenType>() {
							@Override
							public void onDone(final GenType result) {
								deferredMemberFunction.typeResolved().resolve(result);
							}
						});
					}
				});
				break;
			case DIRECT:
				if (invocation instanceof NamespaceInvocation)
					assert false;
				else {
					final ClassInvocation classInvocation = (ClassInvocation) invocation;
					classInvocation.resolvePromise().then(new DoneCallback<EvaClass>() {
						@Override
						public void onDone(final @NotNull EvaClass element_generated) {
							// once again we need EvaFunction, not FunctionDef
							// we seem to have it below, but there can be multiple
							// specializations of each function
							final EvaFunction gf = element_generated.functionMap
									.get(deferredMemberFunction.getFunctionDef());
							deferredMemberFunction.externalRefDeferred().resolve(gf);
							gf.typePromise().then(new DoneCallback<GenType>() {
								@Override
								public void onDone(final GenType result) {
									deferredMemberFunction.typeResolved().resolve(result);
								}
							});
						}
					});
				}
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + mi.role);
			}
		}

		static class GetFunctionMapClass implements Function<EvaNode, Map<FunctionDef, EvaFunction>> {
			@Override
			public Map<FunctionDef, EvaFunction> apply(final @NotNull EvaNode aClass) {
				return ((EvaClass) aClass).functionMap;
			}
		}

		static class GetFunctionMapNamespace implements Function<EvaNode, Map<FunctionDef, EvaFunction>> {
			@Override
			public Map<FunctionDef, EvaFunction> apply(final @NotNull EvaNode aNamespace) {
				return ((EvaNamespace) aNamespace).functionMap;
			}
		}
	}

	public class GeneratedClasses implements Iterable<EvaNode> {
		@NotNull
		List<EvaNode> generatedClasses = _inj().new_ArrayList__EvaNode();
		private int generation;

		public void add(EvaNode aClass) {
			pa._send_GeneratedClass(aClass);

			generatedClasses.add(aClass);
		}

		public void addAll(@NotNull List<EvaNode> lgc) {
			// TODO is this method really needed
			generatedClasses.addAll(lgc);
		}

		public @NotNull List<EvaNode> copy() {
			++generation;
			return new ArrayList<>(generatedClasses);
		}

		@Override
		public @NotNull Iterator<EvaNode> iterator() {
			return generatedClasses.iterator();
		}

		public int size() {
			return generatedClasses.size();
		}

		@Override
		public String toString() {
			return "GeneratedClasses{size=%d, generation=%d}".formatted(generatedClasses.size(), generation);
		}
	}

	class RegisterClassInvocation {
		// TODO this class is a mess

		public Eventual<ClassDefinition> getClassInvocationPromise(final @NotNull ClassInvocation aClassInvocation,
		                                                           @Nullable OS_Module mod,
		                                                           final @Nullable WorkList wl,
		                                                           final @NotNull RegisterClassInvocation_env aEnv) {
			if (mod == null) {
				mod = aClassInvocation.getKlass().getContext().module();
			}

			final @NotNull OS_Module finalMod = mod;

			var req2 = new RegisterClassInvocation2_env(aEnv, generatePhase.getWm(), wl, () -> generatePhase.getGenerateFunctions(finalMod));

			//noinspection UnnecessaryLocalVariable
			var prom = generateClass(req2);
			return prom;
		}

		private Eventual<ClassDefinition> generateClass(RegisterClassInvocation2_env aReq2) {
			// par { return promise ; wm.drain() ; }

			final GenerateFunctions gf = aReq2.getGenerateFunctions().get();
			final ClassInvocation   ci = aReq2.env1().classInvocation();
			final WorkManager       wm = aReq2.workManager();

			final Eventual<ClassDefinition> x = generateClass2(gf, ci, wm);

			wm.drain();
			return x;
		}

		private @NotNull ClassInvocation getClassInvocation(final @NotNull ClassInvocation aClassInvocation,
		                                                    OS_Module mod, final WorkList wl, final @NotNull RegisterClassInvocation_env aEnv) {
			if (mod == null)
				mod = aClassInvocation.getKlass().getContext().module();

			DeferredObject<ClassDefinition, Diagnostic, Void> prom = new DeferredObject<>();

			final GenerateFunctions generateFunctions = generatePhase.getGenerateFunctions(mod);
			wl.addJob(_inj().new_WlGenerateClass(generateFunctions, aClassInvocation, generatedClasses,
			                                     codeRegistrar, aEnv
			)); // TODO why add now?
			generatePhase.getWm().addJobs(wl);
			generatePhase.getWm().drain(); // TODO find a better place to put this

			prom.resolve(new ClassDefinition__(aClassInvocation));

			// return prom;
			return aClassInvocation;
		}

		private @NotNull ClassInvocation part2(final @NotNull ClassInvocation aClassInvocation,
		                                       boolean put,
		                                       final @NotNull RegisterClassInvocation_env aEnv) {
			// 2. Check and see if already done
			Collection<ClassInvocation> cls = classInvocationMultimap.get(aClassInvocation.getKlass());
			for (@NotNull
			ClassInvocation ci : cls) {
				if (equivalentGenericPart(ci, aClassInvocation)) {
					return ci;
				}
			}

			if (put) {
				classInvocationMultimap.put(aClassInvocation.getKlass(), aClassInvocation);
			}

			// 3. Generate new EvaClass
			final @NotNull WorkList wl = _inj().new_WorkList();

			var x = getClassInvocation(aClassInvocation, null, wl, aEnv);

			// 4. Return it
			// final ClassDefinition[] yy = new ClassDefinition[1];
			// x.then(y -> yy[0] =y);
			// return yy[0];
			return x;
		}

		public @NotNull ClassInvocation registerClassInvocation(@NotNull ClassInvocation aClassInvocation) {
			RegisterClassInvocation_env env = new RegisterClassInvocation_env(aClassInvocation, new NULL_DeduceTypes2(), null);

			return registerClassInvocation(env);
		}

		public ClassInvocation registerClassInvocation(final @NotNull RegisterClassInvocation_env env) {
			var aClassInvocation = env.classInvocation();

			// 1. select which to return
			final ClassStatement              c   = aClassInvocation.getKlass();
			final Collection<ClassInvocation> cis = classInvocationMultimap.get(c);

			for (@NotNull
			ClassInvocation ci : cis) {
				// don't lose information
				if (ci.getConstructorName() != null)
					if (!(ci.getConstructorName().equals(aClassInvocation.getConstructorName())))
						continue;

				boolean i = equivalentGenericPart(aClassInvocation, ci);
				if (i) {
					if (aClassInvocation instanceof DerivedClassInvocation) {
						if (ci instanceof DerivedClassInvocation)
							continue;

						/* if (classInvocation.resolvePromise().isResolved()) */
						{
							ci.resolvePromise().then((final @NotNull EvaClass result) -> {
								aClassInvocation.resolveDeferred().resolve(result);
							});
							return aClassInvocation;
						}
					} else
						return ci;
				}
			}

			return part2(aClassInvocation, true, env);
		}
	}

	public @NotNull ICodeRegistrar getCodeRegistrar() {
		// 24/01/04 back and forth
		return this.codeRegistrar;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
