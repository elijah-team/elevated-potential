package tripleo.elijah_durable_elevated.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;

import tripleo.elijah_durable_elevated.elijah.comp.nextgen.CX_ParseElijahFile;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.util.Operation;

import java.io.FileNotFoundException;
import java.io.InputStream;

class DefaultElijahSpecReader implements CX_ParseElijahFile.ElijahSpecReader {
	private final CP_Path     local_prelude;
	private final Compilation c;

	public DefaultElijahSpecReader(final CP_Path aCPPath, final Compilation aC) {
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
