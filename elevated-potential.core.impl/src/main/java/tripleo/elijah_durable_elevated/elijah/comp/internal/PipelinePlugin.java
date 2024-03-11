package tripleo.elijah_durable_elevated.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.elijah.comp.PipelineMember;
import tripleo.elijah.g.GPipelineAccess;

public interface PipelinePlugin {
	@NotNull PipelineMember instance(@NotNull GPipelineAccess aCe);

	String name();
}
