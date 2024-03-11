package tripleo.elijah_durable_elevated.elijah.world.i;

import org.jdeferred2.DoneCallback;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah_durable_elevated.elijah.stages.gen_fn.BaseEvaFunction;

public interface LivingFunction {
	int getCode();

	FunctionDef getElement();

	//void offer(AmazingPart aAp);

	BaseEvaFunction evaNode();

	void codeRegistration(LF_CodeRegistration acr);

	boolean isRegistered();

	void listenRegister(DoneCallback<Integer> aCodeCallback);

}