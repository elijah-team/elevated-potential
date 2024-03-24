package tripleo.elijah_durable_elevated.elijah.world.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateFiles;

public interface LivingClass extends LivingNode {
	GEvaClass evaNode();

	int getCode();

	ClassStatement getElement();

	GGarishClass getGarish();

	void setCode(int aCode);

	void generateWith(GGenerateResultSink aResultSink, @NotNull GGarishClass aGarishClass, GGenerateResult aGr, GenerateFiles aGenerateFiles);
}
