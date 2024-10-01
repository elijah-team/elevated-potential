package tripleo.elijah_elevateder.world.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.g.GEvaClass;
import tripleo.elijah.g.GGarishClass;
import tripleo.elijah.g.GGenerateResult;
import tripleo.elijah.g.GGenerateResultSink;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateFiles;

public interface LivingClass extends LivingNode {
	GEvaClass evaNode();

	int getCode();

	ClassStatement getElement();

	GGarishClass getGarish();

	void setCode(int aCode);

	void generateWith(GGenerateResultSink aResultSink, @NotNull GGarishClass aGarishClass, GGenerateResult aGr, GenerateFiles aGenerateFiles);
}
