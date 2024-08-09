package tripleo.elijah.world.i;

import org.jdeferred2.*;
import tripleo.elijah.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;

public interface LivingFunction {
	Eventual<Integer> getCode();

	FunctionDef getElement();

	//void offer(AmazingPart aAp);

	BaseEvaFunction evaNode();

	void codeRegistration(LF_CodeRegistration acr);

	boolean isRegistered();

	void listenRegister(DoneCallback<Integer> aCodeCallback);

	void waitDeduced(DoneCallback<DeducedBaseEvaFunction> cb);
}
