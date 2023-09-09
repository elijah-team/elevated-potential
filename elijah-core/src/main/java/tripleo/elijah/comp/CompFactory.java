package tripleo.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.LibraryStatementPart;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;

import java.io.File;
import java.util.List;

public interface CompFactory {
	EIT_ModuleInput createModuleInput(OS_Module aModule);

	Qualident createQualident(List<String> sl);

	InputRequest createInputRequest(File aFile, final boolean aDo_out, final @Nullable LibraryStatementPart aLsp);

	@Contract(value = "_ -> new", pure = true)
	@NotNull ICompilationAccess createCompilationAccess();

	ICompilationBus createCompilationBus();

}
