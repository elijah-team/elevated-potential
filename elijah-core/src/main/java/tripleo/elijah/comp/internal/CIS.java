package tripleo.elijah.comp.internal;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.*;

public class CIS implements Observer<CompilerInstructions> {
	private final ObservableCompletableProcess<CompilerInstructions> ocp_ci = new ObservableCompletableProcess<>();
	private final Subject<CompilerInstructions> compilerInstructionsSubject = ReplaySubject.<CompilerInstructions>create();
	public        IProgressSink                 ps;
	private       CompilerInstructionsObserver _cio;

	public @NotNull Operation<Ok> almostComplete() {
		return _cio.almostComplete();
	}

	@Override
	public void onComplete() {
		throw new UnintendedUseException();
	}

	@Override
	public void onError(@NonNull final Throwable e) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.onError(e);
		} else {
			ocp_ci.onError(e);
		}
	}

	@Override
	public void onNext(@NonNull final CompilerInstructions aCompilerInstructions) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) { // l.add
			compilerInstructionsSubject.onNext(aCompilerInstructions);
		} else {
			ocp_ci.onNext(aCompilerInstructions);
		}
	}

	@Override
	public void onSubscribe(@NonNull final Disposable d) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.onSubscribe(d);
		} else {
			ocp_ci.onSubscribe(d);
		}
	}

	public void set_cio(CompilerInstructionsObserver a_cio) {
		_cio = a_cio;
	}

	public void subscribe(final @NotNull Observer<CompilerInstructions> aCio) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.subscribe(aCio);
		} else {
			ocp_ci.subscribe(aCio);
		}
	}

	public void subscribeTo(final Compilation aC) {
		aC.subscribeCI((Observer<CompilerInstructions>) _cio);
	}
}
