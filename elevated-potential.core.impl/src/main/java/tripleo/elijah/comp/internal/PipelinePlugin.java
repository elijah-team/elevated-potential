package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.*;
import tripleo.elijah.g.*;

public interface PipelinePlugin {
	@NotNull
	PipelineMember instance(@NotNull GPipelineAccess aCe);

	String name();
}
