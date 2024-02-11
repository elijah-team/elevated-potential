package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.CX_ElijahSpecReader;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.compiler_model.CM_UleLog;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

import java.util.List;

public interface CompFactory {
	ICompilationAccess createCompilationAccess();

	ICompilationBus createCompilationBus();

	ILazyCompilerInstructions createLazyCompilerInstructions(CompilerInput aCarrier);

	WorldModule createWorldModule(OS_Module aModule);

	EIT_ModuleInput createModuleInput(OS_Module aModule);

	EOT_OutputTree createOutputTree();

	EIT_InputTree createInputTree();

	Qualident createQualident(List<String> sl);

	CK_ObjectTree createObjectTree();

	CY_ElijahSpecParser defaultElijahSpecParser(ElijahCache aElijahCache);

	//CY_EzSpecParser defaultEzSpecParser(EzCache aEzCache);

	CX_ElijahSpecReader defaultElijahSpecReader(CP_Path aLocalPrelude);

	@NotNull CK_Monitor createCkMonitor();

	PW_PushWorkQueue createWorkQueue();

	@NotNull PW_CompilerController createPwController(CompilationImpl aCompilation);

	@NotNull Finally createFinally();

	@NotNull LivingRepo getLivingRepo();

	CompilerInputMaster createCompilerInputMaster();

	CM_UleLog getULog(); // sp?

	Startable askConcurrent(StartableI aRunnable);

	interface StartableI {
		void run();

		boolean isSignalled();

		String getThreadName();
	}
}
