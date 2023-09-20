/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import lombok.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.util.Maybe;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static tripleo.elijah.util.Helpers.*;

public abstract class __Compilation1 implements Compilation {
	public final Map<String, CompilerInstructions> fn2ci = new HashMap<String, CompilerInstructions>();
	@Getter
	private final CIS _cis = new CIS();
	private final CompFactory _con = new DefaultCompFactory();
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
	@Getter
	private CompilerInstructions rootCI;
	private List<CompilerInput> _inputs;
	private IPipelineAccess _pa;
	private IO io;
	private boolean _inside;

	public __Compilation1(final ErrSink errSink, final IO io) {
		this.errSink = errSink;
		this.io = io;
		this._compilationNumber = new Random().nextInt(Integer.MAX_VALUE);

		this.paths = new CP_Paths(this);

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

	// TODO remove this 04/20
	@Override
	public void addFunctionMapHook(final IFunctionMapHook aFunctionMapHook) {
		getCompilationEnclosure().getCompilationAccess().addFunctionMapHook(aFunctionMapHook);
	}

	@Override
	public CompilationEnclosure getCompilationEnclosure() {
		return compilationEnclosure;
	}

	@Override
	public void setCompilationEnclosure(final CompilationEnclosure aCompilationEnclosure) {
		throw new NotImplementedException("Can't set CompilationEnclosure");
		//compilationEnclosure = aCompilationEnclosure;
	}

	@Override
	public void addModule__(final @NotNull OS_Module module, final @NotNull String fn) {

	}

	@Override
	public int compilationNumber() {
		return _compilationNumber;
	}

	@Override
	public @NotNull CompFactory con() {
		return _con;
	}

	@Override
	public void eachModule(final @NotNull Consumer<WorldModule> object) {
		var modules1 = livingRepo().modules();

		for (final WorldModule mod : modules1) {
			object.accept(mod);
		}
	}

	@Override
	public int errorCount() {
		return errSink.errorCount();
	}

	@Override
	public void feedInputs(final @NotNull List<CompilerInput> aCompilerInputs, final @NotNull CompilerController aController) {
		if (aCompilerInputs.size() == 0) {
			aController.printUsage();
			return;
		}

		_inputs = aCompilerInputs; // !!
		compilationEnclosure.setCompilerInput(aCompilerInputs);

		if (aController instanceof DefaultCompilerController) {
			aController._setInputs(this, aCompilerInputs);
			//} else if (aController instanceof UT_Controller uctl) {
			//	uctl._setInputs(this, aCompilerInputs);
		}

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
				return __Compilation1.this;
			}

			@Override
			public IO io() {
				return io;
			}
		};
	}

	@Override
	public @NotNull List<ClassStatement> findClass(final String aClassName) {
		final List<ClassStatement> l = new ArrayList<ClassStatement>();
		var modules1 = world().modules()
				.stream()
				.map(wm -> wm.module())
				.collect(Collectors.toList());

		for (final OS_Module module : modules1) {
			if (module.hasClass(aClassName)) {
				l.add((ClassStatement) module.findClass(aClassName));
			}
		}

		return l;
	}

	@Override
	public Operation2<WorldModule> findPrelude(final String prelude_name) {
		return use.findPrelude(prelude_name);
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
		assert cis.size() > 0; // FIXME this is corect. below is wrong (allows cis.size()==2)
		//assert cis.size() == 1; // FIXME this is corect. below is wrong (allows cis.size()==2)

		if (cis.size() == 0) {
			// README IDEA misconfiguration
			String absolutePath = new File(".").getAbsolutePath();

			getCompilationEnclosure().logProgress(CompProgress.Compilation__hasInstructions__empty, absolutePath);

			return;
		}

		rootCI = cis.get(0);

		pa.setCompilerInput(pa.getCompilation().getInputs());

		if (!_inside) {
			_inside = true;
			getCompilationEnclosure().getCompilationRunner().start(rootCI, pa);
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
		//assert _pa != null;

		return _pa;
	}

	@Override
	public void pushItem(CompilerInstructions aci) {
		_cis.onNext(aci);
	}

	@Override
	public void subscribeCI(final @NotNull Observer<CompilerInstructions> aCio) {
		_cis.subscribe(aCio);
	}

	@Override
	public void use(final @NotNull CompilerInstructions compilerInstructions, final boolean do_out) {
		use.use(compilerInstructions, do_out);
	}

	@Override
	public LivingRepo world() {
		return _repo;
	}

	@Override
	public LivingRepo livingRepo() {
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

	public /*static*/ class CCI_Acceptor__CompilerInputListener implements CompilerInputListener {
		private final Compilation compilation;
		private CCI cci;
		private IProgressSink _ps;

		public CCI_Acceptor__CompilerInputListener(Compilation compilation) {
			this.compilation = compilation;
		}

		public void set(CCI aCci, IProgressSink aPs) {
			cci = aCci;
			_ps = aPs;
		}

		@Override
		public void change(CompilerInput i, CompilerInput.CompilerInputField field) {
			var inputTree = compilation.getInputTree();

			compilation.getCompilationEnclosure().logProgress(CompProgress.__CCI_Acceptor__CompilerInputListener__change__logInput, i);

			switch (field) {
				case TY -> {

					switch (i.ty()) {
					case NULL -> {
						int y2=2;
						//inputTree.addNode(i); README obviously skip nulls
					}
					case SOURCE_ROOT -> {
						int y3=2;
						inputTree.addNode(i);
					}
					case ROOT -> {
						inputTree.addNode(i);

						final CompilationRunner cr = getCompilationEnclosure().getCompilationRunner();
						final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
						if (instructionsMaybe != null) {
							var ci = instructionsMaybe.o.get();

							assert ci != null;

							cr._cis().onNext(ci);
						}
					}
					// README has to wait for ACCEPT_CI, as it is assigned after `ty` is changed
//						hasInstructions(List_of(i....));
					case ARG -> {
						// inputTree.addNode(i); README skip ARGS

						// FIXME processOption here (ie apply compiler change)
						int yyy = 2;
					}
				}
				}
				case ACCEPT_CI -> {
					if (i.ty() == CompilerInput.Ty.ROOT) {
						final CompilationRunner cr = getCompilationEnclosure().getCompilationRunner();
						final Maybe<ILazyCompilerInstructions> instructionsMaybe = i.acceptance_ci();
						if (instructionsMaybe != null) {
							var ci = instructionsMaybe.o.get();

							assert ci != null;

							if (false) {
								cr._cis().onNext(ci);
								hasInstructions(List_of(i.acceptance_ci().o.get()));
							}
						}
					}
				}
				case HASH -> {
					int yy = 2;
					// FIXME latch all create/commit inputs.txt -> should be Buffer!!
				}
				case DIRECTORY_RESULTS -> {
					int y = 2;

					if (i.getDirectoryResults() != null) {
						List<Operation2<CompilerInstructions>> directoryResults = i.getDirectoryResults();

						for (Operation2<CompilerInstructions> directoryResult : directoryResults) {
							if (directoryResult.mode() == Mode.SUCCESS) {
								cci.accept(new Maybe<>(ILazyCompilerInstructions.of(directoryResult.success()), null), _ps);
							}
						}
					}
				}
			}
		}
	}

	//public void setRootCI(CompilerInstructions aRootCI) {
	//	rootCI = aRootCI;
	//}

	private class DefaultCompFactory implements CompFactory {
		@Override
		public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
			return new EIT_ModuleInput(aModule, __Compilation1.this);
		}

		@Override
		public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
			Qualident R = new QualidentImpl();
			for (String s : sl) {
				R.append(Helpers.string_to_ident(s));
			}
			return R;
		}

		@Override
		public @NotNull InputRequest createInputRequest(final File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp) {
			return new InputRequest(aFile, aDo_out, aLsp);
		}

		@Override
		public @NotNull ICompilationAccess createCompilationAccess() {
			return new DefaultCompilationAccess(__Compilation1.this);
		}

		@Contract(" -> new")
		@Override
		public @NotNull ICompilationBus createCompilationBus() {
			return new DefaultCompilationBus(Objects.requireNonNull(compilationEnclosure));
		}

		@Contract("_ -> new")
		@Override
		public @NotNull CompilerBeginning createBeginning(final @NotNull CompilationRunner aCompilationRunner) {
			return new CompilerBeginning(__Compilation1.this, rootCI, _inputs, aCompilationRunner.getProgressSink(), cfg());
		}
	}
}

//
//
//
