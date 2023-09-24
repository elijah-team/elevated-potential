package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;

import java.io.*;
import java.util.*;

public interface CompFactory {
	CompilerBeginning createBeginning(CompilationRunner aCompilationRunner);

	ICompilationAccess createCompilationAccess();

	ICompilationBus createCompilationBus();

	InputRequest createInputRequest(File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp);

	EIT_ModuleInput createModuleInput(OS_Module aModule);

	Qualident createQualident(List<String> sl);
}
