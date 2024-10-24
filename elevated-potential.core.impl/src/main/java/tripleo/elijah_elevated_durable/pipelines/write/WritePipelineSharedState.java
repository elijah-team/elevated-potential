package tripleo.elijah_elevated_durable.pipelines.write;

import com.google.common.collect.Multimap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_elevateder.stages.generate.ElSystem;
import tripleo.util.buffer.Buffer;

/**
 * Really a record, but state is not all set at once
 */
public final class WritePipelineSharedState {
	public Compilation    c;
	public IPipelineAccess pa;
	public GenerateResultSink grs;
	public Multimap<CompilerInstructions, String> lsp_outputs;
	public Multimap<String, Buffer> mmb;
	// public List<NG_OutputItem> outputs;
	public ElSystem sys;
	private GenerateResult gr;

	public WritePipelineSharedState(final @NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;
		c = pa.getCompilation();
	}

	@Contract(pure = true)
	public GenerateResult getGr() {
		return gr;
	}

	@Contract(mutates = "this")
	public void setGr(final @NotNull GenerateResult aGr) {
		gr = aGr;
	}
}
