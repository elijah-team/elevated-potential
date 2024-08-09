package tripleo.elijah.stages.gen_c;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_fn.*;

public interface DeducedBaseEvaFunction extends DeducedEvaNode {
	IEvaFunctionBase.BaseEvaFunction_Reactive reactive();

	IEvaFunctionBase getCarrier();

	OS_Module __accessLangModule();

	WhyNotGarish_Function getWhyNotGarishFunction(GenerateC aGc);
}
