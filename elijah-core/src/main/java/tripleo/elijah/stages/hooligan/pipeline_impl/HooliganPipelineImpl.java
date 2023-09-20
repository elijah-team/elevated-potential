package tripleo.elijah.stages.hooligan.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.world.i.*;

import java.util.*;
import java.util.stream.Collectors;

public class HooliganPipelineImpl {
	public void run(final @NotNull Compilation compilation) {
		final Hooligan              hooligan = new Hooligan();
		final Collection<WorldModule> modules  = compilation.livingRepo().modules();

		final Hooligan.SmallWriter1 sw       = hooligan.__modules2(modules);
		final EOT_OutputTree        cot      = compilation.getOutputTree();
		final List<EIT_Input>       inputs   = inputs_for_modules(modules, compilation);
		final String                text     = sw.getText();
		final EG_Statement          seq      = EG_Statement.of(text, EX_Explanation.withMessage("modules-sw-writer"));
		final EOT_OutputFile        off      = new EOT_OutputFile(inputs, "modules-sw-writer", EOT_OutputType.SWW, seq);

		cot.add(off);
	}

	private @NotNull List<EIT_Input> inputs_for_modules(final Collection<WorldModule> aModules, final Compilation c) {
		return aModules.stream()
				.map(WorldModule::getEITInput)
				.collect(Collectors.toList());
	}
}
