package tripleo.elijah.comp.i;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.nextgen.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;

import java.io.*;

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
			ER_Node node = (ER_Node) x;

//			out.printf("** [__CP_OutputPath_renderNode] %s%n", node.getPath());
		}
	},
	__parseElijjahFile_InputRequest {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			InputRequest aInputRequest = (InputRequest) x;
			File         f             = aInputRequest.file();

			out.printf("** [__parseElijjahFile_InputRequest] %s%n", f.getAbsolutePath());
		}
	},
	__CCI_Acceptor__CompilerInputListener__change__logInput {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			CompilerInput i = (CompilerInput) x;

			out.printf("[-- Ez CIL change ] %s %s%n", i, i.ty());

			if (i.getDirectoryResults() != null) {
				for (Operation2<CompilerInstructions> directoryResult : i.getDirectoryResults()) {
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
			Pair<Integer, String> t = (Pair<Integer, String>)x;

			var aI = t.getLeft();
			var aS = t.getRight();

			out.printf("%d %s%n", aI, aS);
		}
	}, DeducePhase {
		@Override
		public void deprecated_print(final Object x, final PrintStream out, final PrintStream err) {
			Pair<Integer, String> t = (Pair<Integer, String>)x;

			var aI = t.getLeft();
			var aS = t.getRight();

			out.printf("%d %s%n", aI, aS);
		}
	}, DriverPhase {
		@Override
		public void deprecated_print(final Object x, final PrintStream out, final PrintStream err) {
			Pair<Integer, String> t = (Pair<Integer, String>)x;

			var aI = t.getLeft();

			final String ss;
			if (aI == 37939) {
				ss = "findStdLib";
			} else {
				ss = ""+aI;
			}

			var aS = t.getRight();

			out.printf("[-- DriverPhase ] %s %s%n", ss, aS);
		}
	};

	public abstract void deprecated_print(Object x, PrintStream out, PrintStream err);
}
