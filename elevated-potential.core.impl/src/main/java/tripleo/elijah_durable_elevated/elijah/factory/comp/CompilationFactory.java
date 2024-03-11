package tripleo.elijah_durable_elevated.elijah.factory.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah_durable_elevated.elijah.comp.IO_;
import tripleo.elijah_durable_elevated.elijah.comp.StdErrSink;
import tripleo.elijah_durable_elevated.elijah.comp.internal.CompilationImpl;
import tripleo.elijah_durable_elevated.elijah.stages.deduce.IFunctionMapHook;

import java.util.List;

public enum CompilationFactory {
	;

	@Contract("_, _ -> new")
	public static @NotNull CompilationImpl mkCompilation0() {
		return mkCompilation(new StdErrSink(), new IO_());
	}

	@Contract("_, _ -> new")
	public static @NotNull CompilationImpl mkCompilation(final ErrSink eee, final IO io) {
		return new CompilationImpl(eee, io);
	}

	public static @NotNull CompilationImpl mkCompilation2(final List<IFunctionMapHook> aMapHooks) {
		final StdErrSink errSink = new StdErrSink();
		final IO io = new IO_();

		final @NotNull CompilationImpl c = mkCompilation(errSink, io);

		c.testMapHooks(aMapHooks);

		return c;
	}
}
