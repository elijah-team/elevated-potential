package tripleo.elijah_durable_elevated.elijah.world.i;

import java.util.Optional;
import java.util.function.Function;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.d.Stages;
import tripleo.elijah.stages.garish.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah_durable_elevated.elijah.stages.garish.GarishNamespace;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

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
