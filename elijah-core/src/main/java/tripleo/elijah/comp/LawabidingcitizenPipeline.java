package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.stages.hooligan.pipeline_impl.*;

public class LawabidingcitizenPipeline extends PipelineMember implements GPipelineMember {
	private final @NotNull IPipelineAccess pa;
	private final LawabidingcitizenPipelineImpl i = new LawabidingcitizenPipelineImpl();

	@Contract(pure = true)
	public LawabidingcitizenPipeline(@NotNull IPipelineAccess pa0) {
		pa = pa0;
	}

	@Override
	public void run(final CR_State aSt, final CB_Output aOutput) throws Exception {
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
