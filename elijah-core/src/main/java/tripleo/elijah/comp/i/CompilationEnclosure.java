package tripleo.elijah.comp.i;

import io.reactivex.rxjava3.annotations.*;
import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.impl.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.reactive.*;
import tripleo.elijah.pre_world.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.generate.*;
import tripleo.elijah.stages.inter.*;
import tripleo.elijah.stages.write_stage.pipeline_impl.*;
import tripleo.elijah.world.i.*;

import java.util.*;

public interface CompilationEnclosure {
	void addEntryPoint(@NotNull Mirror_EntryPoint aMirrorEntryPoint, IClassGenerator dcg);

	void addModuleListener(ModuleListener aModuleListener);

	@NotNull ModuleThing addModuleThing(OS_Module aMod);

	void addReactive(@NotNull Reactivable r);

	void addReactive(@NotNull Reactive r);

	void addReactiveDimension(ReactiveDimension aReactiveDimension);

	void AssertOutFile(@NotNull NG_OutputRequest aOutputRequest);

	@NotNull Promise<AccessBus, Void, Void> getAccessBusPromise();

	@Contract(pure = true)
	CB_Output getCB_Output();

	@Contract(pure = true)
	Compilation getCompilation();

	@Contract(pure = true)
	@NotNull ICompilationAccess getCompilationAccess();

	@Contract(pure = true)
	ICompilationBus getCompilationBus();

	// @Contract(pure = true) //??
	CompilationClosure getCompilationClosure();

	@Contract(pure = true)
	CompilerDriver getCompilationDriver();

	@Contract(pure = true)
	CompilationRunner getCompilationRunner();

	@Contract(pure = true)
	List<CompilerInput> getCompilerInput();

	ModuleThing getModuleThing(OS_Module aMod);

	@Contract(pure = true)
	IPipelineAccess getPipelineAccess();

	@Contract(pure = true)
	@NotNull Promise<IPipelineAccess, Void, Void> getPipelineAccessPromise();

	@Contract(pure = true)
	PipelineLogic getPipelineLogic();

	void logProgress(@NotNull CompProgress aCompProgress, Object x);

	void noteAccept(@NotNull WorldModule aWorldModule);

	@NonNull DefaultCompilationEnclosure.OFA OutputFileAsserts();

	void reactiveJoin(Reactive aReactive);

	void setCompilationAccess(@NotNull ICompilationAccess aca);

	void setCompilationBus(ICompilationBus aCompilationBus);

	void setCompilationRunner(CompilationRunner aCompilationRunner);

	void setCompilerDriver(CompilerDriver aCompilerDriver);

	void setCompilerInput(List<CompilerInput> aInputs);

	void setPipelineLogic(PipelineLogic aPipelineLogic);

	void AssertOutFile_Class(OutputStrategyC.OSC_NFC aNfc, NG_OutputRequest aOutputRequest);

	void AssertOutFile_Function(OutputStrategyC.OSC_NFF aNff, NG_OutputRequest aOutputRequest);

	void AssertOutFile_Namespace(OutputStrategyC.OSC_NFN aNfn, NG_OutputRequest aOutputRequest);

	void _resolvePipelineAccessPromise(IPipelineAccess aPa);
}
