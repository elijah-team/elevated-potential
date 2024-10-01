package tripleo.elijah_elevateder.nextgen.output;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.generate.OutputStrategyC;

import java.util.List;

public interface NG_OutputItem {
	@NotNull
	List<NG_OutputStatement> getOutputs();

	EOT_FileNameProvider outName(OutputStrategyC aOutputStrategyC, final GenerateResult.TY ty);
}
