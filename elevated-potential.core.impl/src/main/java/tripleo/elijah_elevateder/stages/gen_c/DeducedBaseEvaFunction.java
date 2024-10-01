package tripleo.elijah_elevateder.stages.gen_c;

import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.stages.gen_fn.IEvaFunctionBase;

public interface DeducedBaseEvaFunction extends IEvaFunctionBase {
	BaseEvaFunction_Reactive reactive();

	IEvaFunctionBase getCarrier();

	OS_Module getModule__();

	WhyNotGarish_Function getWhyNotGarishFunction(GenerateC aGc);
}
