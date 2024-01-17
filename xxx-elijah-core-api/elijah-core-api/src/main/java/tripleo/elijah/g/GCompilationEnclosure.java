package tripleo.elijah.g;

import org.apache.commons.lang3.tuple.Pair;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.comp.nextgen.i.AsseverationLogProgress;

public interface GCompilationEnclosure {
	GModuleThing addModuleThing(GOS_Module aModule);

	void logProgress(CompProgress aCompProgress, Pair<Integer, String> aCodeMessagePair);

	GModuleThing getModuleThing(GOS_Module aModule);

	void logProgress2(CompProgress aCompProgress, AsseverationLogProgress aAsseverationLogProgress);
}
