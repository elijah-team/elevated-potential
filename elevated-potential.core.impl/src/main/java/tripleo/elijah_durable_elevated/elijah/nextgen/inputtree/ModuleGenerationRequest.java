package tripleo.elijah_durable_elevated.elijah.nextgen.inputtree;

import tripleo.elijah.g.*;
import tripleo.elijah.work.WorkManager;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import java.util.List;
import java.util.function.Consumer;

public interface ModuleGenerationRequest extends GModuleGenerationRequest {
	List<GEvaNode> baseNodes();

	WorkManager work();

	Consumer<GGenerateResult> baseGenerate();

	GCompilationEnclosure baseEnclosure();


	List<EvaNode> getEvaNodeList();

	Consumer<GenerateResult> getGenerateResultConsumer();

	CompilationEnclosure getCompilationEnclosure();
}
