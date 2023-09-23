package tripleo.elijah.comp.i;

import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.output.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;
import java.util.function.*;

public interface IPipelineAccess {
	void _setAccessBus(AccessBus ab);

	void addFunctionStatement(EvaPipeline.FunctionStatement aFunctionStatement);

	void addLog(ElLog aLOG);

	void addOutput(NG_OutputItem aO);

	AccessBus getAccessBus();

	Compilation getCompilation();

	CompilationEnclosure getCompilationEnclosure();

	List<CompilerInput> getCompilerInput();

	void setCompilerInput(List<CompilerInput> aInputs);

	GenerateResultSink getGenerateResultSink();

	//List<NG_OutputItem> getOutputs();

	DeferredObject/* Promise */<PipelineLogic, Void, Void> getPipelineLogicPromise();

	ProcessRecord getProcessRecord();

	WritePipeline getWitePipeline();

	void notate(Provenance provenance, GN_Notable aNotable);

	PipelineLogic pipelineLogic();

	void registerNodeList(DoneCallback<List<EvaNode>> done);

	void setEvaPipeline(@NotNull EvaPipeline agp);

	void setGenerateResultSink(GenerateResultSink aGenerateResultSink);

	void setNodeList(List<EvaNode> aEvaNodeList);

	void setWritePipeline(WritePipeline aWritePipeline);

	void activeFunction(BaseEvaFunction aEvaFunction);

	void activeClass(EvaClass aEvaClass);

	void activeNamespace(EvaNamespace aEvaNamespace);

	List<EvaNamespace> getActiveNamespaces();

	List<BaseEvaFunction> getActiveFunctions();

	List<EvaClass> getActiveClasses();

	void _send_GeneratedClass(EvaNode aClass);

	void waitGenC(OS_Module mod, Consumer<GenerateC> aCb);

	void resolveWaitGenC(OS_Module mod, GenerateC gc);

	void install_notate(Provenance aProvenance, Class<? extends GN_Notable> aRunClass, Class<? extends GN_Env> aEnvClass);

	void notate(Provenance aProvenance, GN_Env aPlRun2);

	void resolvePipelinePromise(PipelineLogic aPipelineLogic);
}
