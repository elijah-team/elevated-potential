package tripleo.elijah_elevated_durable.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.util.Operation;
import tripleo.elijah_elevated.comp.input.CX_ParseElijahFile;
import tripleo.elijah_elevateder.comp.Compilation;

import java.io.FileNotFoundException;
import java.io.InputStream;

class EDL_ElijahSpecReader implements CX_ParseElijahFile.ElijahSpecReader {
	private final CP_Path     local_prelude;
	private final Compilation c;

	public EDL_ElijahSpecReader(final CP_Path aCPPath, final Compilation aC) {
		c             = aC;
		local_prelude = aCPPath;
	}

	@Override
	public @NotNull Operation<InputStream> get() {
		try {
			final InputStream readFile = local_prelude.getReadInputStream(c.getIO());
			return Operation.success(readFile);
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}
}
