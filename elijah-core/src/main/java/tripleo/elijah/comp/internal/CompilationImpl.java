/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp.internal;

import com.google.common.base.*;
import io.reactivex.rxjava3.core.Observer;
import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.impl.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah.stages.deduce.fluffy.impl.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;

import java.util.*;
import java.util.stream.*;

public class CompilationImpl implements Compilation {
	private final @NotNull FluffyCompImpl       _fluffyComp;
	@Getter
	private final          CIS                  _cis;
	@Getter
	private final          USE                  use;
	private final          CompFactory          _con;
	private final          LivingRepo           _repo;
	@Getter
	private final          CompilationConfig    cfg;
	@Getter
	private final          CompilationEnclosure compilationEnclosure;
	private final          CP_Paths             paths;
	private final @NotNull ErrSink              errSink;
	private final          int                  _compilationNumber;
	private final          CompilerInputMaster  master;
	private final          Finally              _finally;

	private final     CK_ObjectTree              objectTree;
	@Getter
	private final     CK_Monitor                 defaultMonitor  = new __CK_Monitor();
	private final     Map<ElijahSpec, CM_Module> specToModuleMap = new HashMap<>();
	private final     Map<OS_Module, CM_Module>  moduleToCMMap   = new HashMap<>();
	private final     Map<EzSpec, CM_Ez>         specToEzMap     = new HashMap<>();
	private final     List<CompilerInstructions> xxx;
	private final CCI_Acceptor__CompilerInputListener cci_listener;
	private final PW_Controller                       pw_controller;
	private           EIT_InputTree              _input_tree;
	private @Nullable EOT_OutputTree             _output_tree    = null;
	private List<CompilerInput> _inputs;
	private IPipelineAccess     _pa;
	private IO                  io;
	private boolean             _inside;

	public CompilationImpl(final @NotNull ErrSink aErrSink, final IO aIo) {
		errSink              = aErrSink;
		io                   = aIo;
		_compilationNumber   = new Random().nextInt(Integer.MAX_VALUE);
		cfg                  = new CompilationConfig();
		_con                 = new DefaultCompFactory(this);
		objectTree           = _con.createObjectTree();
		_repo                = new DefaultLivingRepo();
		compilationEnclosure = new DefaultCompilationEnclosure(this);
		_finally             = new Finally_();
		paths                = new CP_Paths__(this);
		_fluffyComp          = new FluffyCompImpl(this);
		use                  = new USE(this.getCompilationClosure());
		_cis                 = new CIS();

		master = _con.createCompilerInputMaster();

		cci_listener = new CCI_Acceptor__CompilerInputListener(this);
		master.addListener(cci_listener);

		pw_controller = new PW_CompilerController(this);
		xxx           = new ArrayList<>();
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

	@Override
	public void feedCmdLine(final @NotNull List<String> args) throws Exception {
		final CompilerController controller = new DefaultCompilerController();

		final List<CompilerInput> inputs = args.stream()
				.map((String s) -> {
					final CompilerInput input = new CompilerInput_(s);

					if (s.startsWith("-")) {
						input.setArg();
					} else {
						// TODO 09/24 check this
						input.setSourceRoot();
					}

					return input;
				}).collect(Collectors.toList());

		feedInputs(inputs, controller);
	}

	@Override
	public void feedInputs(final @NotNull List<CompilerInput> aCompilerInputs,
						   final @NotNull CompilerController aController) {
		if (aCompilerInputs.isEmpty()) {
			aController.printUsage();
			return;
		}

		_inputs = aCompilerInputs; // !!
		compilationEnclosure.setCompilerInput(aCompilerInputs);

		aController._setInputs(this, aCompilerInputs);

		assert _inputs != null;

		for (final CompilerInput compilerInput : _inputs) {
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
		return cci_listener._root();
	}

	@Override
	public void setRootCI(CompilerInstructions rootCI) {
		//cci_listener.id.root = rootCI;
	}

	@Override
	public int instructionCount() {
		throw new UnintendedUseException();
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
		if (cis.isEmpty()) {
			//String absolutePath = new File(".").getAbsolutePath();
			//
			//getCompilationEnclosure().logProgress(CompProgress.Compilation__hasInstructions__empty, absolutePath);

			setRootCI(cci_listener._root());
		} else if (getRootCI() == null) {
			setRootCI(cis.get(0));
		}

		if (null == pa.getCompilation().getInputs()) {
			pa.setCompilerInput(pa.getCompilation().getInputs());
		}

		if (!_inside) {
			_inside = true;

			final CompilerInstructions rootCI = getRootCI();
			//final CompilerInstructions rootCI = this.cci_listener._root();
			rootCI.advise(_inputs.get(0));

			final CompilationRunner compilationRunner = getCompilationEnclosure().getCompilationRunner();
			compilationRunner.start(rootCI, pa);
		} else {
			NotImplementedException.raise_stop();
			//throw new UnintendedUseException();
		}

		return Operation.success(Ok.instance());
	}

	@Override
	public @NotNull ModuleBuilder moduleBuilder() {
		return new ModuleBuilder(this);
	}

	@Override
	public CP_Paths paths() {
		return paths;
	}

	@Override
	public void pushItem(CompilerInstructions aci) {
		if (xxx.contains(aci)) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("****************** skip");
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
		return _inputs;
	}

	@Override
	public void use(@NotNull final CompilerInstructions compilerInstructions, final USE_Reasoning aReasoning) {
		if (aReasoning.ty() == USE_Reasoning_.USE_Reasoning__findStdLib) {
			pushItem(compilerInstructions);
		}

		use.use(compilerInstructions);
//		cci_listener.id.add(compilerInstructions);
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

	/**
	 * This one is interesting as it doesnt quite fit
	 */
	@Override
	public CM_Module megaGrande(final OS_Module aModule) {
		CM_Module result;
		if (moduleToCMMap.containsKey(aModule)) {
			result = moduleToCMMap.get(aModule);
		} else {
			result = new CM_Module_();
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
	public CIS _cis() {
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
		return paths;
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
			public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken.makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
			public static final DriverToken COMPILATION_RUNNER_START        = DriverToken.makeToken("COMPILATION_RUNNER_START");
		}
	}

	class __CK_Monitor implements CK_Monitor {
		@Override
		public void reportSuccess() {
			throw new UnintendedUseException();
		}

		@Override
		public void reportFailure() {
			throw new UnintendedUseException();
		}
	}
}

//
//
//
