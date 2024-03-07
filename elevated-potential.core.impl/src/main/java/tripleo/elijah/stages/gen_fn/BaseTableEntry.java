package tripleo.elijah.stages.gen_fn;

import org.jdeferred2.Deferred;
import org.jdeferred2.DoneCallback;
import org.jdeferred2.FailCallback;
import org.jdeferred2.Promise;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.OS_Element;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.util.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 2/4/21 10:11 PM
 */
public abstract class BaseTableEntry {
	public enum Status {
		KNOWN, UNCHECKED, UNKNOWN
	}

	@FunctionalInterface
	public interface StatusListener {
		void onChange(IElementHolder eh, Status newStatus);
	}

	protected final DeferredObject2<OS_Element, Diagnostic, Void> _p_elementPromise = new DeferredObject2<OS_Element, Diagnostic, Void>() {
		@Override
		public Deferred<OS_Element, Diagnostic, Void> resolve(final @Nullable OS_Element resolve) {
			if (resolve == null) {
				if (BaseTableEntry.this instanceof final VariableTableEntry vte) {
					switch (vte.getVtt()) {
					case SELF, TEMP, RESULT -> {
						return super.resolve(resolve); // TODO 24/03/07 resolver, tomasetti
					}
					}
				}
				NotImplementedException.raise();
			}
			return super.resolve(resolve);
		}
	};

	private final List<StatusListener> statusListenerList = new ArrayList<>();
	public        BaseEvaFunction      __gf;
	protected     DeduceTypes2         __dt2;

	// region resolved_element

	// region status
	protected Status status = Status.UNCHECKED;

	DeduceTypeResolve typeResolve;

	public void _fix_table(final DeduceTypes2 aDeduceTypes2, final @NotNull BaseEvaFunction aEvaFunction) {
		__dt2 = aDeduceTypes2;
		__gf  = aEvaFunction;
	}

	public void addStatusListener(final StatusListener sl) {
		statusListenerList.add(sl);
	}

	public void elementPromise(@Nullable final DoneCallback<OS_Element> dc, @Nullable final FailCallback<Diagnostic> fc) {
		if (dc != null)
			_p_elementPromise.then(dc);
		if (fc != null)
			_p_elementPromise.fail(fc);
	}

	public @Nullable OS_Element getResolvedElement() {
		if (_p_elementPromise.isResolved()) {
			//return EventualExtract.of(_p_elementPromise);
			final OS_Element[] xx = {null};

			_p_elementPromise.then(x -> xx[0] = x);

			return xx[0];
		}

		return null;
	}

	// endregion resolved_element

	public Status getStatus() {
		return status;
	}

	public void setResolvedElement(final OS_Element aResolved_element) {
		if (_p_elementPromise.isResolved()) {
			NotImplementedException.raise();
		} else {
			_p_elementPromise.resolve(aResolved_element);
		}
	}

	public void setStatus(final Status newStatus, /* @NotNull */ final IElementHolder eh) {
		status = newStatus;
		assert newStatus != Status.KNOWN || eh != null && eh.getElement() != null;
		for (int i = 0; i < statusListenerList.size(); i++) {
			final StatusListener statusListener = statusListenerList.get(i);
			statusListener.onChange(eh, newStatus);
		}
		if (newStatus == Status.UNKNOWN)
			if (!_p_elementPromise.isRejected())
				_p_elementPromise.reject(new ResolveUnknown());
	}

	protected void setupResolve() {
		assert __dt2 == null;
		typeResolve = new DeduceTypeResolve(this, new NULL_DeduceTypes2());
	}

	public void typeResolve(final GenType aGt) {
		typeResolve.typeResolve(aGt);
	}

	public Promise<GenType, ResolveError, Void> typeResolvePromise() {
		return typeResolve.typeResolution();
	}
}
