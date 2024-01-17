package tripleo.elijah.comp.nextgen.inputtree;

import org.jetbrains.annotations.NotNull;

import tripleo.elijah.g.*;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.inputtree.EIT_InputType;
import tripleo.elijah.nextgen.model.SM_Module;

public interface EIT_ModuleInput extends EIT_Input {
	@NotNull SM_Module computeSourceModel();

	void doGenerate(GModuleGenerationRequest r);

	@Override
	@NotNull EIT_InputType getType();
}
