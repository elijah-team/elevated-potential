package tripleo.elijah_elevated_durable.comp;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.CompilerInstructionsObserver;
import tripleo.elijah_fluffy.util.*;

public class EDL_CIS implements Observer<CompilerInstructions> {
	private final ObservableCompletableProcess<CompilerInstructions> ocp_ci                      = new ObservableCompletableProcess<>();
	private final Subject<CompilerInstructions>                      compilerInstructionsSubject = ReplaySubject.<CompilerInstructions>create();
	private       IProgressSink                                      progressSink;
	private       CompilerInstructionsObserver                       _cio;

	public @NotNull Operation<Ok> almostComplete() {
		return _cio.almostComplete();
	}

	@Override
	public void onSubscribe(@NonNull final Disposable d) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.onSubscribe(d);
		} else {
			ocp_ci.onSubscribe(d);
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
	public void onError(@NonNull final Throwable e) {
		if (DebugFlags.CIS_ocp_ci__FeatureGate) {
			compilerInstructionsSubject.onError(e);
		} else {
			ocp_ci.onError(e);
		}
	}

	@Override
	public void onComplete() {
		throw new UnintendedUseException();
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

	public IProgressSink getProgressSink() {
		return progressSink;
	}

	public void setProgressSink(IProgressSink aProgressSink) {
		progressSink = aProgressSink;
	}
}
