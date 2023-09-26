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
import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.impl.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.specs.*;
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
	private final @NotNull FluffyCompImpl                      _fluffyComp;
	@Getter
	private final          CIS                                 _cis;
	private final          CompFactory                         _con;
	private final          LivingRepo                          _repo;
	@Getter
	private final          CompilationConfig                   cfg;
	@Getter
	private final USE                  use;
	@Getter
	private final CompilationEnclosure compilationEnclosure;
	private final CP_Paths             paths;
	private final          EIT_InputTree                       _input_tree;
	private final @NotNull ErrSink                             errSink;
	private final          int                                 _compilationNumber;
	private final          CompilerInputMaster                 master;
	private final          Finally                             _finally;
	private final          CK_ObjectTree                       objectTree;
	public                 CCI_Acceptor__CompilerInputListener cci_listener;
	private @Nullable      EOT_OutputTree                      _output_tree = null;
	@Getter
	private                CompilerInstructions                rootCI;
	private                List<CompilerInput>                 _inputs;
	private                IPipelineAccess                     _pa;
	private                IO                                  io;
	private                boolean                             _inside;

	public CompilationImpl(final @NotNull ErrSink aErrSink, final IO aIo) {
		errSink              = aErrSink;
		io                   = aIo;
		_compilationNumber   = new Random().nextInt(Integer.MAX_VALUE);
		cfg                  = new CompilationConfig();
		_con                 = new DefaultCompFactory(this);
		objectTree           = _con.createObjectTree();
		_repo                = new DefaultLivingRepo();
		compilationEnclosure = new DefaultCompilationEnclosure(this);
		_finally             = new Finally();
		_input_tree          = new EIT_InputTree();
		paths                = new CP_Paths(this);
		_fluffyComp          = new FluffyCompImpl(this);
		use                  = new USE(this.getCompilationClosure());
		_cis                 = new CIS();

		master = new CompilerInputMaster() {
			private final List<CompilerInputListener> listeners = new ArrayList<>();

			public void addListener(CompilerInputListener a) {
				listeners.add(a);
			}

			public void notifyChange(CompilerInput compilerInput, CompilerInput.CompilerInputField compilerInputField) {
				for (CompilerInputListener listener : listeners) {
					listener.baseNotify(compilerInput, compilerInputField);
				}
			}
		};

		cci_listener = new CCI_Acceptor__CompilerInputListener(this);
		master.addListener(cci_listener);
	}

	public @NotNull ICompilationAccess _access() {
		return new DefaultCompilationAccess(this);
	}

	@Override
	public CK_ObjectTree getObjectTree() {
		return objectTree;
	}

	@Override
	public CIS _cis() {
		return get_cis();
	}

	public CompilerBeginning beginning(final @NotNull CompilationRunner compilationRunner) {
		return new CompilerBeginning(this, getRootCI(), getInputs(), compilationRunner.getProgressSink(), cfg());
	}

	@Override
	public @NotNull CompilationConfig cfg() {
		return cfg;
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
	public void feedCmdLine(final @NotNull List<String> args) throws Exception {
		final CompilerController controller = new DefaultCompilerController();

		final List<CompilerInput> inputs = args.stream()
				.map((String s) -> {
					final CompilerInput input = new CompilerInput(s);

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

		for (final CompilerInput compilerInput : _inputs) {
			compilerInput.setMaster(master); // FIXME this is too much i think
		}

		aController.processOptions();
		aController.runner();
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

		compilationEnclosure._resolvePipelineAccessPromise(_pa);
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
	public @NotNull FluffyComp getFluffy() {
		return _fluffyComp;
	}

	@Override
	public List<CompilerInput> getInputs() {
		return _inputs;
	}

	@Override
	public @NotNull EIT_InputTree getInputTree() {
		return _input_tree;
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
	public @NotNull EOT_OutputTree getOutputTree() {
		if (_output_tree == null) {
			_output_tree = new EOT_OutputTree();
		}

		return _output_tree;
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
	public Operation<Ok> hasInstructions(final @NotNull List<CompilerInstructions> cis, final @NotNull IPipelineAccess pa) {
		if (cis.isEmpty()) {
			// README IDEA misconfiguration
			String absolutePath = new File(".").getAbsolutePath();

			getCompilationEnclosure().logProgress(CompProgress.Compilation__hasInstructions__empty, absolutePath);

			//return Operation.failure(new Exception("cis empty"));

			rootCI = cci_listener._root();
		} else

		if (rootCI == null) {
			rootCI = cis.get(0);
		}

		if (null == pa.getCompilation().getInputs()) {
			pa.setCompilerInput(pa.getCompilation().getInputs());
		}

		if (!_inside) {
			_inside = true;





			rootCI.advise(_inputs.get(0));



			getCompilationEnclosure().getCompilationRunner().start(this.cci_listener._root(), pa);
		}

		return Operation.success(Ok.instance());
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

	@Deprecated
//	@Override
	public List<OS_Module> modules() {
		return this.world().modules().stream().map(WorldModule::module).collect(Collectors.toList());
	}

	@Override
	public IPipelineAccess pa() {
		Preconditions.checkNotNull(_pa);

		return _pa;
	}

	@Override
	public CP_Paths paths() {
		return paths;
	}

	List<CompilerInstructions> xxx = new ArrayList<>();
	@Override
	public void pushItem(CompilerInstructions aci) {
		if (xxx.contains(aci)) {
			System.err.println("****************** skip");
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

	public void setRootCI(CompilerInstructions rootCI) {
		this.rootCI = rootCI;
	}

	@Override
	public void subscribeCI(final @NotNull io.reactivex.rxjava3.core.Observer<CompilerInstructions> aCio) {
		_cis.subscribe(aCio);
	}

	public void testMapHooks(final List<IFunctionMapHook> ignoredAMapHooks) {
		// pipelineLogic.dp.
	}

	@Override
	public USE use() {
		return getUse();
	}

	@Override
	public void use(final @NotNull CompilerInstructions compilerInstructions, final boolean do_out) {
		use.use(compilerInstructions);
//		cci_listener.id.add(compilerInstructions);
	}

	@Override
	public LivingRepo world() {
		return _repo;
	}

	public class DefaultObjectTree implements CK_ObjectTree {
		@Override
		public void asseverate(Object o, Asseverate asseveration) {
			switch (asseveration) {
			case ELIJAH_PARSED -> {
/*
				var x = (Pair<ElijahSpec, Operation<OS_Module>>)o;

				var spec = x.getLeft();
				var calm = x.getRight();

				var pl = getCompilationEnclosure().getPipelineLogic();

				var wm = new DefaultWorldModule(calm.success(), getCompilationEnclosure());
				System.err.println("**************************************************Comp ELIJAH_PARSED  "+wm.module().getFileName());
//				pl.addModule(wm);
*/
			}
			case CI_HASHED -> {
				Triple<EzSpec, CK_SourceFile, Operation<String>> t = (Triple<EzSpec, CK_SourceFile, Operation<String>>) o;

				var spec = t.getLeft();
				var hash = t.getRight();
				var p    = t.getMiddle();

				getCompilationEnclosure().logProgress(CompProgress.Ez__HasHash, Pair.of(spec, hash.success()));

				if (p.compilerInput() != null) {
					p.compilerInput().accept_hash(hash.success());
				} else {
					NotImplementedException.raise_stop();
				}
			}
			}
//			NotImplementedException.raise_stop();
		}

		@Override
		public void asseverate(final Asseveration aAsseveration) {
			aAsseveration.onLogProgress(getCompilationEnclosure());
		}
	}
}

//
//
//
