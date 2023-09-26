package tripleo.elijah.comp.i;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.nextgen.*;

import java.io.*;

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

			out.printf("** [__CP_OutputPath_renderNode] %s%n", node.getPath());
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
			err.println("389389 " + i);
		}
	}, EzM__logProgress {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			var p       = (Pair<IProgressSink.Codes, String>) x;
			var code    = p.getLeft();
			var message = p.getRight();

			final String k = "[EzM] %d %s".formatted(code.value(), message);
			switch (code) {
				case EzM__realParseEzFile -> {
					int ignoreMe=-1;
					out.println(k);
				}
				default -> {
					out.println(k);
				}
			}
		}
	}, USE__parseElijjahFile {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			String absolutePath = (String) x;

			out.printf("[USE::parseElijjahFile] %s%n", absolutePath);
		}
	}, Ez__HasHash {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			var t = (Pair<EzSpec, String>)x;

			var spec = t.getLeft();
			var hash = t.getRight();

			out.printf("[-- Ez has HASH ] %s %s%n", spec.file(), hash);
		}
	};

	public abstract void deprecated_print(Object x, PrintStream out, PrintStream err);
}
