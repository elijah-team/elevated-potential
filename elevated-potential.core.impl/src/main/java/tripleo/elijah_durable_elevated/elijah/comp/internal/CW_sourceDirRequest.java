package tripleo.elijah_durable_elevated.elijah.comp.internal;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.EIT_InputType;
import tripleo.elijah.util.*;

import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.wrap.File;
import java.util.function.*;

public class CW_sourceDirRequest {
	@SuppressWarnings("CallToPrintStackTrace")
	public static void apply(File[] files,
	                         File ignoredDir,
	                         LibraryStatementPart ignoredALsp,
	                         Function<File, Operation2<OS_Module>> ffom,
	                         Compilation c,
	                         USE_Reasoning ignoredAReasoning) {
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

			c.reports().addInput(() -> file_name, EIT_InputType.ELIJAH_SOURCE);
		}
	}

	private static void logProgress(int code, String message) {
		// FIXME ce.logProgress
		System.out.printf("[CW_sourceDirRequest::logProgress] %d %s%n", code, message);
	}
}
