package tripleo.elijah.comp.i;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.*;
import org.apache.commons.lang3.tuple.*;
import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.*;
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

public class CompilationEnclosure {
	public enum AssOutFile {
		CLASS, NAMESPACE, FUNCTION
	}

	public interface ModuleListener {
		void close();

		void listen(WorldModule module);
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

	public final DeferredObject<IPipelineAccess, Void, Void> pipelineAccessPromise = new DeferredObject<>();
	private final CB_Output _cbOutput = new CB_ListBackedOutput();
	private final Compilation compilation;
	private final DeferredObject<AccessBus, Void, Void> accessBusPromise = new DeferredObject<>();
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

	public CompilationEnclosure(final Compilation aCompilation) {
		compilation = aCompilation;

		getPipelineAccessPromise().then(pa -> {
			ab = new AccessBus(getCompilation(), pa);

			accessBusPromise.resolve(ab);

			ab.addPipelinePlugin(new CR_State.HooliganPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.EvaPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.DeducePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WritePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteMakefilePipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteMesonPipelinePlugin());
			ab.addPipelinePlugin(new CR_State.WriteOutputTreePipelinePlugin());

			pa._setAccessBus(ab);

			this.pa = pa;
		});

		compilation.world().addModuleProcess(new CompletableProcess<WorldModule>() {
			@Override
			public void add(final WorldModule item) {
				// TODO Reactive pattern (aka something ala ReplaySubject)
				for (final ModuleListener moduleListener : _moduleListeners) {
					moduleListener.listen(item);
				}
			}

			@Override
			public void complete() {
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

			}

			@Override
			public void start() {

			}
		});
	}

	private void _resolvePipelineAccess(final PipelineLogic aPipelineLogic) {
	}

	public void addEntryPoint(final @NotNull Mirror_EntryPoint aMirrorEntryPoint, final IClassGenerator dcg) {
		aMirrorEntryPoint.generate(dcg);
	}

	public void addModuleListener(final ModuleListener aModuleListener) {
		_moduleListeners.add(aModuleListener);
	}

	public @NotNull ModuleThing addModuleThing(final OS_Module aMod) {
		var mt = new ModuleThing(aMod);
		moduleThings.put(aMod, mt);
		return mt;
	}

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

	private void AssertOutFile_Class(final OutputStrategyC.OSC_NFC aNfc, final NG_OutputRequest aOutputRequest) {
		outFileAssertions.add(Triple.of(AssOutFile.CLASS, aNfc, aOutputRequest));
	}

	private void AssertOutFile_Function(final OutputStrategyC.OSC_NFF aNff, final NG_OutputRequest aOutputRequest) {
		outFileAssertions.add(Triple.of(AssOutFile.FUNCTION, aNff, aOutputRequest));
	}

	private void AssertOutFile_Namespace(final OutputStrategyC.OSC_NFN aNfn, final NG_OutputRequest aOutputRequest) {
		outFileAssertions.add(Triple.of(AssOutFile.NAMESPACE, aNfn, aOutputRequest));
	}

	public @NotNull Promise<AccessBus, Void, Void> getAccessBusPromise() {
		return accessBusPromise;
	}

	@Contract(pure = true)
	public CB_Output getCB_Output() {
		return this._cbOutput;
	}

	@Contract(pure = true)
	public Compilation getCompilation() {
		return compilation;
	}

	@Contract(pure = true)
	public @NotNull ICompilationAccess getCompilationAccess() {
		return ca;
	}

	@Contract(pure = true)
	public ICompilationBus getCompilationBus() {
		return compilationBus;
	}

	// @Contract(pure = true) //??
	public CompilationClosure getCompilationClosure() {
		return this.getCompilation().getCompilationClosure();
	}

	@Contract(pure = true)
	public CompilerDriver getCompilationDriver() {
		return getCompilationBus().getCompilerDriver();
	}

	@Contract(pure = true)
	public CompilationRunner getCompilationRunner() {
		return compilationRunner;
	}

	@Contract(pure = true)
	public List<CompilerInput> getCompilerInput() {
		return inp;
	}

	public ModuleThing getModuleThing(final OS_Module aMod) {
		if (moduleThings.containsKey(aMod)) {
			return moduleThings.get(aMod);
		}
		return addModuleThing(aMod);
	}

	@Contract(pure = true)
	public IPipelineAccess getPipelineAccess() {
		return pa;
	}

	@Contract(pure = true)
	public @NotNull Promise<IPipelineAccess, Void, Void> getPipelineAccessPromise() {
		return pipelineAccessPromise;
	}

	@Contract(pure = true)
	public PipelineLogic getPipelineLogic() {
		return pipelineLogic;
	}

	public void logProgress(final @NotNull CompProgress aCompProgress, final Object x) {
		aCompProgress.deprecated_print(x, System.out, System.err);
	}

	public void noteAccept(final @NotNull WorldModule aWorldModule) {
		var mod = aWorldModule.module();
		var aMt = aWorldModule.rq().mt();
		// System.err.println(mod);
		// System.err.println(aMt);
	}

	public @NonNull OFA OutputFileAsserts() {
		return ofa;
	}

	public void reactiveJoin(final Reactive aReactive) {
		// throw new IllegalStateException("Error");

		// aReactive.join();
		System.err.println("reactiveJoin " + aReactive.toString());
	}

	public void setCompilationAccess(@NotNull ICompilationAccess aca) {
		ca = aca;
	}

	public void setCompilationBus(final ICompilationBus aCompilationBus) {
		compilationBus = aCompilationBus;
	}

	public void setCompilationRunner(final CompilationRunner aCompilationRunner) {
		compilationRunner = aCompilationRunner;
	}

	public void setCompilerDriver(final CompilerDriver aCompilerDriver) {
		compilerDriver = aCompilerDriver;
	}

	public void setCompilerInput(final List<CompilerInput> aInputs) {
		inp = aInputs;
	}

	public void setPipelineLogic(final PipelineLogic aPipelineLogic) {
		pipelineLogic = aPipelineLogic;

		getPipelineAccessPromise().then(pa -> pa.resolvePipelinePromise(aPipelineLogic));
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
