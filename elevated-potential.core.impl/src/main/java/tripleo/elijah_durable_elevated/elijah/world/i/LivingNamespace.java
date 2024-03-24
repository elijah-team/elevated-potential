package tripleo.elijah_durable_elevated.elijah.world.i;

import tripleo.elijah.lang.i.NamespaceStatement;
import tripleo.elijah.stages.d.Stages;
import tripleo.elijah_durable_elevated.elijah.stages.garish.GarishNamespace;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

import java.util.Optional;
import java.util.function.Function;

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
