package tripleo.elijah.comp.i.extra;

import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.output.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.stages.gen_c.GenerateC;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.EvaClass;
import tripleo.elijah.stages.gen_fn.EvaNamespace;
import tripleo.elijah.stages.gen_fn.EvaNode;
import tripleo.elijah.stages.gen_generic.GenerateFiles;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.write_stage.pipeline_impl.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import java.util.*;
import java.util.function.*;

public interface IPipelineAccess extends GPipelineAccess {
	void _send_GeneratedClass(EvaNode aClass);

	void _setAccessBus(AccessBus ab);

	void activeClass(EvaClass aEvaClass);

	void activeFunction(BaseEvaFunction aEvaFunction);

	void activeNamespace(EvaNamespace aEvaNamespace);

	void addOutput(NG_OutputItem aO);

	AccessBus getAccessBus();

	List<EvaClass> getActiveClasses();

	// List<NG_OutputItem> getOutputs();

	List<BaseEvaFunction> getActiveFunctions();

	List<EvaNamespace> getActiveNamespaces();

	Compilation getCompilation();

	CompilationEnclosure getCompilationEnclosure();

	List<CompilerInput> getCompilerInput();

	GenerateResultSink getGenerateResultSink();

	DeferredObject/* Promise */<PipelineLogic, Void, Void> getPipelineLogicPromise();

	ProcessRecord getProcessRecord();

	WritePipeline getWitePipeline();

	void install_notate(Provenance aProvenance, Class<? extends GN_Notable> aRunClass,
			Class<? extends GN_Env> aEnvClass);

	void notate(Provenance aProvenance, GN_Env aPlRun2);

	void notate(Provenance provenance, GN_Notable aNotable);

	PipelineLogic pipelineLogic();

	void registerNodeList(DoneCallback<List<EvaNode>> done);

	void resolvePipelinePromise(PipelineLogic aPipelineLogic);

	void resolveWaitGenC(OS_Module mod, GenerateFiles gc);

	void setCompilerInput(List<CompilerInput> aInputs);

	void setEvaPipeline(@NotNull EvaPipeline agp);

	void setGenerateResultSink(GenerateResultSink aGenerateResultSink);

	void setNodeList(List<EvaNode> aEvaNodeList);

	void setWritePipeline(WritePipeline aWritePipeline);

	void waitGenC(OS_Module mod, Consumer<GenerateC> aCb);

	void finishPipeline(GPipelineMember aPM, WP_Flow.OPS aOps);

	void runStepsNow(CK_Steps aSteps, CK_StepsContext aStepsContext);

	void addFunctionStatement(EG_Statement aStatement);

	void subscribePipelineLogic(AccessBus.AB_PipelineLogicListener aO);

	void subscribe_lgc(AccessBus.@NotNull AB_LgcListener aO);
}
