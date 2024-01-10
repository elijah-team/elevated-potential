package tripleo.elijah.stages.garish;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.output.NG_OutputFunction;
import tripleo.elijah.stages.gen_c.C2C_Result;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaConstructor;
import tripleo.elijah.stages.gen_generic.GRS_Addable;
import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

import java.util.List;

public class GarishConstructor__addFunction implements GRS_Addable {
	private final EvaConstructor gf;
	private final List<C2C_Result> rs;
	private final GenerateC generateC;

	public GarishConstructor__addFunction(final EvaConstructor aGf, final List<C2C_Result> aRs, final GenerateC aGenerateC) {
		gf = aGf;
		rs = aRs;
		generateC = aGenerateC;
	}

	@Override
	public void action(@NotNull final GenerateResultSink aResultSink) {
		NG_OutputFunction o = new NG_OutputFunction();
		o.setFunction(gf, generateC, rs);
		((DefaultGenerateResultSink)aResultSink).pa().addOutput(o);
	}
}
