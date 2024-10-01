package tripleo.elijah_elevateder.factory.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah_elevated_durable.comp.EDL_IO;
import tripleo.elijah_elevated_durable.comp.EDL_ErrSink;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevateder.stages.deduce.IFunctionMapHook;

import java.util.List;

public enum CompilationFactory {
	;

	@Contract("_, _ -> new")
	public static @NotNull EDL_Compilation mkCompilation0() {
		return mkCompilation(new EDL_ErrSink(), new EDL_IO());
	}

	@Contract("_, _ -> new")
	public static @NotNull EDL_Compilation mkCompilation(final ErrSink eee, final IO io) {
		return new EDL_Compilation(eee, io);
	}

	public static @NotNull EDL_Compilation mkCompilation2(final List<IFunctionMapHook> aMapHooks) {
		final EDL_ErrSink errSink = new EDL_ErrSink();
		final IO          io      = new EDL_IO();

		final @NotNull EDL_Compilation c = mkCompilation(errSink, io);

		c.testMapHooks(aMapHooks);

		return c;
	}
}
