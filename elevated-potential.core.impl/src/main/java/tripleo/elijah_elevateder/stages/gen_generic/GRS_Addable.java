package tripleo.elijah_elevateder.stages.gen_generic;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;

public interface GRS_Addable {
	void action(@NotNull GenerateResultSink aResultSink);
}
