package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.CK_Monitor;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.chewtoy.PW_CompilerController;
import tripleo.elijah.comp.chewtoy.Startable;
import tripleo.elijah.comp.local.CX_ParseElijahFile;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.comp_model.CM_UleLog;
import tripleo.elijah.nextgen.inputtree.EIT_InputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.world.i.LivingRepo;
import tripleo.elijah.world.i.WorldModule;

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

	Startable askConcurrent(Runnable aRunnable, final String aThreadName);

	EOT_OutputTree createOutputTree();

	EIT_InputTree createInputTree();

	CX_ParseElijahFile.ElijahSpecReader defaultElijahSpecReader(CP_Path aLocalPrelude);

	@NotNull CK_Monitor createCkMonitor();

	@NotNull PW_CompilerController createPwController(CompilationImpl aCompilation);

	@NotNull Finally_ createFinally();

	@NotNull LivingRepo getLivingRepo();

	CompilerInputMaster createCompilerInputMaster();

	CM_UleLog getULog();

	ILazyCompilerInstructions createLazyCompilerInstructions(CompilerInput aCarrier);
}
