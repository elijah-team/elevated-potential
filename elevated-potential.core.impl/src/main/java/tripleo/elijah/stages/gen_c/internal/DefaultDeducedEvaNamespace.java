package tripleo.elijah.stages.gen_c.internal;

import lombok.experimental.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;

public class DefaultDeducedEvaNamespace implements DeducedEvaNamespace {
	@Delegate
	EvaNamespace carrier;

	//@Override
	//public String identityString() {
	//	return carrier.identityString();
	//}
	//
	//@Override
	//public OS_Module module() {
	//	return carrier.module();
	//}
}
