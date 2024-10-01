package tripleo.elijah_elevated_durable.pipelines;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.util.Ok;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.stages.hooligan.pipeline_impl.LawabidingcitizenPipelineImpl;

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
