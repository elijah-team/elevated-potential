package tripleo.elijah.comp.i.extra;

import org.jdeferred2.*;
import org.jdeferred2.impl.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.output.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.stages.write_stage.pipeline_impl.*;

import java.util.*;
import java.util.function.*;

public interface IPipelineAccess extends GPipelineAccess {
	void _send_GeneratedClass(EvaNode aClass);

	void _setAccessBus(AccessBus ab);

	void activeClass(EvaClass aEvaClass);

	void activeFunction(BaseEvaFunction aEvaFunction);

	void activeNamespace(EvaNamespace aEvaNamespace);

	void addLog(ElLog aLOG);

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

	void resolveWaitGenC(OS_Module mod, GenerateC gc);

	void setCompilerInput(List<CompilerInput> aInputs);

	void setEvaPipeline(@NotNull EvaPipeline agp);

	void setGenerateResultSink(GenerateResultSink aGenerateResultSink);

	void setNodeList(List<EvaNode> aEvaNodeList);

	void setWritePipeline(WritePipeline aWritePipeline);

	void waitGenC(OS_Module mod, Consumer<GenerateC> aCb);

	void finishPipeline(GPipelineMember aPM, WP_Flow.OPS aOps);

	void runStepsNow(CK_Steps aSteps, CK_StepsContext aStepsContext);

	void addFunctionStatement(EG_Statement aStatement);
}
