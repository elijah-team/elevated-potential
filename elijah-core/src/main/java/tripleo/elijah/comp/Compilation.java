package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.deduce.*;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.*;
import java.util.function.*;

public interface Compilation {
	static ElLog.@NotNull Verbosity gitlabCIVerbosity() {
		final boolean gitlab_ci = isGitlab_ci();
		return gitlab_ci ? ElLog.Verbosity.SILENT : ElLog.Verbosity.VERBOSE;
	}

	static boolean isGitlab_ci() {
		return System.getenv("GITLAB_CI") != null;
	}

	// TODO remove this 04/20
	void addFunctionMapHook(IFunctionMapHook aFunctionMapHook);

	CompilationEnclosure getCompilationEnclosure();

	void setCompilationEnclosure(CompilationEnclosure aCompilationEnclosure);

	void addModule__(@NotNull OS_Module module, @NotNull String fn);

	int compilationNumber();

	CompFactory con();

	void eachModule(Consumer<WorldModule> object);

	int errorCount();

	void feedInputs(@NotNull List<CompilerInput> inputs, CompilerController controller);

	//void fakeFlow(List<CompilerInput> aInputs, CompilationFlow aFlow);

	void feedCmdLine(@NotNull List<String> args) throws Exception;

	CompilationClosure getCompilationClosure();

	List<ClassStatement> findClass(String aClassName);

	Operation2<WorldModule> findPrelude(String prelude_name);

	IPipelineAccess get_pa();

	@NotNull FluffyComp getFluffy();

	@Contract(pure = true)
	List<CompilerInput> getInputs();

	String getCompilationNumberString();

	ErrSink getErrSink();

	IO getIO();

	void setIO(IO io);

	OS_Package getPackage(@NotNull Qualident pkg_name);

	String getProjectName();

	@NotNull Operation<Ok> hasInstructions(List<CompilerInstructions> cis);

	void hasInstructions(@NotNull List<CompilerInstructions> cis,
						 @NotNull IPipelineAccess pa);

	@Deprecated
	int instructionCount();

	boolean isPackage(@NotNull String pkg);

	OS_Package makePackage(Qualident pkg_name);

	ModuleBuilder moduleBuilder();

	IPipelineAccess pa();

	void set_pa(IPipelineAccess a_pa);

	void pushItem(CompilerInstructions aci);

	void subscribeCI(Observer<CompilerInstructions> aCio);

	void use(@NotNull CompilerInstructions compilerInstructions, boolean do_out);

	LivingRepo world();

	LivingRepo livingRepo();

	CP_Paths paths();

	EIT_InputTree getInputTree();

	@NotNull EOT_OutputTree getOutputTree();

	List<OS_Module> modules();

	CompilationConfig cfg();

	CompilerBeginning beginning(final CompilationRunner compilationRunner);

	CompilerInputListener getCompilerInputListener();

	enum CompilationAlways {
		;

		@NotNull
		public static String defaultPrelude() {
			return "c";
		}

		public enum Tokens {
			;
			public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken.makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
			public static final DriverToken COMPILATION_RUNNER_START        = DriverToken.makeToken("COMPILATION_RUNNER_START");
		}
	}

	class CompilationConfig {
		public          boolean do_out;
		public          boolean showTree = false;
		public          boolean silent   = false;
		public @NotNull Stages  stage    = Stages.O; // Output
	}

	Map<String, CompilerInstructions> fn2ci();

	USE use();

	CIS _cis();
}
