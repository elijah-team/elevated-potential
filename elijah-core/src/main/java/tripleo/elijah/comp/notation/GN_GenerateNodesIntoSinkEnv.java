package tripleo.elijah.comp.notation;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.i.CompilationEnclosure;
import tripleo.elijah.comp.i.IPipelineAccess;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.ProcessedNode;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.world.i.WorldModule;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public record GN_GenerateNodesIntoSinkEnv(
		List<ProcessedNode> lgc,
		GenerateResultSink resultSink1,
		Object/*EIT_ModuleList*/ moduleList,
		ElLog.Verbosity verbosity,
		GenerateResult gr,
		IPipelineAccess pa,
		CompilationEnclosure ce
) implements GN_Env {

	@org.jetbrains.annotations.Nullable
	public static String getLang(final @NotNull OS_Module mod) {
		final LibraryStatementPart lsp = mod.getLsp();

		if (lsp == null) {
			SimplePrintLoggerToRemoveSoon.println_err_2("7777777777777777777 mod.getFilename " + mod.getFileName());
			return null;
		}

		final CompilerInstructions ci = lsp.getInstructions();
		final @Nullable String lang2 = ci.genLang();

		final @Nullable String lang = lang2 == null ? "c" : lang2;
		return lang;
	}

	@NotNull
	static GenerateFiles getGenerateFiles(final @NotNull OutputFileFactoryParams params, final @NotNull WorldModule wm,
			final @NotNull Supplier<GenerateResultEnv> fgs) {
		final GenerateResultEnv fileGen;
		final OS_Module mod = wm.module();

		// TODO creates more than one GenerateC, look into this
		// TODO ^^ validate this or not plz 09/07

		final String lang = getLang(mod);
		if (lang == null) {
			// 09/26 System.err.println("lang==null for " + mod.getFileName());
			// throw new NotImplementedException();
		}

		if (Objects.equals(lang, "c")) {
			fileGen = fgs.get(); // FIXME "deep" implementation detail
		} else {
			// fileGen = null;
			fileGen = fgs.get();
		}

		String lang1 = Optional.ofNullable(lang).orElse(Compilation.CompilationAlways.defaultPrelude());
		return OutputFileFactory.create(lang1, params, fileGen);
	}

	@Contract("_, _ -> new")
	@NotNull
	OutputFileFactoryParams getParams(final WorldModule mod,
			final @NotNull GN_GenerateNodesIntoSink aGNGenerateNodesIntoSink) {
		return new OutputFileFactoryParams(mod, aGNGenerateNodesIntoSink._env().ce());
	}
}
