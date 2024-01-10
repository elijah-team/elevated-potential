package tripleo.elijah.stages.gen_generic;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

public interface GRS_Addable {
	void action(@NotNull GenerateResultSink aResultSink);
}
