package tripleo.elijah_elevated.comp;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.comp.impl.DefaultCompilationEnclosure;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.g.GCompilationEnclosure;
import tripleo.elijah.util.Operation;
import tripleo.wrap.File;

public class W {
	public static Operation<InputStream> ezSpec_SIS(File file, IO io) {
		try {
			return Operation.success(file.readFile(io));
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}

    @NotNull
    public static Supplier<ICompilationBus> extractCompilationBus(@NotNull ICompilationAccess aca) {
        return () -> {
			final GCompilationEnclosure gCompilationEnclosure  = aca.getCompilation().getCompilationEnclosure();
			final CompilationEnclosure  compilationEnclosure = (DefaultCompilationEnclosure) gCompilationEnclosure;
			final ICompilationBus       compilationBus        = compilationEnclosure.getCompilationBus();
			return compilationBus;
		};
    }
}
