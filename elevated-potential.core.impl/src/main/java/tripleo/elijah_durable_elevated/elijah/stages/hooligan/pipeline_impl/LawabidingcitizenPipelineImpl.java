package tripleo.elijah_durable_elevated.elijah.stages.hooligan.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.util.CompletableProcess;
import tripleo.elijah_durable_elevated.elijah.DebugFlags;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.elijah_durable_elevated.elijah.nextgen.outputtree.EOT_OutputFileImpl;
import tripleo.elijah_durable_elevated.elijah.world.i.WorldModule;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LawabidingcitizenPipelineImpl {
	public void run(final @NotNull Compilation compilation) {
		final Lawabidingcitizen hooligan = new Lawabidingcitizen();

		compilation.world().addModuleProcess(new CompletableProcess<WorldModule>() {
			@Override
			public void add(WorldModule item) {
				// README ignored, we are taking list at end
			}

			@Override
			public void complete() {
				Collection<WorldModule> worldModules = compilation.world().modules();

				if (!DebugFlags.Lawabidingcitizen_disabled) {
					final Lawabidingcitizen.SmallWriter1 sw = hooligan.__modules2(worldModules);
					final EOT_OutputTree cot = compilation.getOutputTree();

					final List<EIT_Input> inputs = worldModules.stream()
							.map(WorldModule::getEITInput)
							.collect(Collectors.toList());

					final String             text = sw.getText();
					final EG_Statement       seq  = EG_Statement.of(text, EX_Explanation.withMessage("modules-sw-writer"));
					final EOT_OutputFileImpl off  = new EOT_OutputFileImpl(inputs, "modules-sw-writer", EOT_OutputType.SWW, seq);

					cot.add(off);
				}
			}

			@Override
			public void error(Diagnostic d) {

			}

			@Override
			public void preComplete() {

			}

			@Override
			public void start() {

			}
		});
	}
}
