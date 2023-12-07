package tripleo.elijah.comp.impl;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.*;
import org.apache.commons.lang3.tuple.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.pre_world.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.generate.*;
import tripleo.elijah.stages.inter.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.stages.write_stage.pipeline_impl.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.*;
import java.util.function.*;

public class DefaultCompilationEnclosure implements CompilationEnclosure {
	public final           Eventual<IPipelineAccess>                                                       pipelineAccessPromise = new Eventual<>();
	private final          Eventual<CompilationRunner>                                                     ecr                   = new Eventual<>();
	private final          Eventual<AccessBus>                                                             accessBusPromise      = new Eventual<>();
	private final          Compilation                                                                     compilation;
	private final          CB_Output                                                                       _cbOutput             = new CB_ListBackedOutput();
	private final          Map<String, PipelinePlugin>                                                     pipelinePlugins       = new HashMap<>();
	private final          Map<OS_Module, ModuleThing>                                                     moduleThings          = new HashMap<>();
	private final          Subject<ReactiveDimension>                                                      dimensionSubject      = ReplaySubject.<ReactiveDimension>create();
	private final          Subject<Reactivable>                                                            reactivableSubject    = ReplaySubject.<Reactivable>create();
	private final @NonNull List<ElLog>                                                                     elLogs                = new LinkedList<>();
	private final          List<ModuleListener>                                             _moduleListeners  = new ArrayList<>();
	private final          List<Triple<AssOutFile, EOT_FileNameProvider, NG_OutputRequest>> outFileAssertions = new ArrayList<>();
	private final @NonNull OFA                                                              ofa               = new OFA(/* outFileAssertions */);
	Observer<ReactiveDimension> dimensionObserver   = new Observer<ReactiveDimension>() {
		@Override
		public void onSubscribe(@NonNull final Disposable d) {
			throw new UnintendedUseException();
		}

		@Override
		public void onNext(@NonNull final ReactiveDimension aReactiveDimension) {
			// aReactiveDimension.observe();
			throw new UnintendedUseException();
		}

		@Override
		public void onError(@NonNull final Throwable e) {
			throw new UnintendedUseException();
		}

		@Override
		public void onComplete() {
			throw new UnintendedUseException();
		}
	};
	Observer<Reactivable>       reactivableObserver = new Observer<Reactivable>() {

		@Override
		public void onSubscribe(@NonNull final Disposable d) {
			throw new UnintendedUseException();
		}

		@Override
		public void onNext(@NonNull final Reactivable aReactivable) {
//			ReplaySubject
			throw new UnintendedUseException();
		}

		@Override
		public void onError(@NonNull final Throwable e) {
			throw new UnintendedUseException();
		}

		@Override
		public void onComplete() {
			throw new UnintendedUseException();
		}
	};
	private AccessBus           ab;
	private ICompilationAccess  ca;
	private ICompilationBus     compilationBus;
	private CompilationRunner   compilationRunner;
	private CompilerDriver      compilerDriver;
	private List<CompilerInput> inp;
	private IPipelineAccess     pa;
	private PipelineLogic       pipelineLogic;

	public DefaultCompilationEnclosure(final Compilation aCompilation) {
		compilation = aCompilation;

		getPipelineAccessPromise().then(pa -> {
			final CompilationEnclosure ce = compilation.getCompilationEnclosure();

			ab = new AccessBus(getCompilation(), pa);

			accessBusPromise.resolve(ab);

			// README these need pipeline access to be created. wanted ce, but went with that
			ce.addPipelinePlugin(new __Plugins.LawabidingcitizenPipelinePlugin());
			ce.addPipelinePlugin(new __Plugins.EvaPipelinePlugin());
			ce.addPipelinePlugin(new __Plugins.DeducePipelinePlugin());
			ce.addPipelinePlugin(new __Plugins.WritePipelinePlugin());
			ce.addPipelinePlugin(new __Plugins.WriteMakefilePipelinePlugin());
			ce.addPipelinePlugin(new __Plugins.WriteMesonPipelinePlugin());
			ce.addPipelinePlugin(new __Plugins.WriteOutputTreePipelinePlugin());

			pa._setAccessBus(ab);

			this.pa = pa;
		});

		compilation.world().addModuleProcess(new ModuleListener_ModuleCompletableProcess());
	}

	//	@Override
	@Override
	public void addEntryPoint(final @NotNull Mirror_EntryPoint aMirrorEntryPoint, final IClassGenerator dcg) {
		aMirrorEntryPoint.generate(dcg);
	}

	@Override
	public void addModuleListener(final ModuleListener aModuleListener) {
		_moduleListeners.add(aModuleListener);
	}

	@Override
	public @NotNull ModuleThing addModuleThing(final OS_Module aMod) {
		var mt = new ModuleThing(aMod);
		moduleThings.put(aMod, mt);
		return mt;
	}

	@Override
	public void addReactive(@NotNull Reactivable r) {
		int y = 2;
		// reactivableObserver.onNext(r);
		reactivableSubject.onNext(r);

		// reactivableObserver.
		dimensionSubject.subscribe(new Observer<ReactiveDimension>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}

			@Override
			public void onNext(final ReactiveDimension aReactiveDimension) {
				// r.join(aReactiveDimension);
				r.respondTo(aReactiveDimension);
			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {

			}
		});
	}

	@Override
	public void addReactive(@NotNull Reactive r) {
		dimensionSubject.subscribe(new Observer<ReactiveDimension>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}

			@Override
			public void onNext(@NonNull ReactiveDimension dim) {
				r.join(dim);
			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {

			}
		});
	}

	@Override
	public void addReactiveDimension(final ReactiveDimension aReactiveDimension) {
		dimensionSubject.onNext(aReactiveDimension);

		reactivableSubject.subscribe(new Observer<Reactivable>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}

			@Override
			public void onNext(@NonNull final @NotNull Reactivable aReactivable) {
				addReactive(aReactivable);
			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onComplete() {

			}
		});

//		aReactiveDimension.setReactiveSink(addReactive);
	}

	@Override
	public void AssertOutFile(final @NotNull NG_OutputRequest aOutputRequest) {
		var fileName = aOutputRequest.fileName();
		if (fileName instanceof OutputStrategyC.OSC_NFC nfc) {
			AssertOutFile_Class(nfc, aOutputRequest);
		} else if (fileName instanceof OutputStrategyC.OSC_NFF nff) {
			AssertOutFile_Function(nff, aOutputRequest);
		} else if (fileName instanceof OutputStrategyC.OSC_NFN nfn) {
			AssertOutFile_Namespace(nfn, aOutputRequest);
		} else {
			throw new NotImplementedException();
		}
	}

	@Override
	public @NotNull Eventual<AccessBus> getAccessBusPromise() {
		return accessBusPromise;
	}

	@Override
	@Contract(pure = true)
	public CB_Output getCB_Output() {
		return this._cbOutput;
	}

	@Override
	@Contract(pure = true)
	public Compilation getCompilation() {
		return compilation;
	}

	@Override
	@Contract(pure = true)
	public @NotNull ICompilationAccess getCompilationAccess() {
		return ca;
	}

	@Override
	public void setCompilationAccess(@NotNull ICompilationAccess aca) {
		ca = aca;
	}

	// @Contract(pure = true) //??

	@Override
	@Contract(pure = true)
	public ICompilationBus getCompilationBus() {
		return compilationBus;
	}

	@Override
	public void setCompilationBus(final ICompilationBus aCompilationBus) {
		compilationBus = aCompilationBus;
	}

	@Override
	public CompilationClosure getCompilationClosure() {
		return this.getCompilation().getCompilationClosure();
	}

	@Contract(pure = true)
	@Override
	public CompilerDriver getCompilationDriver() {
		return getCompilationBus().getCompilerDriver();
	}

	@Contract(pure = true)
	@Override
	public CompilationRunner getCompilationRunner() {
		return compilationRunner;
	}

	@Override
	public void setCompilationRunner(final CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;

		if (ecr.isPending()) {
			ecr.resolve(compilationRunner);
		} else {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("903365 compilationRunner already set");
		}
	}

	@Contract(pure = true)
	@Override
	public List<CompilerInput> getCompilerInput() {
		return inp;
	}

	@Override
	public void setCompilerInput(final List<CompilerInput> aInputs) {
		//assert inp == null;

		inp = aInputs;
	}

	@Override
	public ModuleThing getModuleThing(final OS_Module aMod) {
		if (moduleThings.containsKey(aMod)) {
			return moduleThings.get(aMod);
		}
		return addModuleThing(aMod);
	}

	@Contract(pure = true)
	@Override
	public IPipelineAccess getPipelineAccess() {
		return pa;
	}

	@Contract(pure = true)
	@Override
	public @NotNull Eventual<IPipelineAccess> getPipelineAccessPromise() {
		return pipelineAccessPromise;
	}

	@Contract(pure = true)
	@Override
	public PipelineLogic getPipelineLogic() {
		return pipelineLogic;
	}

	@Override
	public void setPipelineLogic(final PipelineLogic aPipelineLogic) {
		pipelineLogic = aPipelineLogic;

		getPipelineAccessPromise().then(pa -> pa.resolvePipelinePromise(aPipelineLogic));
	}

	@Override
	public void logProgress(final @NotNull CompProgress aCompProgress, final Object x) {
		aCompProgress.deprecated_print(x, System.out, System.err);
	}

	@Override
	public void noteAccept(final @NotNull WorldModule aWorldModule) {
		//var mod = aWorldModule.module();
		//var aMt = EventualExtract.of(aWorldModule.getErq()).mt();
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(mod);
		// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(aMt);
	}

	@Override
	public @NonNull OFA OutputFileAsserts() {
		return ofa;
	}

	@Override
	public void reactiveJoin(final Reactive aReactive) {
		// throw new IllegalStateException("Error");

		// aReactive.join();
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("reactiveJoin " + aReactive.toString());
	}

	@Override
	public void setCompilerDriver(final CompilerDriver aCompilerDriver) {
		compilerDriver = aCompilerDriver;
	}

	@Override
	public void AssertOutFile_Class(OutputStrategyC.OSC_NFC aNfc, NG_OutputRequest aOutputRequest) {
		outFileAssertions.add(Triple.of(AssOutFile.CLASS, aNfc, aOutputRequest));
	}

	@Override
	public void AssertOutFile_Function(OutputStrategyC.OSC_NFF aNff, NG_OutputRequest aOutputRequest) {
		outFileAssertions.add(Triple.of(AssOutFile.FUNCTION, aNff, aOutputRequest));
	}

	@Override
	public void AssertOutFile_Namespace(OutputStrategyC.OSC_NFN aNfn, NG_OutputRequest aOutputRequest) {
		outFileAssertions.add(Triple.of(AssOutFile.NAMESPACE, aNfn, aOutputRequest));
	}

	@Override
	public void _resolvePipelineAccessPromise(IPipelineAccess aPa) {
		if (!pipelineAccessPromise.isResolved()) { // FIXME 10/18 ugh
			pipelineAccessPromise.resolve(aPa);
		}
	}

	@Override
	public void waitCompilationRunner(Consumer<CompilationRunner> ccr) {
		ecr.then(ccr::accept);
	}

	@Override
	public void logProgress2(final CompProgress aCompProgress, final AsseverationLogProgress alp) {
		alp.call(System.out, System.err);
	}

	@Override
	public CK_Monitor getDefaultMonitor() {
		return ((CompilationImpl) compilation).getDefaultMonitor();
	}

	@Override
	public void runStepsNow(final CK_Steps aSteps, final CK_AbstractStepsContext aStepContext) {
		final CK_Monitor monitor = getDefaultMonitor();
		CK_DefaultStepRunner.runStepsNow(aSteps, aStepContext, monitor);
	}

	@Override
	public PipelinePlugin getPipelinePlugin(final String aPipelineName) {
		if (!(pipelinePlugins.containsKey(aPipelineName))) {
			return null;
		}

		return pipelinePlugins.get(aPipelineName);
	}

	@Override
	public void addPipelinePlugin(final @NotNull Function<GPipelineAccess, PipelineMember> aCr) {
		pipelineAccessPromise.then(pa -> {
			final PipelinePlugin plugin = (PipelinePlugin) aCr.apply(pa);
			pipelinePlugins.put(plugin.name(), plugin);
		});
	}

	@Override
	public void addPipelinePlugin(final @NotNull PipelinePlugin aPlugin) {
		pipelinePlugins.put(aPlugin.name(), aPlugin);
	}

	@Override
	public void writeLogs() {
		final PipelineLogic pipelineLogic = this.getPipelineLogic();
		final GN_WriteLogs  notable       = new GN_WriteLogs(this.getCompilationAccess(), pipelineLogic._pa().getCompilationEnclosure().getLogs());

		pa.notate(Provenance.DefaultCompilationAccess__writeLogs, notable);
	}

	@Override
	public void addLog(final ElLog aLOG) {
		elLogs.add(aLOG);
	}

	@Override
	public List<ElLog> getLogs() {
		return elLogs;
	}

	@Override
	public EIT_ModuleList getModuleList() {
		return compilation.getObjectTree().getModuleList();
	}

	@Override
	public GModuleThing addModuleThing(final GOS_Module aModule) {
		return addModuleThing((OS_Module) aModule);
	}

	@Override
	public void logProgress(final CompProgress aCompProgress, final Pair<Integer, String> aCodeMessagePair) {
		aCompProgress.deprecated_print(aCodeMessagePair, System.out, System.err);
	}

	@Override
	public GModuleThing getModuleThing(final GOS_Module aModule) {
		return getModuleThing((OS_Module) aModule);
	}

	private class ModuleListener_ModuleCompletableProcess implements CompletableProcess<WorldModule> {

		@Override
		public void add(final WorldModule item) {
//			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("[ModuleListener_ModuleCompletableProcess] add " + item.module().getFileName());

			// TODO Reactive pattern (aka something ala ReplaySubject)
			for (final ModuleListener moduleListener : _moduleListeners) {
				moduleListener.listen(item);
			}
		}

		@Override
		public void complete() {
			// 09/26 tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("[ModuleListener_ModuleCompletableProcess] complete");

			// TODO Reactive pattern (aka something ala ReplaySubject)
			for (final ModuleListener moduleListener : _moduleListeners) {
				moduleListener.close();
			}
		}

		@Override
		public void error(final Diagnostic d) {

		}

		@Override
		public void preComplete() {
//			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("[ModuleListener_ModuleCompletableProcess] preComplete");
		}

		@Override
		public void start() {
//			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("[ModuleListener_ModuleCompletableProcess] start");
		}

	}

	public class OFA implements Iterable<Triple<AssOutFile, EOT_FileNameProvider, NG_OutputRequest>> {

		// public OFA(final List<Triple<AssOutFile, EOT_OutputFile.FileNameProvider,
		// NG_OutputRequest>> aOutFileAssertions) {
		// _l = aOutFileAssertions;
		// }

		public boolean contains(String aFileName) {
			for (Triple<AssOutFile, EOT_FileNameProvider, NG_OutputRequest> outFileAssertion : outFileAssertions) {
				final String containedFilename = outFileAssertion.getMiddle().getFilename();

				if (containedFilename.equals(aFileName)) {
					return true;
				}
			}

			return false;
		}

		@Override
		public Iterator<Triple<AssOutFile, EOT_FileNameProvider, NG_OutputRequest>> iterator() {
			return outFileAssertions.stream().iterator();
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
