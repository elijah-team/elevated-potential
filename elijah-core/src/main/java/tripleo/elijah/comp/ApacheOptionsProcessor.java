package tripleo.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CompilationChange;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.comp.i.OptionsProcessor;
import tripleo.elijah.comp.impl.CC_SetDoOut;
import tripleo.elijah.comp.impl.CC_SetShowTree;
import tripleo.elijah.comp.impl.CC_SetSilent;
import tripleo.elijah.comp.impl.CC_SetStage;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.vendor.org.apache.commons.cli.*;

import java.util.List;

public class ApacheOptionsProcessor implements OptionsProcessor {
	private final CommandLineParser clp     = new DefaultParser();
	private final Options           options = new Options();

	@Contract(pure = true)
	public ApacheOptionsProcessor() {
		options.addOption("s", true, "stage: E: parse; O: output");
		options.addOption("showtree", false, "show tree");
		options.addOption("out", false, "make debug files");
		options.addOption("silent", false, "suppress DeduceType output to console");
	}

	@Override
	public Operation<Ok> process(final @NotNull Compilation c, final @NotNull List<CompilerInput> aInputs, final ICompilationBus aCb) {
		try {
			final CommandLine cmd = clp.parse(options, aInputs);

			/**
			 * {@link ICompilationBus#option(CompilationChange)}
			 */

			// TODO 09/08 promises??
			//c.getCompilationEnclosure().getCompilationBus().option();

			if (cmd.hasOption("s")) {
				new CC_SetStage(cmd.getOptionValue('s')).apply(c);
			}
			if (cmd.hasOption("showtree")) {
				new CC_SetShowTree(true).apply(c);
			}
			if (cmd.hasOption("out")) {
				new CC_SetDoOut(true).apply(c);
			}

			if (Compilation.isGitlab_ci() || cmd.hasOption("silent")) {
				new CC_SetSilent(true).apply(c);
			}

			return Operation.success(Ok.instance());
		} catch (ParseException aE) {
			return Operation.failure(/*new DiagnosticException*/(aE));
		}
	}
}
