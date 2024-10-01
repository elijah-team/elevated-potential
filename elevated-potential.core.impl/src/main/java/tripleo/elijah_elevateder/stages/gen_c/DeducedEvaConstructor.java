package tripleo.elijah_elevateder.stages.gen_c;

import tripleo.elijah_elevateder.stages.gen_fn.EvaConstructor;
import tripleo.elijah_elevateder.stages.gen_fn.IEvaConstructor.BaseEvaConstructor_Reactive;
import tripleo.elijah_elevateder.stages.gen_fn.IEvaFunctionBase;

public interface DeducedEvaConstructor extends IEvaFunctionBase {
	BaseEvaConstructor_Reactive reactive();

    EvaConstructor getCarrier();
}
