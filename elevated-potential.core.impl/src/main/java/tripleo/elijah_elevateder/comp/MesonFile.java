package tripleo.elijah_elevateder.comp;

import com.google.common.collect.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah_elevated_durable.pipelines.WriteMesonPipeline;
import tripleo.elijah_prolific.v.V;

import java.util.*;
import java.util.stream.*;

import static tripleo.elijah.util.Helpers.*;

public class MesonFile implements EG_Statement {

	private final WriteMesonPipeline writeMesonPipeline;
	private final String             aSub_dir;
	private final Multimap<CompilerInstructions, String> lsp_outputs;
	private final CompilerInstructions compilerInstructions;
	private final CP_Path              path;

	public MesonFile(final WriteMesonPipeline aWriteMesonPipeline,
	                 final String aSubDir,
	                 final Multimap<CompilerInstructions, String> aLspOutputs,
	                 final CompilerInstructions aCompilerInstructions,
	                 final CP_Path aPath) {
		writeMesonPipeline   = aWriteMesonPipeline;
		aSub_dir             = aSubDir;
		lsp_outputs          = aLspOutputs;
		compilerInstructions = aCompilerInstructions;
		path                 = aPath;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return EX_Explanation.withMessage("MesonFile");
	}

	public CP_Path getPath() {
		return path;
	}

	public @NotNull String getPathString() {
		return path.getPath().toString();
	}

	@Override
	public @NotNull String getText() {
		final Collection<String> files_ = lsp_outputs.get(compilerInstructions);
		final Set<String> files = files_.stream().filter(x -> x.endsWith(".c"))
				.map(x -> String.format("\t'%s',", writeMesonPipeline.pullFileName(x)))
				.collect(Collectors.toUnmodifiableSet());

		final StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s_sources = files(\n%s\n)", aSub_dir, String_join("\n", files)));
		sb.append("\n");
		sb.append(
				String.format("%s = static_library('%s', %s_sources, install: false,)", aSub_dir, aSub_dir, aSub_dir)); // include_directories,
																														// dependencies:
																														// [],
		sb.append("\n");
		sb.append("\n");
		sb.append(String.format("%s_dep = declare_dependency( link_with: %s )", aSub_dir, aSub_dir)); // include_directories
		sb.append("\n");

		V.asv(V.e.WMP_write_lsp, ""+path);

		final String s = sb.toString();
		return s;
	}
}
