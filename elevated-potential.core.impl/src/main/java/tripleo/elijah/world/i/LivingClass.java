package tripleo.elijah.world.i;

import org.jetbrains.annotations.*;

import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_generic.*;

public interface LivingClass extends LivingNode {
	GEvaClass evaNode();

	int getCode();

	ClassStatement getElement();

	GGarishClass getGarish();

	void setCode(int aCode);

	void generateWith(GGenerateResultSink aResultSink, @NotNull GGarishClass aGarishClass, GGenerateResult aGr,
			GenerateFiles aGenerateFiles);
}
