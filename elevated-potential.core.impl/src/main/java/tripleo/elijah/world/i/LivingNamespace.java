package tripleo.elijah.world.i;

import java.util.Optional;
import java.util.function.Function;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.d.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;

public interface LivingNamespace extends LivingNode {
	EvaNamespace evaNode();

	int getCode();

	void setCode(int aCode);

	NamespaceStatement getElement();

	default GarishNamespace getGarish() {
		return (GarishNamespace) getForStage(Stages.GARISH).get();
	}

	<T> Optional<T> getForStage(Stages stg);

	<T> /*Operation<>*/ T getForStage(Stages stg, Function<LivingCreatorSpec, T> factory);

	void generateWith(GenerateResultSink aResultSink, GarishNamespace aGarishNamespace, GenerateResult aGr, GenerateFiles aGenerateFiles);
}
