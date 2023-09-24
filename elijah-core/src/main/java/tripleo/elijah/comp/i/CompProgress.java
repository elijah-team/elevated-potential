package tripleo.elijah.comp.i;

import tripleo.elijah.comp.*;
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
			File f = aInputRequest.file();

			out.printf("** [__parseElijjahFile_InputRequest] %s%n", f.getAbsolutePath());
		}
	},
	__CCI_Acceptor__CompilerInputListener__change__logInput {
		@Override
		public void deprecated_print(Object x, PrintStream out, PrintStream err) {
			CompilerInput i = (CompilerInput) x;
			err.println("389389 " + i);
		}
	};

	public abstract void deprecated_print(Object x, PrintStream out, PrintStream err);
}
