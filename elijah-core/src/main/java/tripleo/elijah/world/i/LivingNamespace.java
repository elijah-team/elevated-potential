package tripleo.elijah.world.i;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;

public interface LivingNamespace extends LivingNode {
	EvaNamespace evaNode();

	int getCode();

	void setCode(int aCode);

	NamespaceStatement getElement();

	GarishNamespace getGarish();

	void generateWith(GenerateResultSink aResultSink, GarishNamespace aGarishNamespace, GenerateResult aGr, GenerateFiles aGenerateFiles);
}
