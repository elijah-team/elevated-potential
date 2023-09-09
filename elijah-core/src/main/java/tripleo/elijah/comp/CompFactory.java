package tripleo.elijah.comp;

import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.comp.internal.CompilationRunner;
import tripleo.elijah.comp.internal.CompilerBeginning;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;

import java.io.File;
import java.util.List;

public interface CompFactory {
	EIT_ModuleInput createModuleInput(OS_Module aModule);

	Qualident createQualident(List<String> sl);

	InputRequest createInputRequest(File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp);

	ICompilationAccess createCompilationAccess();

	ICompilationBus createCompilationBus();

	CompilerBeginning createBeginning(CompilationRunner aCompilationRunner);
}
