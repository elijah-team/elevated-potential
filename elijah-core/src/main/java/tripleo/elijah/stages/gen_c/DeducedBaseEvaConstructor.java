package tripleo.elijah.stages.gen_c;

import tripleo.elijah.stages.gen_fn.IEvaConstructor.BaseEvaConstructor_Reactive;
import tripleo.elijah.stages.gen_fn.IEvaFunctionBase;

public interface DeducedBaseEvaConstructor extends IEvaFunctionBase {
	BaseEvaConstructor_Reactive reactive();
}
