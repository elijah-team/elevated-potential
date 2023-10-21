package tripleo.elijah.comp;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.Startable;
import tripleo.elijah.comp.nextgen.pw.PW_PushWorkQueue;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.world.i.WorldModule;

import java.io.File;
import java.util.List;

public interface CompFactory {

	ICompilationAccess createCompilationAccess();

	ICompilationBus createCompilationBus();

	InputRequest createInputRequest(File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp);

	EIT_ModuleInput createModuleInput(OS_Module aModule);

	Qualident createQualident(List<String> sl);

	CK_ObjectTree createObjectTree();

	CY_ElijahSpecParser defaultElijahSpecParser(ElijahCache aElijahCache);

	CY_EzSpecParser defaultEzSpecParser(EzCache aEzCache);

	WorldModule createWorldModule(OS_Module aModule);

	PW_PushWorkQueue createWorkQueue();

	Startable askConcurrent(Runnable aRunnable, final String aThreadName);
}
