package tripleo.elijah_elevated_durable.comp;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah_elevateder.comp.i.Compilation;

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
