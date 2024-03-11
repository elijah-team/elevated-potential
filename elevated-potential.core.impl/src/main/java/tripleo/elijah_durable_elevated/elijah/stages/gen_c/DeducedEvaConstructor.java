package tripleo.elijah_durable_elevated.elijah.stages.gen_c;

import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.*;

public interface DeducedEvaConstructor extends IEvaFunctionBase {
	IEvaConstructor.BaseEvaConstructor_Reactive reactive();

    EvaConstructor getCarrier();
}
