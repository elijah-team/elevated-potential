package tripleo.elijah.world.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.nextgen.GEvaClass;
import tripleo.elijah.stages.garish.GarishClass;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;

public interface LivingClass extends LivingNode {
	GEvaClass evaNode();

	int getCode();

	ClassStatement getElement();

	GGarishClass getGarish();

	void setCode(int aCode);

	void generateWith(GGenerateResultSink aResultSink, @NotNull GGarishClass aGarishClass, GGenerateResult aGr, GenerateFiles aGenerateFiles);
}
