package tripleo.elijah.nextgen.inputtree;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.nextgen.model.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.work.*;

import java.util.*;
import java.util.function.*;

public interface EIT_ModuleInput extends EIT_Input {
	@NotNull SM_Module computeSourceModel();

	void doGenerate(List<EvaNode> nodes,
					WorkManager wm,
					@NotNull Consumer<GenerateResult> resultConsumer,
					CompilationEnclosure ce);

	@Override
	@NotNull EIT_InputType getType();
}
