package tripleo.elijah.stages.garish;

import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah.stages.gen_generic.pipeline_impl.GenerateResultSink;

public class GarishNamespace_Generator {
	private final EvaNamespace carrier;
	private       boolean      generatedAlready;

	public GarishNamespace_Generator(final EvaNamespace aEvaNamespace) {
		carrier = aEvaNamespace;
	}

	public void provide(final GenerateResultSink aResultSink,
						final GarishNamespace aGarishNamespace,
						final GenerateResult aGr,
						final GenerateC aGenerateC) {
		if (generatedAlready) {
			throw new IllegalStateException("GarishNamespace generated already");
		}

		// TODO do we need `self' parameters for namespace?

		if (carrier.varTable.size() > 0) { // TODO should we let this through?
			//aResultSink.addNamespace_0(this, result.tos().getBuffer(), result.tosHdr().getBuffer());
			aResultSink.addNamespace_1(aGarishNamespace, aGr, aGenerateC);
		}

		generatedAlready = true;
	}
}
