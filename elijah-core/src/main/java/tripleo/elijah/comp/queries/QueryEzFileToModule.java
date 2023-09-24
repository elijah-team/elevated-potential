package tripleo.elijah.comp.queries;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.query.QueryDatabase;
import tripleo.elijah.util.*;

import java.io.*;

public class QueryEzFileToModule {
	public @NotNull Operation<CompilerInstructions> calculate(final QueryEzFileToModuleParams params) {
		final Compilation compilation = params.getCompilation();
		final File        file        = new File(params.sourceFilename);

		return CX_ParseEzFile.parseEzFile(file, compilation);
	}

	public OS_Module load(final QueryDatabase qb) {
		throw new NotImplementedException();
	}
}
