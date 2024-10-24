package tripleo.elijah_elevateder.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.*;
import tripleo.elijah_elevated_durable.comp.EDL_Finally;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevated.comp.pushwork.PW_CompilerController;
import tripleo.elijah_elevated.comp.pushwork.Startable;
import tripleo.elijah_elevated.comp.input.CX_ParseElijahFile;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.comp_model.CM_UleLog;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah_elevateder.world.i.LivingRepo;
import tripleo.elijah_elevateder.world.i.WorldModule;

import java.util.List;

public interface CompFactory {

	ICompilationAccess createCompilationAccess();

	ICompilationBus createCompilationBus();

	EIT_ModuleInput createModuleInput(OS_Module aModule);

	Qualident createQualident(List<String> sl);

	CK_ObjectTree createObjectTree();

	CY_ElijahSpecParser defaultElijahSpecParser(ElijahCache aElijahCache);

	//CY_EzSpecParser defaultEzSpecParser(EzCache aEzCache);

	WorldModule createWorldModule(OS_Module aModule);

	PW_PushWorkQueue createWorkQueue();

	Startable askConcurrent(StartableI aRunnable);

	interface StartableI {
		void run();

		boolean isSignalled();

		String getThreadName();
	}

	EOT_OutputTree createOutputTree();

	EIT_InputTree createInputTree();

	CX_ParseElijahFile.ElijahSpecReader defaultElijahSpecReader(CP_Path aLocalPrelude);

	@NotNull CK_Monitor createCkMonitor();

	@NotNull PW_CompilerController createPwController(EDL_Compilation aCompilation);

	@NotNull EDL_Finally createFinally();

	@NotNull LivingRepo getLivingRepo();

	CompilerInputMaster createCompilerInputMaster();

	CM_UleLog getULog(); // sp?

	ILazyCompilerInstructions createLazyCompilerInstructions(CompilerInput aCarrier);
}
