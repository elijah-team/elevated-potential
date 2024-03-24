package tripleo.elijah_durable_elevated.elijah.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_elevated.elijah.stages.generate.OutputStrategyC;

import java.util.List;

public interface NG_OutputItem {
	@NotNull
	List<NG_OutputStatement> getOutputs();

	EOT_FileNameProvider outName(OutputStrategyC aOutputStrategyC, final GenerateResult.TY ty);
}
