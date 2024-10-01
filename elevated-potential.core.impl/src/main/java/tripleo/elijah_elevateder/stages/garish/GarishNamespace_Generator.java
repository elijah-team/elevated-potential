package tripleo.elijah_elevateder.stages.garish;




import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaNamespace;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;

public class GarishNamespace_Generator {
	private final EvaNamespace carrier;
	private       boolean      generatedAlready;

	public GarishNamespace_Generator(final EvaNamespace aEvaNamespace) {
		carrier = aEvaNamespace;
	}

	public boolean generatedAlready() {
		return generatedAlready;
	}

	public void provide(final GenerateResultSink aResultSink,
	                    final GarishNamespace aGarishNamespace,
	                    final GenerateResult aGr,
	                    final GenerateC aGenerateC) {
		if (generatedAlready) {
			throw new IllegalStateException("GarishNamespace generated already");
		}

		// TODO do we need `self' parameters for namespace?

		if (!carrier.varTable.isEmpty()) { // TODO should we let this through?
			aResultSink.add(new GarishNamespace__addClass_1(aGarishNamespace, aGr, aGenerateC));
		}

		generatedAlready = true;
	}
}
