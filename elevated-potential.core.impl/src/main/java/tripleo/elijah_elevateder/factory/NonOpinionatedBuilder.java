package tripleo.elijah_elevateder.factory;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevated_durable.comp.EDL_CompilerController;
import tripleo.elijah.work.WorkList;
import tripleo.elijah_elevateder.work.EDL_WorkList;
import tripleo.elijah_elevated.comp.input.EDL_CompilerInput;

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
		final CompilerInput    input = new EDL_CompilerInput(s, null);
		return input;
	}

	public EDL_CompilerController createCompilerController(final Compilation aC) {
		final EDL_Compilation c = (EDL_Compilation) aC;
		return new EDL_CompilerController(c.getCompilationAccess3());
	}

	public WorkList createWorkList(final Object contextAkaOpinion) {
		return new EDL_WorkList();
	}
}
