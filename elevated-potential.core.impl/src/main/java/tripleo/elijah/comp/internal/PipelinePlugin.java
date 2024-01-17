package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.PipelineMember;
import tripleo.elijah.g.GPipelineAccess;

public interface PipelinePlugin {
	@NotNull PipelineMember instance(@NotNull GPipelineAccess aCe);

	String name();
}
