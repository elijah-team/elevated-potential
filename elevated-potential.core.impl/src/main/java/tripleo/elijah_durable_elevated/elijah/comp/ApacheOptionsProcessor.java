package tripleo.elijah_durable_elevated.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah_durable_elevated.elijah.comp.impl.CC_SetSilent;
import tripleo.elijah_durable_elevated.elijah.comp.internal.CompilationImpl;
import tripleo.vendor.org_apache_commons_cli.*;

import java.util.List;

public class ApacheOptionsProcessor implements OptionsProcessor {
	private final CommandLineParser clp     = new DefaultParser();
	private final Options           options = new Options();

	@Contract(pure = true)
	public ApacheOptionsProcessor() {
		options.addOption("s", true, "stage: E: parse; O: output");
		options.addOption("silent", false, "suppress DeduceType output to console");
	}

	public Operation<Ok> process(final @NotNull Compilation c,
								 final @NotNull List<CompilerInput> aInputs,
								 final ICompilationBus aCb) {
		try {
			final CommandLine cmd = clp.parse(options, aInputs);

			/**
			 * {@link ICompilationBus#option(CompilationChange)}
			 */

			// TODO 09/08 promises??
			// c.getCompilationEnclosure().getCompilationBus().option();

			if (CompilationImpl.isGitlab_ci() || cmd.hasOption("silent")) {
				aCb.addCompilerChange(CC_SetSilent.class);
//				new CC_SetSilent(true).apply(c);
			}

			return Operation.success(Ok.instance());
		} catch (ParseException aE) {
			return Operation.failure(/* new DiagnosticException */(aE));
		}
	}

	@Override
	public Operation<Ok> process(final Compilation0 aC, final List<CompilerInput> aInputs, final ICompilationBus aCb) {
		return process((Compilation) aC, aInputs, aCb);
	}
}
