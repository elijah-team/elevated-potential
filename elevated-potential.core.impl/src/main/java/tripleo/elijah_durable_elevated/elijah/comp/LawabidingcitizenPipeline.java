package tripleo.elijah_durable_elevated.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.util.Ok;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.stages.hooligan.pipeline_impl.LawabidingcitizenPipelineImpl;

public class LawabidingcitizenPipeline extends PipelineMember implements GPipelineMember {
	private final @NotNull IPipelineAccess               pa;
	private final          LawabidingcitizenPipelineImpl i = new LawabidingcitizenPipelineImpl();

	@Contract(pure = true)
	public LawabidingcitizenPipeline(@NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;
	}

	@Override
	public void run(final Ok aSt, final CB_Output aOutput) {
		try {
			final Compilation compilation = pa.getCompilation();
			i.run(compilation);
		} catch (Throwable t) {
			t.printStackTrace();
			int y = 2;
			y = y;
		}
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}
}
