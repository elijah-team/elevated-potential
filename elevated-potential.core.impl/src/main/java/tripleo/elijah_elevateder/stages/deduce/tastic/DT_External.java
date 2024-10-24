package tripleo.elijah_elevateder.stages.deduce.tastic;

import org.jdeferred2.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevateder.stages.deduce.DeduceTypes2;

public interface DT_External {
	// void run();

	void actualise(DeduceTypes2 aDt2);

	Promise<OS_Module, Void, Void> onTargetModulePromise();

	OS_Module targetModule();

	void tryResolve();
}
