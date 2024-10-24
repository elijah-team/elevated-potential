/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevated_durable.comp;

import com.google.common.base.Preconditions;
import io.reactivex.rxjava3.core.Observer;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.CompilerInputListener;
import tripleo.elijah.comp.nextgen.i.CPX_CalculateFinishParse;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.pn.PN_Ping;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_PushWork;
import tripleo.elijah.comp.percy.CN_CompilerInputWatcher;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.compiler_model.CM_Module;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GWorldModule;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_elevated_durable.paths_impl.EDL_CP_Paths;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated.comp.input.EDL_CompilerInput;
import tripleo.elijah_elevated.comp.pushwork.PW_CompilerController;
import tripleo.elijah.fluffy.FluffyComp;
import tripleo.elijah_elevated_durable.comp_model.*;
import tripleo.elijah_elevated_durable.compilation_bus.EDL_CompilationRunner;
import tripleo.elijah_elevated_durable.fluffy_impl.FluffyCompImpl;
import tripleo.elijah_elevated_durable.input.USE;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah_elevateder.comp.*;
import tripleo.elijah_elevateder.comp.i.*;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.comp.internal.CCI_Acceptor__CompilerInputListener;
import tripleo.elijah_elevateder.factory.NonOpinionatedBuilder;
import tripleo.elijah_elevateder.nextgen.comp_model.CM_CompilerInput;
import tripleo.elijah_elevateder.stages.deduce.IFunctionMapHook;
import tripleo.elijah_elevateder.stages.logging.ElLog_;
import tripleo.elijah_elevateder.world.i.LivingRepo;
import tripleo.elijah_elevateder.world.i.WorldModule;
import tripleo.elijah_fluffy.util.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EDL_Compilation implements Compilation, EventualRegister {
	private final List<CN_CompilerInputWatcher>                                  _ciws;
	private final Map<CompilerInput, CM_CompilerInput>                           _ci_models;
	private final List<Triple<CN_CompilerInputWatcher.e, CompilerInput, Object>> _ciw_buffer;

	private final FluffyCompImpl       _fluffyComp;
	//	@Getter
	private final CompilationConfig    cfg;
	//	@Getter
	private final CompilationEnclosure compilationEnclosure;
	//	@Getter
	private final EDL_CIS              _cis;
	//	@Getter
//	private final CK_Monitor                          defaultMonitor;
//	@Getter
	private final USE                  use;
	private final CompFactory _con;
	private final    LivingRepo                 _repo;
	private final    CP_Paths                            paths;
	private final    ErrSink                             errSink;
	private final    int                                 _compilationNumber;
	private final    CompilerInputMaster                 master;
	private final    Finally                             _finally;
	private final    CK_ObjectTree                       objectTree;
	private final    Map<ElijahSpec, CM_Module>          specToModuleMap;
	private final    Map<OS_Module, CM_Module> moduleToCMMap;
	private final    Map<EzSpec, CM_Ez>        specToEzMap;
	private final List<CompilerInstructions>   xxx;
	private final CCI_Acceptor__CompilerInputListener cci_listener;
	private final PW_Controller                       pw_controller;
	private final LCM                                 lcm;
	private       JarWork                             jarwork;
	private          EIT_InputTree                       _input_tree;
	private          EOT_OutputTree                      _output_tree;
	private          IPipelineAccess                     _pa;
	private          IO                                  io;
	@SuppressWarnings("BooleanVariableAlwaysNegated")
	private          boolean                             _inside;
	private          CompilerInput              __advisement;
	private          ICompilationAccess3        compilationAccess3;
	private @NotNull CK_Monitor                 defaultMonitor;
	private       CPX_Signals        cpxSignals;
	private       Eventual<CP_Paths> _p_pathsEventual = new Eventual<>();

	public EDL_Compilation(final @NotNull ErrSink aErrSink, final IO aIo) {
		errSink            = aErrSink;
		io                 = aIo;
		_con               = new EDL_CompFactory(this);
		lcm                = new LCM(this);
		specToModuleMap    = new HashMap<>();
		moduleToCMMap      = new HashMap<>();
		specToEzMap        = new HashMap<>();
		xxx                = new ArrayList<>();
		_compilationNumber = new Random().nextInt(Integer.MAX_VALUE);
		_fluffyComp        = new FluffyCompImpl(this);
		cfg                = new CompilationConfig();
		use                = new USE(this.getCompilationClosure());
		_cis               = new EDL_CIS();
		paths              = new EDL_CP_Paths(this);
		_p_pathsEventual.resolve(paths);
		_repo                = _con.getLivingRepo();
		compilationEnclosure = new EDL_CompilationEnclosure(this);
		defaultMonitor       = _con.createCkMonitor();
		objectTree           = _con.createObjectTree();
		master               = _con.createCompilerInputMaster();
		_finally             = _con.createFinally();
		pw_controller        = _con.createPwController(this);
		cci_listener         = new CCI_Acceptor__CompilerInputListener(this);
		master.addListener(cci_listener);

		_ciws       = new ArrayList<>();
		_ci_models  = new HashMap<>();
		_ciw_buffer = new ArrayList<>();
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
	public PW_CompilerController get_pw() {
		return (PW_CompilerController) this.pw_controller;
	}

	public JarWork getJarwork() throws WorkException {
		if (jarwork == null) jarwork = new JarWorkImpl();
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
		return new EDL_CompilationAccess(this);
	}

	@Override
	public CK_ObjectTree getObjectTree() {
		return objectTree;
	}

	@Override
	public int errorCount() {
		return errSink.errorCount();
	}

	@Override
	public void feedCmdLine(final @NotNull List<String> args) {
		final CompilerController controller = new EDL_CompilerController(this.getCompilationAccess3());
		final NonOpinionatedBuilder nob = new NonOpinionatedBuilder();
		final List<CompilerInput> inputs = nob.inputs(args);
		feedInputs(inputs, controller);
	}

	public ICompilationAccess3 getCompilationAccess3() {
		var _c = this;
		if (compilationAccess3 == null) {
			compilationAccess3 = new ICompilationAccess3() {
				@Override
				public Compilation getComp() {
					return _c;
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
				public List<ElLog> getLogs() {
					return getComp().getCompilationEnclosure().getLogs();
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
			};
		}
		return compilationAccess3;
	}

	@NotNull
	public List<CompilerInput> stringListToInputList(final @NotNull List<String> args) {
		final List<CompilerInput> inputs = args.stream()
				.map(s -> {
					final CompilerInput    input = new EDL_CompilerInput(s, Optional.of(this));
					final CM_CompilerInput cm    = this.get(input);
					if (cm.inpSameAs(s)) {
						input.setSourceRoot();
					} else {
						assert false;
					}
					return input;
				})
				.collect(Collectors.toList());
		return inputs;
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
				return EDL_Compilation.this;
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
		return cci_listener._root();
	}

	@Override
	public void setRootCI(CompilerInstructions rootCI) {
		//cci_listener.id.root = rootCI;
	}

	@Override
	public boolean isPackage(@NotNull final String pkg_name) {
		return world().isPackage(pkg_name);
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

			lcm.asv(rootCI, LCM_Event_RootCI.instance());

			return Operation.success(Ok.instance());
		} else {
			if (cis.isEmpty()) {
				//String absolutePath = new File(".").getAbsolutePath();
				//
				//getCompilationEnclosure().logProgress(CompProgress.Compilation__hasInstructions__empty, absolutePath);

				setRootCI(cci_listener._root());
			} else if (getRootCI() == null) {
				setRootCI(cis.get(0));
			}

			//if (null == pa.getCompilation().getInputs()) {
			//	pa.setCompilerInput(pa.getCompilation().getInputs());
			//}

			if (!_inside) {
				_inside = true;

				final CompilerInstructions rootCI = getRootCI();
				//final CompilerInstructions rootCI = this.cci_listener._root();
				rootCI.advise(__advisement/*_inputs.get(0)*/);

				final EDL_CompilationRunner compilationRunner = getCompilationEnclosure().getCompilationRunner();
				compilationRunner.start(rootCI, pa);
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
	public void addCodeOutput(final EOT_FileNameProvider aFileNameProvider, final Supplier<EOT_OutputFile> aOutputFileSupplier, final boolean addFlag) {
		final EOT_OutputFile eof = aOutputFileSupplier.get();
		final Finally.Output e   = reports().addCodeOutput(aFileNameProvider, eof);
		if (addFlag) {
			getOutputTree().add(eof);
		}
	}

	@Override
	public void pushItem(CompilerInstructions aci) {
		if (xxx.contains(aci)) {
			SimplePrintLoggerToRemoveSoon.println_err_4("** [CompilerInstructions::pushItem] duplicate instructions: " + aci.getFilename());
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
		//return _inputs;
		throw new UnintendedUseException();
	}

	@Override
	public void use(@NotNull final CompilerInstructions compilerInstructions, final USE_Reasoning aReasoning) {
		if (aReasoning.ty() == USE_Reasoning.Type.USE_Reasoning__findStdLib) {
			pushItem(compilerInstructions);
		}

		use.use(compilerInstructions);
		//cci_listener.id.add(rootCI);
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
			result = new EDL_CM_Ez();
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
			public EDL_CompilationRunner cr() {
				return c().getRunner();
			}

			@Override
			public CompilationConfig cfg() {
				return c()._cfg();
			}
		};
	}

	@Override
	public EDL_CompilationRunner getRunner() {
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
		CM_Module result;
		if (moduleToCMMap.containsKey(aModule)) {
			result = moduleToCMMap.get(aModule);
		} else {
			result = new EDL_CM_Module();
			moduleToCMMap.put(aModule, result);
		}

		result.advise(Operation2.success(aModule));

		return result;
	}

	@Override
	public LivingRepo world2() {
		return _repo;
	}

	@Override
	public Operation<Ok> hasInstructions2(@NotNull final List<CompilerInstructions> cis, @NotNull final IPipelineAccess pa) {
		return hasInstructions(cis, get_pa());
	}

	@Override
	public IPipelineAccess pa() {
		Preconditions.checkNotNull(_pa);

		return _pa;
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
	public IPipelineAccess get_pa() {
		return _pa;
	}

	@Override
	public void set_pa(final GPipelineAccess aPipelineAccess) {
		set_pa((IPipelineAccess) aPipelineAccess);
	}

	@Override
	public void set_pa(IPipelineAccess a_pa) {
		_pa = a_pa;
	}

	@Override
	public EDL_CIS _cis() {
		return _cis; //get_cis();
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
	public void checkFinishEventuals() {
		throw new UnintendedUseException();
	}

	@Override
	public <P> void register(final Eventual<P> aEventual) {
		//throw new UnintendedUseException();
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

	public enum CompilationAlways {
		;

		@NotNull
		public static String defaultPrelude() {
			return "c";
		}

		public enum Tokens {
			;

			// README 10/20 Disjointed needs annotation
			//  12/04 ServiceLoader
			public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken.makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
			public static final DriverToken COMPILATION_RUNNER_START        = DriverToken.makeToken("COMPILATION_RUNNER_START");
		}
	}

	static class __CK_Monitor implements CK_Monitor {
		@Override
		public void reportSuccess() {
			throw new UnintendedUseException();
		}

		@Override
		public void reportFailure() {
			throw new UnintendedUseException();
		}
	}

	@Override
	public CompilationEnclosure getCompilationEnclosure() {
		// TODO Auto-generated method stub
		return compilationEnclosure;
	}

	public CK_Monitor getDefaultMonitor() {
		// back and forth
		return this.defaultMonitor;
	}

	@Override
	public CPX_Signals signals() {
		if (cpxSignals == null) {
			cpxSignals = new CPX_Signals() {

				@Override
				public void subscribeCalculateFinishParse(CPX_CalculateFinishParse cp_OutputPath) {
					pathsEventual.then(paths1 -> {
						paths1.subscribeCalculateFinishParse(cp_OutputPath);
					});
				}
			};
		}
		return cpxSignals;
	}

	private Eventual<CP_Paths> pathsEventual = new Eventual<>();
}
