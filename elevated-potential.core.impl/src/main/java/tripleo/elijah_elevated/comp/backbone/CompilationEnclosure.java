package tripleo.elijah_elevated.comp.backbone;

import io.reactivex.rxjava3.annotations.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.nextgen.i.Asseverable;
import tripleo.elijah.comp.nextgen.i.AsseverationLogProgress;
import tripleo.elijah.g.GCompilationEnclosure;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah_fluffy.util.Eventual;
import tripleo.elijah_elevated_durable.comp.EDL_CompilationEnclosure;
import tripleo.elijah_elevated_durable.compilation_bus.EDL_CompilationRunner;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevated_durable.pipelines.PipelineMember;
import tripleo.elijah_elevateder.comp.Compilation;
import tripleo.elijah_elevateder.comp.EDL_AccessBus;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_elevateder.comp.internal.PipelinePlugin;
import tripleo.elijah_elevateder.pre_world.Mirror_EntryPoint;
import tripleo.elijah_elevateder.stages.gen_fn.IClassGenerator;
import tripleo.elijah_elevateder.stages.generate.OutputStrategyC;
import tripleo.elijah_elevateder.stages.inter.ModuleThing;
import tripleo.elijah_elevateder.stages.write_stage.pipeline_impl.NG_OutputRequest;
import tripleo.elijah_elevateder.world.i.WorldModule;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface CompilationEnclosure extends Asseverable, GCompilationEnclosure {
	void addEntryPoint(@NotNull Mirror_EntryPoint aMirrorEntryPoint, IClassGenerator dcg);

	void addModuleListener(ModuleListener aModuleListener);

	@NotNull ModuleThing addModuleThing(OS_Module aMod);

	void addReactive(@NotNull Reactivable r);

	void addReactive(@NotNull Reactive r);

	void addReactiveDimension(ReactiveDimension aReactiveDimension);

	void AssertOutFile(@NotNull NG_OutputRequest aOutputRequest);

	@NotNull Eventual<EDL_AccessBus> getAccessBusPromise();

	@Contract(pure = true)
	CB_Output getCB_Output();

	@Contract(pure = true)
	Compilation getCompilation();

	@Contract(pure = true)
	@NotNull ICompilationAccess getCompilationAccess();

	void setCompilationAccess(@NotNull ICompilationAccess aca);

	@Contract(pure = true)
	ICompilationBus getCompilationBus();

	void setCompilationBus(ICompilationBus aCompilationBus);

	// @Contract(pure = true) //??
	CompilationClosure getCompilationClosure();

	@Contract(pure = true)
	CompilerDriver getCompilationDriver();

	@Contract(pure = true)
	EDL_CompilationRunner getCompilationRunner();

	void setCompilationRunner(EDL_CompilationRunner aCompilationRunner);

	@Contract(pure = true)
	List<CompilerInput> getCompilerInput();

	void setCompilerInput(List<CompilerInput> aInputs);

	ModuleThing getModuleThing(OS_Module aMod);

	@Contract(pure = true)
	IPipelineAccess getPipelineAccess();

	@Contract(pure = true)
	@NotNull Eventual<IPipelineAccess> getPipelineAccessPromise();

	@Contract(pure = true)
	PipelineLogic getPipelineLogic();

	void setPipelineLogic(PipelineLogic aPipelineLogic);

	void logProgress(@NotNull CompProgress aCompProgress, Object x);

	void noteAccept(@NotNull WorldModule aWorldModule);

	@NonNull EDL_CompilationEnclosure.OFA OutputFileAsserts();

	void reactiveJoin(Reactive aReactive);

	void setCompilerDriver(CompilerDriver aCompilerDriver);

	void AssertOutFile_Class(OutputStrategyC.OSC_NFC aNfc, NG_OutputRequest aOutputRequest);

	void AssertOutFile_Function(OutputStrategyC.OSC_NFF aNff, NG_OutputRequest aOutputRequest);

	void AssertOutFile_Namespace(OutputStrategyC.OSC_NFN aNfn, NG_OutputRequest aOutputRequest);

	void _resolvePipelineAccessPromise(IPipelineAccess aPa);

	void waitCompilationRunner(Consumer<EDL_CompilationRunner> ccr);

	@Override
	void logProgress2(CompProgress aCompProgress, AsseverationLogProgress aAsseverationLogProgress);

	CK_Monitor getDefaultMonitor();

	void runStepsNow(CK_Steps aSteps, CK_AbstractStepsContext aStepContext);

	PipelinePlugin getPipelinePlugin(String aPipelineName);

	void addPipelinePlugin(final @NotNull Function<GPipelineAccess, PipelineMember> aCr);

	void addPipelinePlugin(PipelinePlugin aPipelinePlugin);

	void writeLogs();

	void addLog(ElLog aLOG);

	List<ElLog> getLogs();

	EIT_ModuleList getModuleList();
}
