package tripleo.elijah.comp.impl;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.*;
import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.pre_world.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.generate.*;
import tripleo.elijah.stages.inter.*;
import tripleo.elijah.stages.write_stage.pipeline_impl.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.*;
import java.util.function.*;

public class DefaultCompilationEnclosure implements CompilationEnclosure {
	public final DeferredObject<IPipelineAccess, Void, Void> pipelineAccessPromise = new DeferredObject<>();

	private final Eventual<CompilationRunner> ecr = new Eventual<>();
	private final DeferredObject<AccessBus, Void, Void> accessBusPromise = new DeferredObject<>();
	private final CB_Output _cbOutput = new CB_ListBackedOutput();
	private final Compilation compilation;
	private final Map<OS_Module, ModuleThing> moduleThings = new HashMap<>();
	private final Subject<ReactiveDimension> dimensionSubject = ReplaySubject.<ReactiveDimension>create();
	private final Subject<Reactivable> reactivableSubject = ReplaySubject.<Reactivable>create();
	private final List<ModuleListener> _moduleListeners = new ArrayList<>();
	Observer<ReactiveDimension> dimensionObserver = new Observer<ReactiveDimension>() {
		@Override
		public void onComplete() {

		}

		@Override
		public void onError(@NonNull final Throwable e) {

		}

		@Override
		public void onNext(@NonNull final ReactiveDimension aReactiveDimension) {
			// aReactiveDimension.observe();
			throw new IllegalStateException("Error");
		}

		@Override
		public void onSubscribe(@NonNull final Disposable d) {

		}
	};
	Observer<Reactivable> reactivableObserver = new Observer<Reactivable>() {

		@Override
		public void onComplete() {

		}

		@Override
		public void onError(@NonNull final Throwable e) {

		}

		@Override
		public void onNext(@NonNull final Reactivable aReactivable) {
//			ReplaySubject
			throw new IllegalStateException("Error");
		}

		@Override
		public void onSubscribe(@NonNull final Disposable d) {

		}
	};
	private AccessBus ab;
	private ICompilationAccess ca;
	private ICompilationBus compilationBus;
	private CompilationRunner compilationRunner;
	private CompilerDriver compilerDriver;
	private List<CompilerInput> inp;

	private IPipelineAccess pa;

	private PipelineLogic pipelineLogic;

	private final List<Triple<AssOutFile, EOT_OutputFile.FileNameProvider, NG_OutputRequest>> outFileAssertions = new ArrayList<>();

	private final @NonNull OFA ofa = new OFA(/* outFileAssertions */);

	public DefaultCompilationEnclosure(final Compilation aCompilation) {
		compilation = aCompilation;

		getPipelineAccessPromise().then(pa -> {
			ab = new AccessBus(getCompilation(), pa);

			accessBusPromise.resolve(ab);

			ab.addPipelinePlugin(new CR_State.LawabidingcitizenPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.EvaPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.DeducePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WritePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteMakefilePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteMesonPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteOutputTreePipelinePlugin());

			pa._setAccessBus(ab);

			this.pa = pa;
		});

		compilation.world().addModuleProcess(new ModuleListener_ModuleCompletableProcess());
	}

//	@Override
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
			public void onComplete() {

			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(final ReactiveDimension aReactiveDimension) {
				// r.join(aReactiveDimension);
				r.respondTo(aReactiveDimension);
			}

			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}
		});
	}

	@Override
	public void addReactive(@NotNull Reactive r) {
		dimensionSubject.subscribe(new Observer<ReactiveDimension>() {
			@Override
			public void onComplete() {

			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(@NonNull ReactiveDimension dim) {
				r.join(dim);
			}

			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}
		});
	}

	@Override
	public void addReactiveDimension(final ReactiveDimension aReactiveDimension) {
		dimensionSubject.onNext(aReactiveDimension);

		reactivableSubject.subscribe(new Observer<Reactivable>() {
			@Override
			public void onComplete() {

			}

			@Override
			public void onError(@NonNull final @NotNull Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(@NonNull final @NotNull Reactivable aReactivable) {
				addReactive(aReactivable);
			}

			@Override
			public void onSubscribe(@NonNull final Disposable d) {

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
	public @NotNull Promise<AccessBus, Void, Void> getAccessBusPromise() {
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
	@Contract(pure = true)
	public ICompilationBus getCompilationBus() {
		return compilationBus;
	}

	// @Contract(pure = true) //??

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

	@Contract(pure = true)
	@Override
	public List<CompilerInput> getCompilerInput() {
		return inp;
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
	public @NotNull Promise<IPipelineAccess, Void, Void> getPipelineAccessPromise() {
		return pipelineAccessPromise;
	}

	@Contract(pure = true)
	@Override
	public PipelineLogic getPipelineLogic() {
		return pipelineLogic;
	}

	@Override
	public void logProgress(final @NotNull CompProgress aCompProgress, final Object x) {
		aCompProgress.deprecated_print(x, System.out, System.err);
	}

	@Override
	public void noteAccept(final @NotNull WorldModule aWorldModule) {
		var mod = aWorldModule.module();
		var aMt = aWorldModule.rq().mt();
		// System.err.println(mod);
		// System.err.println(aMt);
	}

	@Override
	public @NonNull OFA OutputFileAsserts() {
		return ofa;
	}

	@Override
	public void reactiveJoin(final Reactive aReactive) {
		// throw new IllegalStateException("Error");

		// aReactive.join();
		System.err.println("reactiveJoin " + aReactive.toString());
	}

	@Override
	public void setCompilationAccess(@NotNull ICompilationAccess aca) {
		ca = aca;
	}

	@Override
	public void setCompilationBus(final ICompilationBus aCompilationBus) {
		compilationBus = aCompilationBus;
	}

	@Override
	public void setCompilationRunner(final CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;

		ecr.resolve(compilationRunner);
	}

	@Override
	public void setCompilerDriver(final CompilerDriver aCompilerDriver) {
		compilerDriver = aCompilerDriver;
	}

	@Override
	public void setCompilerInput(final List<CompilerInput> aInputs) {
		assert inp == null;

		inp = aInputs;
	}

	@Override
	public void setPipelineLogic(final PipelineLogic aPipelineLogic) {
		pipelineLogic = aPipelineLogic;

		getPipelineAccessPromise().then(pa -> pa.resolvePipelinePromise(aPipelineLogic));
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
		pipelineAccessPromise.resolve(aPa);
	}

	@Override
	public void waitCompilationRunner(Consumer<CompilationRunner> ccr) {
		ecr.then(ccr::accept);
	}

	@Override
	public void logProgress2(final CompProgress aCompProgress, final AssererationLogProgress alp) {
		alp.call(System.out, System.err);
	}

	private class ModuleListener_ModuleCompletableProcess implements CompletableProcess<WorldModule> {

		@Override
		public void add(final WorldModule item) {
//			System.err.println("[ModuleListener_ModuleCompletableProcess] add " + item.module().getFileName());

			// TODO Reactive pattern (aka something ala ReplaySubject)
			for (final ModuleListener moduleListener : _moduleListeners) {
				moduleListener.listen(item);
			}
		}
		@Override
		public void complete() {
			// 09/26 System.err.println("[ModuleListener_ModuleCompletableProcess] complete");

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
//			System.err.println("[ModuleListener_ModuleCompletableProcess] preComplete");
		}

		@Override
		public void start() {
//			System.err.println("[ModuleListener_ModuleCompletableProcess] start");
		}

	}

	public class OFA implements Iterable<Triple<AssOutFile, EOT_OutputFile.FileNameProvider, NG_OutputRequest>> {

		// public OFA(final List<Triple<AssOutFile, EOT_OutputFile.FileNameProvider,
		// NG_OutputRequest>> aOutFileAssertions) {
		// _l = aOutFileAssertions;
		// }

		public boolean contains(String aFileName) {
			for (Triple<AssOutFile, EOT_OutputFile.FileNameProvider, NG_OutputRequest> outFileAssertion : outFileAssertions) {
				final String containedFilename = outFileAssertion.getMiddle().getFilename();

				if (containedFilename.equals(aFileName)) {
					return true;
				}
			}

			return false;
		}

		@Override
		public Iterator<Triple<AssOutFile, EOT_OutputFile.FileNameProvider, NG_OutputRequest>> iterator() {
			return outFileAssertions.stream().iterator();
		}
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
