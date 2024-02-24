package tripleo.elijah.comp.internal;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.CompilerInput;
import tripleo.elijah.comp.i.IProgressSink;

import java.util.List;

public record CompilerBeginning(
		Compilation compilation,
		CompilerInstructions compilerInstructions,
		List<CompilerInput> compilerInput,
		IProgressSink progressSink,
		Compilation.CompilationConfig cfg
) {


	//@Override
	//public String toString() {
	//	return "CompilerBeginning[" +
	//			"compilation=" + compilation + ", " +
	//			"compilerInstructions=" + compilerInstructions + ", " +
	//			"compilerInput=" + compilerInput + ", " +
	//			"progressSink=" + progressSink + ", " +
	//			"cfg=" + cfg + ']';
	//}

}
