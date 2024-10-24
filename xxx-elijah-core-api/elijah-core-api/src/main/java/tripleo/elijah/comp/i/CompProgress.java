package tripleo.elijah.comp.i;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.queries.CompilerInstructions_Result;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah_fluffy.util.Mode;
import tripleo.elijah_fluffy.util.Operation2;

import java.io.*;
import java.util.List;

// FIXME use emojis??
// NOTE strange pattern (ie why not just directly?)
public enum CompProgress {
	Compilation__hasInstructions__empty {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			String absolutePath = (String) x;

			err.println("No CIs found. Current dir is " + absolutePath);
		}
	},
	__CP_OutputPath_renderNode {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
//			ER_Node node = (ER_Node) x;

//			out.printf("** [__CP_OutputPath_renderNode] %s%n", node.getPath());
		}
	},
	__parseElijjahFile_InputRequest {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			// eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
/*
			InputRequest aInputRequest = (InputRequest) x;
			File         f             = aInputRequest.file();

			out.printf("** [__parseElijjahFile_InputRequest] %s%n", f.getAbsolutePath());
*/
		}
	},
	__CCI_Acceptor__CompilerInputListener__change__logInput {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			CompilerInput i = (CompilerInput) x;

			out.printf("[-- Ez CIL change ] %s %s%n", i, i.ty());

			final CompilerInstructions_Result            directoryResult1 = i.getDirectoryResults();
			if (directoryResult1 != null) {
				final List<Operation2<CompilerInstructions>> directoryResult2 = directoryResult1.getDirectoryResult();

				for (Operation2<CompilerInstructions> directoryResult : directoryResult2) {
					if (directoryResult.mode() == Mode.SUCCESS) {
						final CompilerInstructions compilerInstructions = directoryResult.success();

						out.println("[--- Ez directoryResult ] " + compilerInstructions.getFilename());
					}
				}
			} else {
				out.println("[--- Ez directoryResult ] == null");
			}
		}
	},
	USE__parseElijjahFile {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			String absolutePath = (String) x;

			out.printf("[USE::parseElijjahFile] %s%n", absolutePath);
		}
	}, Ez__HasHash {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			var t = (Pair<EzSpec, String>) x;

			var spec = t.getLeft();
			var hash = t.getRight();

			out.printf("[-- Ez has HASH ] %s %s%n", spec.file(), hash);
		}
	}, GenerateC {
		@Override
		public void deprecated_print(final Object x, final PrintStream out, final PrintStream err) {
			final Pair<Integer, String> t = (Pair<Integer, String>) x;

			final int    code = t.getLeft();
			final String aS   = t.getRight();

			out.printf("%d %s%n", code, aS);
		}
	}, DeducePhase {
		@Override
		public void deprecated_print(final Object x, final PrintStream out, final PrintStream err) {
			final Pair<Integer, String> t = (Pair<Integer, String>) x;

			final int    code    = t.getLeft();
			final String message = t.getRight();

			out.printf("%d %s%n", code, message);
		}
	}, DriverPhase {
		@Override
		public void deprecated_print(final Object x, final PrintStream out, final PrintStream err) {
			final Pair<Integer, String> t = (Pair<Integer, String>) x;

			final int    code    = t.getLeft();
			final String message = t.getRight();

			final String codeText = switch (code) {
				case 37939 -> "findStdLib";
				default -> "" + code;
			};

			out.printf("[-- DriverPhase ] %s %s%n", codeText, message);
		}
	};

	public abstract void deprecated_print(Object x, PrintStream out, PrintStream err);
}
