/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.internal;

import com.google.common.base.Preconditions;
import io.reactivex.rxjava3.core.Observer;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.chewtoy.JarWork;
import tripleo.elijah.comp.chewtoy.JarWorkImpl;
import tripleo.elijah.comp.chewtoy.PW_CompilerController;
import tripleo.elijah.comp.graph.*;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.graph.i.CK_Steps;
import tripleo.elijah.comp.graph.i.CK_StepsContext;
import tripleo.elijah.comp.graph.i.CM_Module;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.CompilerInputListener;
import tripleo.elijah.comp.i.extra.ICompilationRunner;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.impl.*;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.inputs.CompilerInputMaster;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.process.CPX_RunStepsContribution;
import tripleo.elijah.comp.process.CPX_Signals;
import tripleo.elijah.comp.nextgen.CP_Paths__;
import tripleo.elijah.comp.process.CPX_CalculateFinishParse;
import tripleo.elijah.comp.process.CPX_RunStepLoop;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pn.PN_Ping;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.comp_model.CM_CompilerInput;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.stages.deduce.IFunctionMapHook;
import tripleo.elijah.stages.deduce.fluffy.i.FluffyComp;
import tripleo.elijah.stages.deduce.fluffy.impl.FluffyCompImpl;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.stages.logging.ElLog_;
import tripleo.elijah.util.*;
import tripleo.elijah.util2.DebugFlags;
import tripleo.elijah.util2.Eventual;
import tripleo.elijah.util2.EventualRegister;
import tripleo.elijah.util2.UnintendedUseException;
import tripleo.elijah.util3._AbstractEventualRegister;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah_elevated.comp.model.Default__Elevated_CM_Factory;
import tripleo.elijah_elevated.comp.model.Elevated_CM_Factory;
import tripleo.elijah_elevated.comp.model.Elevated_CM_Module;

import java.util.*;
import java.util.function.Supplier;

public class CompilationImpl extends _AbstractEventualRegister implements Compilation, EventualRegister {
	private final List<CN_CompilerInputWatcher>                                  _ciws;
	private final Map<CompilerInput, CM_CompilerInput>                           _ci_models;
	private final List<Triple<CN_CompilerInputWatcher.e, CompilerInput, Object>> _ciw_buffer;

	private final FluffyCompImpl                                                 _fluffyComp;
//	@Getter
	private final CompilationConfig                   cfg;
//	@Getter
	private final CompilationEnclosure                compilationEnclosure;
//	@Getter
	private final CIS                                 _cis;
//	@Getter
//	private final CK_Monitor                          defaultMonitor;
//	@Getter
	private final USE                                 use;
	private final CompFactory                         _con;
	private final LivingRepo                          _repo;
	private final CP_Paths                            paths;
	private final ErrSink                             errSink;
	private final int                 _compilationNumber;
	private final CompilerInputMaster master;
	private final Finally             _finally;
	private final CK_ObjectTree                       objectTree;
	private final Map<ElijahSpec, CM_Module>          specToModuleMap;
	private final Map<OS_Module, CM_Module>           moduleToCMMap;
	private final Map<EzSpec, CM_Ez>                  specToEzMap;
	private final List<CompilerInstructions>          xxx;
	private final CCI_Acceptor__CompilerInputListener cci_listener;
	private final PW_Controller                       pw_controller;
	private final LCM lcm;
	private JarWork jarwork;
	private       EIT_InputTree                       _input_tree;
	private       EOT_OutputTree                      _output_tree;
	private       IPipelineAccess                     _pa;
	private       IO                                  io;
	@SuppressWarnings("BooleanVariableAlwaysNegated")
	private       boolean                             _inside;
	private       CompilerInput                       __advisement;
	private                ICompilationAccess3 aICompilationAccess3;
	private final @NotNull CK_Monitor          defaultMonitor;
	private CPX_Signals              cpxSignals;
	private CPX_RunStepsContribution _stepsContribution;
	private CompilerInstructions ___rootCI;

	public CompilationImpl(final @NotNull ErrSink aErrSink, final IO aIo) {
		errSink              = aErrSink;
		io                   = aIo;
		_con                 = new DefaultCompFactory(this);
		lcm                  = new LCM(this);
		specToModuleMap      = new HashMap<>();
		moduleToCMMap        = new HashMap<>();
		specToEzMap          = new HashMap<>();
		xxx                  = new ArrayList<>();
		_compilationNumber   = new Random().nextInt(Integer.MAX_VALUE);
		_fluffyComp          = new FluffyCompImpl(this);
		cfg                  = new CompilationConfig();
		use                  = new USE(this.getCompilationClosure());
		_cis                 = new CIS();
		paths                = new CP_Paths__(this);
		pathsEventual.resolve(paths);
		_repo                = _con.getLivingRepo();
		compilationEnclosure = new DefaultCompilationEnclosure(this);
		defaultMonitor       = _con.createCkMonitor();
		objectTree           = _con.createObjectTree();
		master               = _con.createCompilerInputMaster();
		_finally             = _con.createFinally();
		pw_controller        = _con.createPwController(this);
		cci_listener         = new CCI_Acceptor__CompilerInputListener(this);
		master.addListener(cci_listener);

		_ciws                   = new ArrayList<>();
		_ci_models              = new HashMap<>();
		_ciw_buffer             = new ArrayList<>();
	}

	@Override
	public void ____m() {
		try {
			final JarWork jarwork1 = getJarwork();
			jarwork1.work();
		} catch (WorkException aE) {
			//throw new RuntimeException(aE);
			aE.printStackTrace();
		}
	}

	@Override
	public PW_Controller __pw_controller() {
		return pw_controller;
	}

	public JarWork getJarwork() throws WorkException {
		if (jarwork==null)jarwork = new JarWorkImpl();
		return jarwork;
	}

	public static ElLog_.@NotNull Verbosity gitlabCIVerbosity() {
		final boolean gitlab_ci = isGitlab_ci();
		return gitlab_ci ? ElLog_.Verbosity.SILENT : ElLog_.Verbosity.VERBOSE;
	}

	public static boolean isGitlab_ci() {
		return System.getenv("GITLAB_CI") != null;
	}

	public @NotNull ICompilationAccess _access() {
		return new DefaultCompilationAccess(this);
	}

	@Override
	public CK_ObjectTree getObjectTree() {
		return objectTree;
	}

	@Override
	public int errorCount() {
		return errSink.errorCount();
	}

	public ICompilationAccess3 getCompilationAccess3() {
		if (aICompilationAccess3 == null) {
			aICompilationAccess3 = new ICompilationAccess3() {
				@Override
				public Compilation getComp() {
					return CompilationImpl.this;
				}

				@Override
				public boolean getSilent() {
					return getComp().cfg().silent;
				}

				@Override
				public void addLog(final ElLog aLog) {
					getComp().getCompilationEnclosure().addLog(aLog);
				}

				@Override
				public void writeLogs(final boolean aSilent) {
					assert !aSilent;
					getComp().getCompilationEnclosure().writeLogs();
				}

				@Override
				public PipelineLogic getPipelineLogic() {
					return getComp().getCompilationEnclosure().getPipelineLogic();
				}

				@Override
				public List<ElLog> getLogs() {
					return getComp().getCompilationEnclosure().getLogs();
				}
			};
		}
		return aICompilationAccess3;
	}

	@Override
	public void feedInputs(final @NotNull List<CompilerInput> aCompilerInputs,
						   final @NotNull CompilerController aController) {
		if (aCompilerInputs.isEmpty()) {
			aController.printUsage();
			return;
		}

		// FIXME 12/04 This seems like alot (esp here)
		compilationEnclosure.setCompilerInput(aCompilerInputs);
		aController.setEnclosure(compilationEnclosure);

		for (final CompilerInput compilerInput : aCompilerInputs) {
			compilerInput.setMaster(master); // FIXME this is too much i think
		}

		aController.processOptions();
		aController.runner();
	}

	@Override
	public @NotNull CompilationClosure getCompilationClosure() {
		return new CompilationClosure() {
			@Override
			public ErrSink errSink() {
				return errSink;
			}

			@Override
			public @NotNull Compilation getCompilation() {
				return CompilationImpl.this;
			}

			@Override
			public IO io() {
				return io;
			}
		};
	}

	@Override
	public String getCompilationNumberString() {
		return String.format("%08x", _compilationNumber);
	}

	@Override
	public CompilerInputListener getCompilerInputListener() {
		return cci_listener;
	}

	@Override
	public @NotNull ErrSink getErrSink() {
		return errSink;
	}

	@Override
	public OS_Package getPackage(final @NotNull Qualident pkg_name) {
		return _repo.getPackage(pkg_name.toString());
	}

	@Override
	public String getProjectName() {
		return getRootCI().getName();
	}

	@Override
	public CompilerInstructions getRootCI() {
		final CompilerInstructions compilerInstructions = cci_listener._root();
//		if (compilerInstructions != null && __advisement == null) {
//			__advisement = compilerInstructions.profferCompilerInput();
//		}

		if (___rootCI != null) {
			return ___rootCI;
		} else {
			return compilerInstructions;
		}
	}

	@Override
	public void setRootCI(CompilerInstructions rootCI) {
		___rootCI = rootCI;
	}

	@Override
	public OS_Package makePackage(final Qualident pkg_name) {
		return world().makePackage(pkg_name);
	}

	@Override
	public IO getIO() {
		return io;
	}

	@Override
	public void setIO(final IO io) {
		this.io = io;
	}

	@Override
	public @NotNull FluffyComp getFluffy() {
		return _fluffyComp;
	}

	@Override
	public Operation<Ok> hasInstructions(final @NotNull List<CompilerInstructions> cis, final @NotNull IPipelineAccess pa) {
		if (DebugFlags._pancake_lcm_gate) {
			assert cis.size() > 0;

			// don't inline yet b/c getProjectName
			final CompilerInstructions rootCI = cis.get(0);

			setRootCI(rootCI);

			lcm.asv(rootCI, LCM_Event_RootCI.INSTANCE);

			return Operation.success(Ok.instance());
		} else {
			if (cis.isEmpty()) {
				setRootCI(cci_listener._root());
			} else if (getRootCI() == null) {
				setRootCI(cis.get(0));
			}

			if (!_inside) {
				_inside = true;

				final CompilerInstructions rootCI = getRootCI();
				rootCI.advise(__advisement);

				final LCM_HandleEvent  lcmHandleEvent = new LCM_HandleEvent(this.getLCMAccess(),
				                                                            this.lcm,
				                                                            null,
				                                                            LCM_Event_RootCI.INSTANCE);
				LCM_Event_RootCI.INSTANCE.handle(lcmHandleEvent);
			} else {
				NotImplementedException.raise_stop();
				//throw new UnintendedUseException();
			}

			return Operation.success(Ok.instance());
		}
	}

	@Override
	public @NotNull ModuleBuilder moduleBuilder() {
		return new ModuleBuilder(this);
	}

	@Override
	public CP_Paths paths() {
//		assert /*m*/paths != null;
		return paths;
	}

	@Override
	public void provideCio(final Supplier<CompilerInstructionsObserver> aSupplier) {
		if (aSupplier == null) {
			final CompilerInstructionsObserver cio                  = new CompilerInstructionsObserver(this);
			_cis().set_cio(cio);
		} else {
			throw new UnintendedUseException("case not implemented");
		}
	}

	@Override
	public void pushItem(CompilerInstructions aci) {
		if (xxx.contains(aci)) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("** [CompilerInstructions::pushItem] duplicate instructions: " + aci.getFilename());
			return;
		} else {
			xxx.add(aci);
			_cis.onNext(aci);
		}
	}

	@Override
	public Finally reports() {
		return _finally;
	}

	@Override
	public void subscribeCI(final @NotNull Observer<CompilerInstructions> aCio) {
		_cis.subscribe(aCio);
	}

	@Override
	public @NotNull CompilationConfig cfg() {
		return cfg;
	}

	@Override
	public List<CompilerInput> getInputs() {
		return this.getCompilationEnclosure().getCompilerInput();
	}

	@Override
	public void use(@NotNull final CompilerInstructions compilerInstructions, final USE_Reasoning aReasoning) {
		if (aReasoning.ty() == USE_Reasoning.Type.USE_Reasoning__findStdLib) {
			pushItem(compilerInstructions);
		}

		use.use(compilerInstructions);
		//cci_listener.id.add(compilerInstructions);
	}

	@Override
	public LivingRepo world() {
		return _repo;
	}

	@Override
	public ElijahCache use_elijahCache() {
		return use.getElijahCache();
	}

	@Override
	public void pushWork(final PW_PushWork aInstance, final PN_Ping aPing) {
		((PW_CompilerController) pw_controller).submitWork(aInstance);
	}

	@Override
	public CM_Module megaGrande(final ElijahSpec aSpec, final Operation2<OS_Module> aModuleOperation) {
		CM_Module result;
		if (specToModuleMap.containsKey(aSpec)) {
			result = specToModuleMap.get(aSpec);
		} else {
			assert aModuleOperation.mode() == Mode.SUCCESS;
			result = megaGrande(aModuleOperation.success());
			specToModuleMap.put(aSpec, result);
		}

		result.advise(aSpec);
		//result.advise(aModuleOperation);

		return result;
	}

	@Override
	public CM_Ez megaGrande(final EzSpec aSpec) {
		CM_Ez result;
		if (specToEzMap.containsKey(aSpec)) {
			result = specToEzMap.get(aSpec);
		} else {
			//assert aModuleOperation.mode() == Mode.SUCCESS;
			//result = megaGrande(aModuleOperation.success());
			result = new CM_Ez_();
			specToEzMap.put(aSpec, result);
		}

		result.advise(aSpec);
		//result.advise(aModuleOperation);

		return result;
	}

	@Override
	public LCM_CompilerAccess getLCMAccess() {
		final var _c = this;
		return new LCM_CompilerAccess() {
			@Override
			public Compilation c() {
				return _c;
			}

			@Override
			public ICompilationRunner cr() {
				return c().getRunner();
			}

			@Override
			public CompilationConfig cfg() {
				return c()._cfg();
			}

			@Override
			public LCM getModel() {
				return ((CompilationImpl)c()).lcm;
			}
		};
	}

	@Override
	public ICompilationRunner getRunner() {
		return getCompilationEnclosure().getCompilationRunner();
	}

	@Override
	public CompilationConfig _cfg() {
		return this.cfg;
	}

	@Override
	public CM_CompilerInput get(final CompilerInput aCompilerInput) {
		if (_ci_models.containsKey(aCompilerInput))
			return _ci_models.get(aCompilerInput);

		CM_CompilerInput result = new CM_CompilerInput(aCompilerInput, this);
		_ci_models.put(aCompilerInput, result);
		return result;
	}

	/**
	 * This one is interesting as it doesnt quite fit
	 */
	@Override
	public CM_Module megaGrande(final OS_Module aModule) {
		final Elevated_CM_Module singleModule = modelFactory().singleModule(aModule);

		CM_Module result;
		if (moduleToCMMap.containsKey(aModule)) {
			result = moduleToCMMap.get(aModule);
		} else {
			result = new CM_Module_();
			moduleToCMMap.put(aModule, result);
		}

		result.advise(Operation2.success(aModule));

		assert singleModule.getModule() == result._getModule(); // what are we checking??

		return result;
	}

	Elevated_CM_Factory _modelFactory;

	public Elevated_CM_Factory modelFactory() {
		if (this._modelFactory == null) {
			_modelFactory = new Default__Elevated_CM_Factory(this);
		}
		return _modelFactory;
	}

	@Override
	public void contribute(final Object o) {
		if (o instanceof CPX_RunStepsContribution) {
			_stepsContribution = (CPX_RunStepsContribution) o;
		} else {
			assert false;
		}
	}

	@Override
	public CM_CompilerInput get(final Object aO) {
		System.err.println(aO.getClass().getName());
		if (aO.getClass().getName().equals("tripleo.elijah.nextgen.comp_model.CM_CompilerInput")) {
			return (CM_CompilerInput) aO;
		}
		return null;
	}

	@Override
	public LivingRepo world2() {
		return _repo;
	}

	@Override
	public Operation<Ok> hasInstructions2(@NotNull final List<CompilerInstructions> cis, @NotNull final IPipelineAccess pa) {
		return hasInstructions(cis, pa());
	}

	@Override
	public IPipelineAccess pa() {
		final IPipelineAccess[] pa = new IPipelineAccess[1];

		compilationEnclosure.getPipelineAccessPromise().then(apa -> pa[0] = apa);

		Preconditions.checkNotNull(pa[0]);
		return pa[0];
	}

	@Override
	public Operation2<GWorldModule> findPrelude(final String prelude_name) {
		final Operation2<OS_Module> prelude = use.findPrelude(prelude_name);

		if (prelude.mode() == Mode.SUCCESS) {
			final OS_Module   m        = prelude.success();
			final WorldModule prelude1 = _con.createWorldModule(m);

			return Operation2.success(prelude1);
		} else {
			return Operation2.failure(prelude.failure()); // FIXME 10/15 chain
		}
	}

	@Override
	public void set_pa(final GPipelineAccess aPipelineAccess) {
		//set_pa((IPipelineAccess) aPipelineAccess);
		throw new UnintendedUseException("not in ep");
	}

	@Override
	public CIS _cis() {
		return _cis;
	}

	@Override
	public @NotNull CompFactory con() {
		return _con;
	}

	@Override
	public @NotNull EOT_OutputTree getOutputTree() {
		if (_output_tree == null) {
			_output_tree = _con.createOutputTree();
		}

		return _output_tree;
	}

	@Override
	public @NotNull EIT_InputTree getInputTree() {
		if (_input_tree == null) {
			_input_tree = _con.createInputTree();
		}

		return _input_tree;
	}

	@Override
	public void subscribeCI(final ICompilerInstructionsObserver aCio) {
		throw new UnintendedUseException(); // If this doesn't trigger on Core tests, remove
	}

	public void testMapHooks(final List<IFunctionMapHook> ignoredAMapHooks) {
		// pipelineLogic.dp.
	}

	public CP_Paths _paths() {
		assert paths != null;
		return paths;
	}

	@Override
	public void addCompilerInputWatcher(final CN_CompilerInputWatcher aCNCompilerInputWatcher) {
		_ciws.add(aCNCompilerInputWatcher);
	}

	@Override
	public void compilerInputWatcher_Event(final CN_CompilerInputWatcher.e aEvent, final CompilerInput aCompilerInput, final Object aO) {
		if (_ciws.isEmpty()) {
			_ciw_buffer.add(Triple.of(aEvent, aCompilerInput, aO));
		} else {
			if (!_ciw_buffer.isEmpty()) {
				for (Triple<CN_CompilerInputWatcher.e, CompilerInput, Object> triple : _ciw_buffer) {
					for (CN_CompilerInputWatcher ciw : _ciws) {
						ciw.event(triple.getLeft(), triple.getMiddle(), triple.getRight());
					}
				}
				_ciw_buffer.clear();
			}
			for (CN_CompilerInputWatcher ciw : _ciws) {
				ciw.event(aEvent, aCompilerInput, aO);
			}
		}
	}

	@Override
	public CompilationEnclosure getCompilationEnclosure() {
		// back and forth
		return this.compilationEnclosure;
	}

	public CK_Monitor getDefaultMonitor() {
		// back and forth
		return this.defaultMonitor;
	}

	@Override
	public CPX_Signals signals() {
		if(cpxSignals == null) {
			cpxSignals = new CPX_Signals() {
				@Override public void subscribeCalculateFinishParse(CPX_CalculateFinishParse cp_OutputPath) {
					pathsEventual.then(paths1 -> paths1.subscribeCalculateFinishParse(cp_OutputPath));
				}

				@Override
				public void subscribeRunStepLoop(final CPX_RunStepLoop cp_RunStepLoop) {
					// TODO 24/01/13 create process and rpc to it
					Preconditions.checkNotNull(_stepsContribution);
					final CK_Steps        steps       = _stepsContribution.steps();
					final CK_StepsContext stepContext = _stepsContribution.stepsContext();

					compilationEnclosure.runStepsNow(steps, stepContext);
					cp_RunStepLoop.notify_CPX_RunStepLoop(getErrSink(), getOutputTree(), getObjectTree());
				}

				@Override
				public void signalRunStepLoop(final CompilerInstructions aRoot) {
					getRunner().start(aRoot, pa()); // still eeeeeeeeeeeeeeeeee
				}
			};
		}
		return cpxSignals;
	}
	
	private Eventual<CP_Paths> pathsEventual = new Eventual<>();
	
}

//
//
//
