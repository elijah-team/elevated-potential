package tripleo.elijah_elevateder.stages.hooligan.pipeline_impl;

import org.jetbrains.annotations.*;

import tripleo.elijah_elevateder.DebugFlags;
import tripleo.elijah.diagnostic.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.nextgen.outputtree.EOT_OutputFileImpl;
import tripleo.elijah_elevateder.world.i.WorldModule;
import tripleo.elijah_fluffy.diagnostic.Diagnostic;
import tripleo.elijah_fluffy.util.CompletableProcess;

import java.util.*;
import java.util.stream.*;

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

					final String text = sw.getText();
					final EG_Statement       seq = EG_Statement.of(text, EX_Explanation.withMessage("modules-sw-writer"));
					final EOT_OutputFileImpl off = new EOT_OutputFileImpl(inputs, "modules-sw-writer", EOT_OutputType.SWW, seq);

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
