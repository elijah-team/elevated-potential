package tripleo.elijah.stages.gen_c;

import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_fn.IEvaConstructor.*;

public interface DeducedEvaConstructor extends DeducedEvaNode /*extends IEvaFunctionBase*/ {
	BaseEvaConstructor_Reactive reactive();

    EvaConstructor getCarrier();
}
