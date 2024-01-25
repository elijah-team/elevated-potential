package tripleo.elijah.comp.impl;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.util.*;

import java.util.*;
import java.util.function.*;
import tripleo.wrap.File;

import static tripleo.elijah.nextgen.inputtree.EIT_InputType.EZ_FILE;

public class CW_ezDirRequest {
//	public static void apply() {
//	}

	public static List<Operation2<CompilerInstructions>> apply(String[] files,
	                                                           File directory,
	                                                           LibraryStatementPart ignoredALsp,
	                                                           Function<File, CompilerInstructions> parseEzFile,
	                                                           CompilationClosure cc,
	                                                           QSEZ_Reasoning aReasoning) {
		Compilation c       = (Compilation) cc.getCompilation();
		ErrSink      errSink = cc.errSink();
		List<Operation2<CompilerInstructions>> R       = new ArrayList<>();

		for (final String file_name : files) {
			try {
				final File                 file   = new File(directory, file_name);
				final CompilerInstructions ezFile = parseEzFile.apply(file);
				if (ezFile != null) {
					R.add(Operation2.success(ezFile));

					c.getObjectTree().asseverate(ezFile, Asseverate.EZ_PARSED);
					c.reports().addCodeInput(() -> file_name, EZ_FILE);
				} else {
					R.add(Operation2.failure(new QuerySearchEzFiles.Diagnostic_9995(file)));
					errSink.reportError("9995 ezFile is null " + file); // TODO Diagnostic
				}
			} catch (final Exception e) {
				R.add(Operation2.failure(new ExceptionDiagnostic(e)));
			}
		}
		return R;
	}
}
