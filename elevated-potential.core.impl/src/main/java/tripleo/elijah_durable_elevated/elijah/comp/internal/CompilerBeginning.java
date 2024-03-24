package tripleo.elijah_durable_elevated.elijah.comp.internal;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;

import java.util.List;

public record CompilerBeginning(
		Compilation compilation,
		CompilerInstructions rootCI,
		List<CompilerInput> compilerInput,
		IProgressSink progressSink,
		Compilation.CompilationConfig cfg
) {


	@Override
	public String toString() {
		return "CompilerBeginning[" +
				"compilation=" + compilation + ", " +
				"rootCI=" + rootCI + ", " +
				"compilerInput=" + compilerInput + ", " +
				"progressSink=" + progressSink + ", " +
				"cfg=" + cfg + ']';
	}

}
