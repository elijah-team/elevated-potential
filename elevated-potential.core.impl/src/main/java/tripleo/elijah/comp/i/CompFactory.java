package tripleo.elijah.comp.i;

import java.util.*;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.comp.nextgen.inputtree.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.comp_model.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.world.i.*;

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

	@NotNull
	CK_Monitor createCkMonitor();

	@NotNull
	PW_CompilerController createPwController(CompilationImpl aCompilation);

	@NotNull
	Finally_ createFinally();

	@NotNull
	LivingRepo getLivingRepo();

	CompilerInputMaster createCompilerInputMaster();

	CM_UleLog getULog(); // sp?

	ILazyCompilerInstructions createLazyCompilerInstructions(CompilerInput aCarrier);
}
