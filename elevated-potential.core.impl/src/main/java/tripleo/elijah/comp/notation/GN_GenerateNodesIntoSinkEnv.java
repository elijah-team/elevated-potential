package tripleo.elijah.comp.notation;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.world.i.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import java.util.*;
import java.util.function.*;

public final class GN_GenerateNodesIntoSinkEnv implements GN_Env {
	private final List<ProcessedNode>  lgc;
	private final GenerateResultSink   resultSink1;
	private final Object           moduleList;
	private final ElLog_.Verbosity verbosity;
	private final GenerateResult   gr;
	private final IPipelineAccess      pa;
	private final CompilationEnclosure ce;

	public GN_GenerateNodesIntoSinkEnv(
			List<ProcessedNode> lgc,
			GenerateResultSink resultSink1,
			ElLog_.Verbosity verbosity,
			GenerateResult gr,
			CompilationEnclosure ce
									  ) {
		this.lgc         = lgc;
		this.resultSink1 = resultSink1;
		this.moduleList  = ce.getCompilation().getObjectTree().getModuleList();
		this.verbosity   = verbosity;
		this.gr          = gr;
		this.pa          = ce.getPipelineAccess();
		this.ce          = ce;
	}

	@org.jetbrains.annotations.Nullable
	public static String getLang(final @NotNull OS_Module mod) {
		final LibraryStatementPart lsp = mod.getLsp();

		if (lsp == null) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_2("7777777777777777777 mod.getFilename " + mod.getFileName());
			return null;
		}

		final CompilerInstructions ci    = lsp.getInstructions();
		final Optional<String>     s     = ci.genLang();
		if (s.isPresent()) {
			final @Nullable String lang2 = s.get();
			final @Nullable String lang  = lang2 == null ? "c" : lang2;
			return lang;
		} else return null;
	}

	@NotNull
	static GenerateFiles getGenerateFiles(final @NotNull OutputFileFactoryParams params, final @NotNull WorldModule wm,
										  final @NotNull Supplier<GenerateResultEnv> fgs) {
		final GenerateResultEnv fileGen;
		final OS_Module         mod = wm.module();

		// TODO creates more than one GenerateC, look into this
		// TODO ^^ validate this or not plz 09/07

		final String lang = getLang(mod);
		if (lang == null) {
			// 09/26 tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("lang==null for " + mod.getFileName());
			// throw new NotImplementedException();
		}

		if (Objects.equals(lang, "c")) {
			fileGen = fgs.get(); // FIXME "deep" implementation detail
		} else {
			// fileGen = null;
			fileGen = fgs.get();
		}

		String lang1 = Optional.ofNullable(lang).orElse(CompilationImpl.CompilationAlways.defaultPrelude());
		return OutputFileFactory.create(lang1, params, fileGen);
	}

	@Contract("_, _ -> new")
	@NotNull
	OutputFileFactoryParams getParams(final WorldModule mod,
									  final @NotNull GN_GenerateNodesIntoSink aGNGenerateNodesIntoSink) {
		return new OutputFileFactoryParams(mod, aGNGenerateNodesIntoSink._env().ce());
	}

	public List<ProcessedNode> lgc() {
		return lgc;
	}

	public GenerateResultSink resultSink1() {
		return resultSink1;
	}

	public Object moduleList() {
		return moduleList;
	}

	public ElLog_.Verbosity verbosity() {
		return verbosity;
	}

	public GenerateResult gr() {
		return gr;
	}

	public IPipelineAccess pa() {
		return pa;
	}

	public CompilationEnclosure ce() {
		return ce;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (GN_GenerateNodesIntoSinkEnv) obj;
		return Objects.equals(this.lgc, that.lgc) &&
				Objects.equals(this.resultSink1, that.resultSink1) &&
				Objects.equals(this.moduleList, that.moduleList) &&
				Objects.equals(this.verbosity, that.verbosity) &&
				Objects.equals(this.gr, that.gr) &&
				Objects.equals(this.pa, that.pa) &&
				Objects.equals(this.ce, that.ce);
	}

	@Override
	public int hashCode() {
		return Objects.hash(lgc, resultSink1, moduleList, verbosity, gr, pa, ce);
	}

	@Override
	public String toString() {
		return "GN_GenerateNodesIntoSinkEnv[" +
				"lgc=" + lgc + ", " +
				"resultSink1=" + resultSink1 + ", " +
				"moduleList=" + moduleList + ", " +
				"verbosity=" + verbosity + ", " +
				"gr=" + gr + ", " +
				"pa=" + pa + ", " +
				"ce=" + ce + ']';
	}

}
