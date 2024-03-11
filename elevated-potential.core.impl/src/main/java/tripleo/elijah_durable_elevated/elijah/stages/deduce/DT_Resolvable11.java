package tripleo.elijah_durable_elevated.elijah.stages.deduce;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;

public class DT_Resolvable11<T> {
	private final DeferredObject<T, ResolveError, Void> _p_res = new DeferredObject<>();

	public void reject(final ResolveError aResolveError) {
		_p_res.reject(aResolveError);
	}

	public void resolve(T t) {
		_p_res.resolve(t);
	}

	public void then(DoneCallback<T> then) {
		_p_res.then(then);
	}
}
