package tripleo.elijah.stages.deduce;

import java.util.*;

import org.jetbrains.annotations.*;
import org.jetbrains.annotations.Nullable;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.*;
import io.reactivex.rxjava3.subjects.*;
import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.nextgen.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.work.*;

class Dependencies {
	private final DeduceTypes2 deduceTypes2;
	final WorkList wl;
	final WorkManager wm;

	Dependencies(final DeduceTypes2 aDeduceTypes2, final WorkManager aWm) {
		deduceTypes2 = aDeduceTypes2;
		wm = aWm;
		wl = deduceTypes2._inj().new_WorkList();
	}

	public void subscribeFunctions(final @NotNull Subject<FunctionInvocation> aDependentFunctionSubject) {
		aDependentFunctionSubject.subscribe(new Observer<FunctionInvocation>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {

			}

			@Override
			public void onNext(@NonNull final @NotNull FunctionInvocation aFunctionInvocation) {
				action_function(aFunctionInvocation);
			}

			@Override
			public void onError(@NonNull final Throwable e) {

			}

			@Override
			public void onComplete() {

			}
		});
	}

	public void action_function(@NotNull FunctionInvocation aDependentFunction) {
		final FunctionDef function = aDependentFunction.getFunction();
		WorkJob gen;
		final @NotNull OS_Module mod;
		if (function == LangGlobals.defaultVirtualCtor) {
			ClassInvocation ci = aDependentFunction.getClassInvocation();
			if (ci == null) {
				NamespaceInvocation ni = aDependentFunction.getNamespaceInvocation();
				assert ni != null;
				mod = ni.getNamespace().getContext().module();

				ni.resolvePromise().then(result -> result.dependentFunctions().add(aDependentFunction));
			} else {
				mod = ci.getKlass().getContext().module();
				ci.resolvePromise().then(result -> result.dependentFunctions().add(aDependentFunction));
			}
			final @NotNull GenerateFunctions gf = deduceTypes2.getGenerateFunctions(mod);
			gen = deduceTypes2._inj().new_WlGenerateDefaultCtor(gf, aDependentFunction, deduceTypes2.creationContext(),
					deduceTypes2.phase.getCodeRegistrar());
		} else {
			mod = function.getContext().module();
			final @NotNull GenerateFunctions gf = deduceTypes2.getGenerateFunctions(mod);
			gen = deduceTypes2._inj().new_WlGenerateFunction(gf, aDependentFunction,
					deduceTypes2.phase.getCodeRegistrar());
		}
		wl.addJob(gen);
		List<BaseEvaFunction> coll = new ArrayList<>();
		wl.addJob(deduceTypes2._inj().new_WlDeduceFunction(gen, coll, deduceTypes2));
		wm.addJobs(wl);
	}

	public void subscribeTypes(final @NotNull Subject<GenType> aDependentTypesSubject) {
		aDependentTypesSubject.subscribe(new Observer<GenType>() {
			@Override
			public void onSubscribe(@NonNull final Disposable d) {
			}

			@Override
			public void onNext(final @NotNull GenType aGenType) {
				action_type(aGenType);
			}

			@Override
			public void onError(final Throwable aThrowable) {
			}

			@Override
			public void onComplete() {
			}
		});
	}

	public void action_type(@NotNull GenType genType) {
		// TODO work this out further, maybe like a Deepin flavor
		if (genType.getResolvedn() != null) {
			@NotNull
			OS_Module mod = genType.getResolvedn().getContext().module();
			final @NotNull GenerateFunctions gf = deduceTypes2.phase.generatePhase.getGenerateFunctions(mod);
			NamespaceInvocation ni = deduceTypes2.phase.registerNamespaceInvocation(genType.getResolvedn());
			@NotNull
			WlGenerateNamespace gen = deduceTypes2._inj().new_WlGenerateNamespace(gf, ni,
					deduceTypes2.phase.generatedClasses,
					deduceTypes2.phase.getCodeRegistrar());

			assert genType.getCi() == null || genType.getCi() == ni;
			genType.setCi(ni);

			wl.addJob(gen);

			ni.resolvePromise().then(result -> {
				genType.setNode(result);
				result.dependentTypes().add(genType);
			});
		} else if (genType.getResolved() != null) {
			if (genType.getFunctionInvocation() != null) {
				action_function(genType.getFunctionInvocation());
				return;
			}

			final ClassStatement c = genType.getResolved().getClassOf();
			final @NotNull OS_Module mod = c.getContext().module();
			final @NotNull GenerateFunctions gf = deduceTypes2.phase.generatePhase.getGenerateFunctions(mod);
			@Nullable
			ClassInvocation ci;
			if (genType.getCi() == null) {
				ci = deduceTypes2._inj().new_ClassInvocation(c, null, new ReadySupplier_1<>(deduceTypes2));
				ci = deduceTypes2.phase.registerClassInvocation(ci);

				genType.setCi(ci);
			} else {
				assert genType.getCi() instanceof ClassInvocation;
				ci = (ClassInvocation) genType.getCi();
			}

			final Eventual<ClassDefinition> pcd = deduceTypes2.phase.generateClass2(gf, ci, wm);

			pcd.then(result -> {
				final EvaClass genclass = (EvaClass) result.getNode();

				genType.setNode(genclass);
				genclass.dependentTypes().add(genType);
			});
		}
		//
		wm.addJobs(wl);
	}
}
