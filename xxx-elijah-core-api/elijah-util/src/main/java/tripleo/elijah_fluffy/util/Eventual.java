package tripleo.elijah_fluffy.util;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.diagnostic.ExceptionDiagnostic;

public class Eventual<P> {
	private final DeferredObject<P, Diagnostic, Void> prom = new DeferredObject<>();

	/**
	 * Please overload this
	 */
	public String description() {
		return "GENERIC-DESCRIPTION";
	}

	public void fail(final Diagnostic d) {
		prom.reject(d);
	}

	public boolean isResolved() {
		return prom.isResolved();
	}

	public void register(final @NotNull EventualRegister ev) {
		ev.register(this);
	}

	public void resolve(final P p) {
		prom.resolve(p);
	}

	public void then(final DoneCallback<? super P> cb) {
		prom.then(cb);
	}

	public boolean _prom_isRejected() {
		return prom.isRejected();
	}

	public void reject(Diagnostic aDiagnostic) {
		prom.reject(aDiagnostic);
	}

	public boolean isPending() {
		return prom.isPending();
	}

	public void reject(final Exception aFailure) {
		final Diagnostic x = new ExceptionDiagnostic(aFailure);
		reject(x);
	}

	public void onFail(final FailCallback<Diagnostic> cb) {
		prom.fail(cb);
	}
}
