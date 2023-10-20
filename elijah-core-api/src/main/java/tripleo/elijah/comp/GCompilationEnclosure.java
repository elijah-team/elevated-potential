package tripleo.elijah.comp;

import org.apache.commons.lang3.tuple.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;

public interface GCompilationEnclosure {
	GModuleThing addModuleThing(GOS_Module aModule);

	void logProgress(CompProgress aCompProgress, Pair<Integer, String> aCodeMessagePair);

	GModuleThing getModuleThing(GOS_Module aModule);

	void logProgress2(CompProgress aCompProgress, AsseverationLogProgress aAsseverationLogProgress);
}
