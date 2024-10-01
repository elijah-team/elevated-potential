package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.CompilerInputMaster;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.CompilerInputListener;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.comp_model.CM_UleLog;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah_fluffy.util.Operation2;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_elevated.comp.input.CX_ParseElijahFile;
import tripleo.elijah_elevated.comp.pushwork.*;
import tripleo.elijah_elevated_durable.lang_impl.QualidentImpl;
import tripleo.elijah_elevated_durable.world_impl.DefaultLivingRepo;
import tripleo.elijah_elevated_durable.world_impl.DefaultWorldModule;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.i.CompFactory;
import tripleo.elijah_elevateder.comp.internal.ILazyCompilerInstructions_;
import tripleo.elijah_elevateder.nextgen.inputtree.EIT_InputTreeImpl;
import tripleo.elijah_elevateder.nextgen.inputtree.EIT_ModuleInputImpl;
import tripleo.elijah_elevateder.nextgen.outputtree.EOT_OutputTreeImpl;
import tripleo.elijah_elevateder.util.Helpers0;
import tripleo.elijah_elevateder.world.i.LivingRepo;
import tripleo.elijah_elevateder.world.i.WorldModule;

import java.util.*;

class EDL_CompFactory implements CompFactory {
	private final EDL_Compilation compilation;
	private       CM_UleLog       _log;

	public EDL_CompFactory(EDL_Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Contract(" -> new")
	@Override
	public @NotNull ICompilationAccess createCompilationAccess() {
		return new EDL_CompilationAccess(compilation);
	}

	@Contract(" -> new")
	@Override
	public @NotNull ICompilationBus createCompilationBus() {
		return new EDL_CompilationBus(Objects.requireNonNull(compilation.getCompilationEnclosure()));
	}

	@Contract("_ -> new")
	@Override
	public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
		return new EIT_ModuleInputImpl(aModule, compilation);
	}

	@Contract("_ -> new")
	@Override
	public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
		final Qualident R = new QualidentImpl();
		// README 10/13 avoid inclination to
		for (final String s : sl) {
			R.append(Helpers0.string_to_ident(s));
		}
		return R;
	}

	@Contract(" -> new")
	@Override
	public CK_ObjectTree createObjectTree() {
		return new EDL_ObjectTree(compilation);
	}

	@Contract("_ -> new")
	@Override
	public CY_ElijahSpecParser defaultElijahSpecParser(final ElijahCache elijahCache) {
		return new CY_ElijahSpecParser() {
			@Override
			public Operation2<OS_Module> parse(ElijahSpec spec) {
				final Compilation           c  = compilation;
				final Operation2<OS_Module> om = CX_realParseElijjahFile2.realParseElijjahFile2(spec, elijahCache, c);
				return om;
			}
		};
	}

	@Override
	public WorldModule createWorldModule(OS_Module aModule) {
		return new DefaultWorldModule(aModule, compilation.getCompilationEnclosure());
	}

	@Override
	public PW_PushWorkQueue createWorkQueue() {
		return new PW_PushWorkQueue__JC();
//		return new PW_PushWorkQueue_Blocking();
		//return new PW_PushWorkQueue_Concurrent();
	}

	@Override
	public Startable askConcurrent(final StartableI aRunnable) {
		final Thread thread = new Thread(aRunnable::run);
		thread.setName(aRunnable.getThreadName());
		return new Startable() {
			@Override
			public void start() {
				thread.start();
			}

			@Deprecated @Override
			public Thread stealThread() {
				return thread;
			}

			@Override
			public boolean isSignalled() {
				return false;
			}
		};
	}

	@Override
	public @NotNull CompilerInputMaster createCompilerInputMaster() {
		return new CompilerInputMaster() {
			private final List<CompilerInputListener> listeners = new ArrayList<>();

			@Override
			public void addListener(final CompilerInputListener compilerInputListener) {
				listeners.add(compilerInputListener);
			}

			@Override
			public void notifyChange(final CompilerInput compilerInput, final CompilerInput.CompilerInputField compilerInputField) {
				for (CompilerInputListener listener : listeners) {
					listener.baseNotify(compilerInput, compilerInputField);
				}
			}
		};
	}

	@Override
	public EOT_OutputTree createOutputTree() {
		return new EOT_OutputTreeImpl();
	}

	@Override
	public EIT_InputTree createInputTree() {
		return new EIT_InputTreeImpl();
	}

	@Override
	public CX_ParseElijahFile.ElijahSpecReader defaultElijahSpecReader(final CP_Path aLocalPrelude) {
		return new EDL_ElijahSpecReader(aLocalPrelude, compilation);
	}

	@NotNull
	@Override
	public CK_Monitor createCkMonitor() {
		return new EDL_Compilation.__CK_Monitor();
	}

	@NotNull
	@Override
	public PW_CompilerController createPwController(EDL_Compilation aCompilation) {
		return new PW_CompilerController(aCompilation);
	}

	@NotNull
	@Override
	public EDL_Finally createFinally() {
		return new EDL_Finally();
	}

	@NotNull
	@Override
	public LivingRepo getLivingRepo() {
		return new DefaultLivingRepo();
	}

	@Override
	public CM_UleLog getULog() {
		if (_log == null) {
			_log = new CM_UleLog() {
				@Override
				public void info(String string) {
					SimplePrintLoggerToRemoveSoon.println2(string);
				}

				@Override
				public void asv(final int code, final Object... aObjects) {
					SimplePrintLoggerToRemoveSoon.println2("{{ULE}} "+code+" "+Arrays.asList(aObjects));
				}
			};
		}
		return _log;
	}

	@Override
	public ILazyCompilerInstructions createLazyCompilerInstructions(final CompilerInput aCompilerInput) {
		return ILazyCompilerInstructions_.of(aCompilerInput, compilation.getCompilationClosure());
	}
}
