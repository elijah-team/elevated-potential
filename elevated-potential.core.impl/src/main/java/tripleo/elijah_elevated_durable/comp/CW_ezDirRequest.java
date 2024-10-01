package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.graph.i.Asseverate;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.queries.QSEZ_Reasoning;
import tripleo.elijah_fluffy.diagnostic.ExceptionDiagnostic;
import tripleo.elijah_fluffy.util.Operation2;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.comp.queries.QuerySearchEzFiles;
import tripleo.wrap.File;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
		ErrSink     errSink = cc.errSink();
		List<Operation2<CompilerInstructions>> R       = new ArrayList<>();

		for (final String file_name : files) {
			try {
				final File                 file   = new File(directory, file_name);
				final CompilerInstructions ezFile = parseEzFile.apply(file);
				if (ezFile != null) {
					R.add(Operation2.success(ezFile));

					c.getObjectTree().asseverate(ezFile, Asseverate.EZ_PARSED);
					c.reports().addInput(() -> file_name, EZ_FILE);
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
