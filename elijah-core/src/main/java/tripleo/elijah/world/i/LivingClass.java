package tripleo.elijah.world.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;

public interface LivingClass extends LivingNode {
	EvaClass evaNode();

	int getCode();

	ClassStatement getElement();

	GarishClass getGarish();

	void setCode(int aCode);

	void generateWith(GenerateResultSink aResultSink, @NotNull GarishClass aGarishClass, GenerateResult aGr, GenerateFiles aGenerateFiles);
}
