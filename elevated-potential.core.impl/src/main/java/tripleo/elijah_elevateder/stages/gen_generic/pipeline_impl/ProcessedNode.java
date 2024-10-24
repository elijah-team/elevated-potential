package tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateFiles;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResultEnv;

public interface ProcessedNode {
	boolean isContainerNode();

	boolean matchModule(OS_Module aMod);

	void processClassMap(GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen);

	void processConstructors(GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen);

	void processContainer(GenerateFiles aGgc, GenerateResultEnv aFileGen);

	void processFunctions(GenerateFiles ggc, final @NotNull GenerateResultEnv aFileGen);
}
