package tripleo.elijah.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.g.*;
import tripleo.elijah.stages.gen_c.C2C_Result;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.world.i.LivingClass;
import tripleo.elijah.world.i.LivingNamespace;

import java.util.List;

public interface GenerateResultSink extends GGenerateResultSink {
	void add(GRS_Addable node);

	void addFunction(BaseEvaFunction aGf, List<C2C_Result> aRs, GenerateFiles aGenerateFiles);

	void additional(GenerateResult aGenerateResult);

	@Nullable
	LivingClass getLivingClassForEva(GEvaClass aEvaClass);

	@Nullable
	LivingNamespace getLivingNamespaceForEva(GEvaNamespace aEvaClass);

	GPipelineAccess pa();
}
