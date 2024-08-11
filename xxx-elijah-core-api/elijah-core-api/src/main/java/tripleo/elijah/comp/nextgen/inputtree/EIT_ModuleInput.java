package tripleo.elijah.comp.nextgen.inputtree;

import org.jetbrains.annotations.*;

import tripleo.elijah.g.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.model.*;

public interface EIT_ModuleInput extends EIT_Input {
	@NotNull
	SM_Module computeSourceModel();

	void doGenerate(GModuleGenerationRequest r);

	@Override
	@NotNull
	EIT_InputType getType();
}
