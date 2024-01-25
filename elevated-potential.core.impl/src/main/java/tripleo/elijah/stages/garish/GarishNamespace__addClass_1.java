package tripleo.elijah.stages.garish;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.nextgen.output.NG_OutputNamespace;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_generic.GRS_Addable;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.DefaultGenerateResultSink;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

public class GarishNamespace__addClass_1 implements GRS_Addable {
	private final GarishNamespace garishNamespace;
	//private final GenerateResult gr;
	private final GenerateC generateC;

	public GarishNamespace__addClass_1(final GarishNamespace aGarishNamespace, final GenerateResult aGr, final GenerateC aGenerateC) {
		garishNamespace = aGarishNamespace;
		//gr              = aGr;
		generateC       = aGenerateC;
	}

	@Override
	public void action(@NotNull final GenerateResultSink aResultSink) {
		NG_OutputNamespace o = new NG_OutputNamespace();
		o.setNamespace(garishNamespace, generateC);
		((DefaultGenerateResultSink)aResultSink).pa().addOutput(o);
	}
}
