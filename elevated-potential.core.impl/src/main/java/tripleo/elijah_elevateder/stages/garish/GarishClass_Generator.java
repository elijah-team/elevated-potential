package tripleo.elijah_elevateder.stages.garish;

import org.jetbrains.annotations.*;



import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.EvaClass;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResult;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;

public class GarishClass_Generator {
	private final EvaClass carrier;
	private       boolean  generatedAlready;

	public GarishClass_Generator(final EvaClass aEvaClass) {
		carrier = aEvaClass;
	}

	public void provide(final @NotNull GenerateResultSink aResultSink,
	                    final @NotNull GarishClass          aGarishClass,
	                    final @NotNull GenerateResult aGenerateResult,
	                    final @NotNull GenerateC aGenerateC) {
		if (!generatedAlready) {
			switch (carrier.getKlass().getType()) {
			// Don't generate class definition for these three
			case INTERFACE:
			case SIGNATURE:
			case ABSTRACT:
				return;
			}

			aResultSink.add(new GarishClass__addClass_1(aGarishClass, aGenerateResult, aGenerateC));
			generatedAlready = true;
		}
	}
}
