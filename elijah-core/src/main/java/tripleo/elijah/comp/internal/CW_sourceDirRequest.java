package tripleo.elijah.comp.internal;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.function.*;

public class CW_sourceDirRequest {
	@SuppressWarnings("CallToPrintStackTrace")
	public static void apply(File[] files,
	                         File ignoredDir,
	                         LibraryStatementPart ignoredALsp,
	                         Function<File, Operation2<OS_Module>> ffom,
	                         Compilation c,
	                         USE.USE_Reasoning ignoredAReasoning) {
		for (final File file : files) {
//			final CompFactory.InputRequest inp = c.con().createInputRequest(file, do_out, lsp);

			final String                file_name = file.toString();
			final Operation2<OS_Module> om        = ffom.apply(file);

			if (om.mode() == Mode.FAILURE) {
				logProgress(204 , "" + om.failure());

				var d = om.failure().get();
				if (d instanceof Exception e) {
					// help!!
					e.printStackTrace();
				}

				logProgress(2033, d.getClass().getName());
			}

//			c.reports().addInput(inp, Finally.Out2.ELIJAH);
			c.reports().addInput(() -> file_name, Finally.Out2.ELIJAH);
		}
	}

	private static void logProgress(int code, String message) {
		// FIXME ce.logProgress
		System.out.printf("[CW_sourceDirRequest::logProgress] %d %s%n", code, message);
	}
}
