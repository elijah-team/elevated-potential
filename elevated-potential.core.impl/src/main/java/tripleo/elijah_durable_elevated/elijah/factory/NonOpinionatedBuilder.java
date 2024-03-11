package tripleo.elijah_durable_elevated.elijah.factory;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.comp.internal.CompilationImpl;
import tripleo.elijah_durable_elevated.elijah.comp.internal.DefaultCompilerController;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_durable_elevated.elijah.work.WorkList__;
import tripleo.elijah_elevated.comp.input.CompilerInput_;

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

	public WorkList createWorkList(final Object contextAkaOpinion) {
		return new WorkList__();
	}
}
