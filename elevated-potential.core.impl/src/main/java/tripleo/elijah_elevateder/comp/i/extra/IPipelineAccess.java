package tripleo.elijah_elevateder.comp.i.extra;

import org.jdeferred2.DoneCallback;
import org.jdeferred2.impl.DeferredObject;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.graph.i.CK_Steps;
import tripleo.elijah.comp.graph.i.CK_StepsContext;
import tripleo.elijah.comp.notation.GN_Env;
import tripleo.elijah.comp.notation.GN_Notable;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.pipelines.*;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.comp.EDL_AccessBus;
import tripleo.elijah_elevateder.comp.i.ProcessRecord;
import tripleo.elijah_elevateder.comp.internal.Provenance;
import tripleo.elijah_elevateder.nextgen.output.NG_OutputItem;
import tripleo.elijah_elevateder.stages.gen_c.GenerateC;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateFiles;
import tripleo.elijah_elevateder.stages.gen_generic.pipeline_impl.GenerateResultSink;
import tripleo.elijah_elevateder.stages.write_stage.pipeline_impl.WP_Flow;

import java.util.List;
import java.util.function.Consumer;

public interface IPipelineAccess extends GPipelineAccess {
	void _send_GeneratedClass(EvaNode aClass);

	void _setAccessBus(EDL_AccessBus ab);

	void activeClass(EvaClass aEvaClass);

	void activeFunction(BaseEvaFunction aEvaFunction);

	void activeNamespace(EvaNamespace aEvaNamespace);

	void addOutput(NG_OutputItem aO);

	EDL_AccessBus getAccessBus();

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

	void subscribePipelineLogic(EDL_AccessBus.AB_PipelineLogicListener aO);

	void subscribe_lgc(EDL_AccessBus.@NotNull AB_LgcListener aO);
}
