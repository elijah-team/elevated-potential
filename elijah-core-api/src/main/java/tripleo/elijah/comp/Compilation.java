package tripleo.elijah.comp;

import io.reactivex.rxjava3.core.Observer;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.pn.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.deduce.fluffy.i.*;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.*;

public interface Compilation {
    static ElLog.@NotNull Verbosity gitlabCIVerbosity() {
        final boolean gitlab_ci = isGitlab_ci();
        return gitlab_ci ? ElLog.Verbosity.SILENT : ElLog.Verbosity.VERBOSE;
    }

    static boolean isGitlab_ci() {
        return System.getenv("GITLAB_CI") != null;
    }

//	CompilerBeginning beginning(final CompilationRunner compilationRunner);

    CK_ObjectTree getObjectTree();

    CIS _cis();

    CompilationConfig cfg();

    CompFactory con();

    int errorCount();

    void feedCmdLine(@NotNull List<String> args) throws Exception;

    void feedInputs(@NotNull List<CompilerInput> inputs, CompilerController controller);

    Operation2<WorldModule> findPrelude(String prelude_name);

    IPipelineAccess get_pa();

    void set_pa(IPipelineAccess a_pa);

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

    void setIO(IO io);

    @NotNull
    EOT_OutputTree getOutputTree();

    OS_Package getPackage(@NotNull Qualident pkg_name);

    String getProjectName();

    CompilerInstructions getRootCI();

    void setRootCI(CompilerInstructions aRoot);

    Operation<Ok> hasInstructions(@NotNull List<CompilerInstructions> cis, @NotNull IPipelineAccess pa);

    @Deprecated
    int instructionCount();

    boolean isPackage(@NotNull String pkg);

    OS_Package makePackage(Qualident pkg_name);

    ModuleBuilder moduleBuilder();

    IPipelineAccess pa();

    CP_Paths paths();

    void pushItem(CompilerInstructions aci);

    Finally reports();

    void subscribeCI(Observer<CompilerInstructions> aCio);

    void use(@NotNull CompilerInstructions compilerInstructions, USE.USE_Reasoning aReasoning);

    LivingRepo world();

    ElijahCache use_elijahCache();

    void pushWork(PW_PushWork aInstance, final PN_Ping aPing);

    enum CompilationAlways {
        ;

        @NotNull
        public static String defaultPrelude() {
            return "c";
        }

        public enum Tokens {
            ;
            public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken
                    .makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
            public static final DriverToken COMPILATION_RUNNER_START        = DriverToken
                    .makeToken("COMPILATION_RUNNER_START");
        }
    }

    class CompilationConfig {
        public boolean do_out;
        public boolean showTree = false;
        public boolean silent = false;
        public @NotNull Stages stage = Stages.O; // Output
    }
}
