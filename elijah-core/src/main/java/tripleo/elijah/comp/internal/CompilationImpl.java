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
import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah.stages.deduce.fluffy.impl.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CompilationImpl implements Compilation {

	private final @NotNull FluffyCompImpl _fluffyComp;
	private @Nullable      EOT_OutputTree _output_tree = null;

	public CompilationImpl(final ErrSink aEee, final IO aIo) {
		_fluffyComp = new FluffyCompImpl(this);

		errSink = aEee;
		io = aIo;
		_compilationNumber = new Random().nextInt(Integer.MAX_VALUE);

		paths = new CP_Paths(this);

		master = new CompilerInputMaster() {
			private final List<CompilerInputListener> listeners = new ArrayList<>();

			public void notifyChange(CompilerInput compilerInput, CompilerInput.CompilerInputField compilerInputField) {
				for (CompilerInputListener listener : listeners) {
					listener.baseNotify(compilerInput, compilerInputField);
				}
			}

			public void addListener(CompilerInputListener a) {
				listeners.add(a);
			}
		};

		cci_listener = new CCI_Acceptor__CompilerInputListener(this);
		master.addListener(cci_listener);
	}

	public @NotNull ICompilationAccess _access() {
		return new DefaultCompilationAccess(this);
	}

	@Override
	public @NotNull FluffyComp getFluffy() {
		return _fluffyComp;
	}

	@Override
	public @NotNull EOT_OutputTree getOutputTree() {
		if (_output_tree == null) {
			_output_tree = new EOT_OutputTree();
		}

		return _output_tree;
	}

	@Deprecated
//	@Override
	public List<OS_Module> modules() {
		return this.world().modules()
				.stream()
				.map(WorldModule::module)
				.collect(Collectors.toList());
	}

	public CompilerBeginning beginning(final @NotNull CompilationRunner compilationRunner) {
		return new CompilerBeginning(this, getRootCI(), getInputs(), compilationRunner.getProgressSink(), cfg());
	}

	@Override
	public CompilerInputListener getCompilerInputListener() {
		return cci_listener;
	}

	public void testMapHooks(final List<IFunctionMapHook> ignoredAMapHooks) {
		//pipelineLogic.dp.
	}

	@Override
	public Map<String, CompilerInstructions> fn2ci() {
		return fn2ci;
	}

	@Override
	public USE use() {
		return getUse();
	}

	@Override
	public CIS _cis() {
		return get_cis();
	}

	public final Map<String, CompilerInstructions> fn2ci = new HashMap<>();
	@Getter
	private final CIS _cis = new CIS();
	private final CompFactory _con = new DefaultCompFactory(this);
	private final LivingRepo _repo = new DefaultLivingRepo();
	@Getter
	private final CompilationConfig cfg = new CompilationConfig();
	@Getter
	private final USE use = new USE(this);
	private final CompilationEnclosure compilationEnclosure = new CompilationEnclosure(this);
	private final CP_Paths paths;
	private final EIT_InputTree _input_tree = new EIT_InputTree();
	private final ErrSink errSink;
	private final int _compilationNumber;
	private final CompilerInputMaster master;
	public CCI_Acceptor__CompilerInputListener cci_listener;

	public void setRootCI(CompilerInstructions rootCI) {
		this.rootCI = rootCI;
	}

	@Getter
	private CompilerInstructions rootCI;
	private List<CompilerInput> _inputs;
	private IPipelineAccess _pa;
	private IO io;
	private       boolean _inside;
	private final Finally _finally = new Finally();

	@Override
	public CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclosure;
	}

	@Override
	public @NotNull CompFactory con() {
		return _con;
	}

	@Override
	public int errorCount() {
		return errSink.errorCount();
	}

	@Override
	public void feedInputs(final @NotNull List<CompilerInput> aCompilerInputs, final @NotNull CompilerController aController) {
		if (aCompilerInputs.isEmpty()) {
			aController.printUsage();
			return;
		}

		_inputs = aCompilerInputs; // !!
		compilationEnclosure.setCompilerInput(aCompilerInputs);

		aController._setInputs(this, aCompilerInputs);

		for (final CompilerInput compilerInput : _inputs) {
			//	cci.accept(compilerInput.acceptance_ci(), _ps);
			compilerInput.setMaster(master); // FIXME this is too much i think
		}


		aController.processOptions();
		aController.runner();
	}

	@Override
	public void feedCmdLine(final @NotNull List<String> args) throws Exception {
		final CompilerController controller = new DefaultCompilerController();

		final List<CompilerInput> inputs = args.stream()
				.map(s -> {
					final CompilerInput input = new CompilerInput(s);

					// TODO 09/08 check this
					if (s.equals(input.getInp())) {
						input.setSourceRoot();
					}

					return input;
				})
				.collect(Collectors.toList());

		feedInputs(inputs, controller);
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
	public Operation2<WorldModule> findPrelude(final String prelude_name) {
		Operation2<OS_Module> prelude = use.findPrelude(prelude_name);

		assert prelude.mode() == Mode.SUCCESS;

		var prelude1 = new DefaultWorldModule(prelude.success(), compilationEnclosure);

		return Operation2.success(prelude1);
	}

	@Override
	public IPipelineAccess get_pa() {
		return _pa;
	}

	@Override
	public void set_pa(IPipelineAccess a_pa) {
		_pa = a_pa;

		compilationEnclosure.pipelineAccessPromise.resolve(_pa);
	}

	@Override
	public List<CompilerInput> getInputs() {
		return _inputs;
	}

	@Override
	public String getCompilationNumberString() {
		return String.format("%08x", _compilationNumber);
	}

	@Override
	public ErrSink getErrSink() {
		return errSink;
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
	public OS_Package getPackage(final @NotNull Qualident pkg_name) {
		return _repo.getPackage(pkg_name.toString());
	}

	@Override
	public String getProjectName() {
		return rootCI.getName();
	}

	@Override
	public @NotNull Operation<Ok> hasInstructions(final @NotNull List<CompilerInstructions> cis) {
		hasInstructions(cis, pa());
		return Operation.success(Ok.instance());
	}

	@Override
	public void hasInstructions(final @NotNull List<CompilerInstructions> cis,
								final @NotNull IPipelineAccess pa) {
//		assert cis.size() > 0; // FIXME this is corect. below is wrong (allows cis.size()==2)
		//assert cis.size() == 1; // FIXME this is corect. below is wrong (allows cis.size()==2)

		if (cis.isEmpty()) {
			// README IDEA misconfiguration
			String absolutePath = new File(".").getAbsolutePath();

			getCompilationEnclosure().logProgress(CompProgress.Compilation__hasInstructions__empty, absolutePath);

			return;
		}

		rootCI = cis.get(0);

		pa.setCompilerInput(pa.getCompilation().getInputs());

		if (!_inside) {
			_inside = true;
			getCompilationEnclosure().getCompilationRunner().start(this.cci_listener._root(), pa);
		}
	}

	@Override
	@Deprecated
	public int instructionCount() {
		return 4; // TODO shim !!!cis.size();
	}

	@Override
	public boolean isPackage(final @NotNull String pkg) {
		return _repo.hasPackage(pkg);
	}

	@Override
	public OS_Package makePackage(final Qualident pkg_name) {
		return _repo.makePackage(pkg_name);
	}

	@Override
	public @NotNull ModuleBuilder moduleBuilder() {
		return new ModuleBuilder(this);
	}

	@Override
	public IPipelineAccess pa() {
		Preconditions.checkNotNull(_pa);

		return _pa;
	}

	@Override
	public void pushItem(CompilerInstructions aci) {
		_cis.onNext(aci);
	}

	@Override
	public void subscribeCI(final @NotNull io.reactivex.rxjava3.core.Observer<CompilerInstructions> aCio) {
		_cis.subscribe(aCio);
	}

	@Override
	public void use(final @NotNull CompilerInstructions compilerInstructions, final boolean do_out) {
		try {
			use.use(compilerInstructions, do_out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public LivingRepo world() {
		return _repo;
	}

	@Override
	public CP_Paths paths() {
		return paths;
	}

	@Override
	public @NotNull EIT_InputTree getInputTree() {
		return _input_tree;
	}

	@Override
	public @NotNull CompilationConfig cfg() {
		return cfg;
	}

	@Override
	public Finally reports() {
		return _finally;
	}

	/*
	// TODO remove this 04/20
	@Override
	public void addFunctionMapHook(final IFunctionMapHook aFunctionMapHook) {
		getCompilationEnclosure().getCompilationAccess().addFunctionMapHook(aFunctionMapHook);
	}
*/

/*
	@Override
	public void setCompilationEnclosure(final CompilationEnclosure aCompilationEnclosure) {
		throw new NotImplementedException("Can't set CompilationEnclosure");
		//compilationEnclosure = aCompilationEnclosure;
	}
*/

/*
	@Override
	public int compilationNumber() {
		return _compilationNumber;
	}
*/

/*
	@Override
	public void fakeFlow(final List<CompilerInput> aInputs, final @NotNull CompilationFlow aFlow) {
		getCompilationEnclosure().getPipelineAccessPromise()
				.then(pa -> {
					get_pa().setCompilerInput(aInputs);

					aFlow.run(this);
				});
	}
*/

}

//
//
//
