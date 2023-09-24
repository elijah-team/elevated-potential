package tripleo.elijah.comp;

import antlr.*;
import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.types.*;
import tripleo.elijah.lang2.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.*;

public interface Compilation {
	CK_ObjectTree getObjectTree();

	CIS _cis();

	// void fakeFlow(List<CompilerInput> aInputs, CompilationFlow aFlow);

	CompilerBeginning beginning(final CompilationRunner compilationRunner);

	CompilationConfig cfg();

	CompFactory con();

	int errorCount();

	void feedCmdLine(@NotNull List<String> args) throws Exception;

	void feedInputs(@NotNull List<CompilerInput> inputs, CompilerController controller);

	Operation2<WorldModule> findPrelude(String prelude_name);

	Map<String, CompilerInstructions> fn2ci();

	IPipelineAccess get_pa();

	CompilationClosure getCompilationClosure();

	CompilationEnclosure getCompilationEnclosure();

	String getCompilationNumberString();

	CompilerInputListener getCompilerInputListener();

	ErrSink getErrSink();

	@NotNull
	FluffyComp getFluffy();

	@Contract(pure = true)
	List<CompilerInput> getInputs();

	EIT_InputTree getInputTree();

	IO getIO();

	@NotNull
	EOT_OutputTree getOutputTree();

	OS_Package getPackage(@NotNull Qualident pkg_name);

	String getProjectName();

	CompilerInstructions getRootCI();

	@NotNull
	Operation<Ok> hasInstructions(List<CompilerInstructions> cis);

	void hasInstructions(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

	@Deprecated
	int instructionCount();

	boolean isPackage(@NotNull String pkg);

	OS_Package makePackage(Qualident pkg_name);

	ModuleBuilder moduleBuilder();

	IPipelineAccess pa();

	CP_Paths paths();

	void pushItem(CompilerInstructions aci);

	Finally reports();

	void set_pa(IPipelineAccess a_pa);

	void setIO(IO io);

	void setRootCI(CompilerInstructions aRoot);

	void subscribeCI(Observer<CompilerInstructions> aCio);

	USE use();

	void use(@NotNull CompilerInstructions compilerInstructions, boolean do_out);

	LivingRepo world();
}
