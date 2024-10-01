package tripleo.elijah_elevateder.stages.gen_generic;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.work.WorkList;
import tripleo.elijah.work.WorkManager;

import java.util.Collection;
import java.util.List;

public interface GenerateFiles extends CodeGenerator {
	ElLog elLog();

	void finishUp(final GenerateResult aGenerateResult, final WorkManager wm, final WorkList aWorkList);

	void generate_constructor(EvaConstructor aGf, GenerateResult aGr, WorkList aWl, GenerateResultSink aResultSink,
	                          final WorkManager aWorkManager, final @NotNull GenerateResultEnv aFileGen);

	void generate_function(EvaFunction aEvaFunction, GenerateResult aGenerateResult, WorkList aWorkList,
	                       GenerateResultSink aResultSink);

	GenerateResult generateCode(Collection<EvaNode> lgn, @NotNull GenerateResultEnv aFileGen);

	<T> GenerateResultEnv getFileGen();

	GenerateResult resultsFromNodes(@NotNull List<EvaNode> aNodes, WorkManager wm, GenerateResultSink grs,
			@NotNull GenerateResultEnv fg);
}
