package tripleo.elijah.comp.queries;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.CK_SourceFile;
import tripleo.elijah.comp.i.CompilationClosure;
import tripleo.elijah.util.Operation2;

public class QSEZ_Reasonings {
	public record QSEZ_Reasoning__ElaboratedInDirectory(
			tripleo.wrap.File directory,
			String[] fileListing,
			CK_SourceFile<CompilerInstructions> sourceFile,
			CompilationClosure compilationClosure,
			Operation2<CompilerInstructions> compilerInstructionsOperation
	) implements QSEZ_Reasoning { }

	public static QSEZ_Reasoning create (Object o) {
		return new QSEZ_Reasoning() {};
	}
}
