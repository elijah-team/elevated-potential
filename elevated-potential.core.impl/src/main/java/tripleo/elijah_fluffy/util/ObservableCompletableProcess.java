package tripleo.elijah_fluffy.util;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_fluffy.diagnostic.ExceptionDiagnostic;

public class ObservableCompletableProcess<T> implements Observer<T> {
	// private CompletableProcess<T> cpt;
	private final Subject<T> subject = ReplaySubject.<T>create();

	public void almostComplete() {
		// cpt.preComplete();
	}

	@Override
	public void onComplete() {
		// cpt.complete();
 		subject.onComplete();
	}

	@Override
	public void onError(@NonNull final Throwable e) {
		subject.onError(e);
		// cpt.error(new ExceptionDiagnostic((Exception) e));
	}

	@Override
	public void onNext(@NonNull final T aT) {
		subject.onNext(aT);
		// cpt.add(aT);
	}

	@Override
	public void onSubscribe(@NonNull final Disposable d) {
		subject.onSubscribe(d);
		// cpt.start();
	}

	public void subscribe(final @NotNull CompletableProcess<T> cp) {
		subject.subscribe(new Observer<T>() {
			@Override
			public void onComplete() {
				cp.preComplete();
				cp.complete();
			}

			@Override
			public void onError(@NonNull final Throwable e) {
				cp.error(new ExceptionDiagnostic((Exception) e));
			}

			@Override
			public void onNext(@NonNull final T aT) {
				cp.add(aT);
			}

			@Override
			public void onSubscribe(@NonNull final Disposable d) {
				cp.start();
			}
		});
	}

	public void subscribe(final @NotNull Observer<T> aCio) {
		subject.subscribe(aCio);
	}
}
