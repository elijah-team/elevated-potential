package tripleo.elijah.factory;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.internal.DefaultCompilerController;

import java.util.List;
import java.util.stream.Collectors;

public class NonOpinionatedBuilder {
	public List<CompilerInput> inputs(final List<String> args) {
		final List<CompilerInput> inputs = args.stream()
				.map(s -> {
					final CompilerInput input = createCompilerInput_simple(s);
					if (!s.startsWith("-")) {//cm.inpSameAs(s)) {
						input.setSourceRoot();
					}
					return input;
				})
				.collect(Collectors.toList());
		return inputs;
	}

	@NotNull
	private static CompilerInput createCompilerInput_simple(final String s) {
		final CompilerInput    input = new CompilerInput_(s, null);
		return input;
	}

	public DefaultCompilerController createCompilerController(final Compilation aC) {
		final CompilationImpl c = (CompilationImpl) aC;
		return new DefaultCompilerController(c.getCompilationAccess3());
	}
}
