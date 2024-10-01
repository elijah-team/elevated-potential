package tripleo.elijah_elevateder.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevated_durable.pipelines.PipelineMember;
import tripleo.elijah.g.GPipelineAccess;

public interface PipelinePlugin {
	@NotNull PipelineMember instance(@NotNull GPipelineAccess aCe);

	String name();
}
