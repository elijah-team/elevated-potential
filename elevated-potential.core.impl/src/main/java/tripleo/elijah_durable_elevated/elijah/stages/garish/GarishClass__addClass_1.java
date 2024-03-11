package tripleo.elijah_durable_elevated.elijah.stages.garish;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_durable_elevated.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah_durable_elevated.elijah.nextgen.output.NG_OutputClass;
import tripleo.elijah_durable_elevated.elijah.stages.gen_c.GenerateC;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GRS_Addable;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

public class GarishClass__addClass_1 implements GRS_Addable {
	private final GarishClass    garishClass;
	//private final GenerateResult generateResult;
	private final GenerateC      generateC;

	public GarishClass__addClass_1(final GarishClass aGarishClass, final GenerateResult aGenerateResult, final GenerateC aGenerateC) {
		garishClass    = aGarishClass;
		//generateResult = aGenerateResult;
		generateC      = aGenerateC;
	}

	@Override
	public void action(final @NotNull GenerateResultSink aResultSink) {
		NG_OutputClass o = new NG_OutputClass();
		o.setClass(garishClass, generateC);
		((IPipelineAccess)aResultSink.pa()).addOutput(o);
	}
}
