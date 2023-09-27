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

//	CompilerBeginning beginning(final CompilationRunner compilationRunner);

	CompilationConfig cfg();

	CompFactory con();

	int errorCount();

	void feedCmdLine(@NotNull List<String> args) throws Exception;

	void feedInputs(@NotNull List<CompilerInput> inputs, CompilerController controller);

	Operation2<WorldModule> findPrelude(String prelude_name);

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

	void set_pa(IPipelineAccess a_pa);

	void setIO(IO io);

	void setRootCI(CompilerInstructions aRoot);

	void subscribeCI(Observer<CompilerInstructions> aCio);

	void use(@NotNull CompilerInstructions compilerInstructions, USE.USE_Reasoning aReasoning);

	LivingRepo world();

	static ElLog.@NotNull Verbosity gitlabCIVerbosity() {
		final boolean gitlab_ci = isGitlab_ci();
		return gitlab_ci ? ElLog.Verbosity.SILENT : ElLog.Verbosity.VERBOSE;
	}

	static boolean isGitlab_ci() {
		return System.getenv("GITLAB_CI") != null;
	}

	class PCon {
		public IExpression ExpressionBuilder_build(final IExpression aEe, final ExpressionKind aEk,
		                                           final IExpression aE2) {
			return ExpressionBuilder.build(aEe, aEk, aE2);
		}

		public IExpression newCharLitExpressionImpl(final Token aC) {
			return new CharLitExpressionImpl(aC);
		}

		public CompilerInstructions newCompilerInstructionsImpl() {
			return new CompilerInstructionsImpl();
		}

		public IExpression newDotExpressionImpl(final IExpression aE1, final IdentExpression aE) {
			return new DotExpressionImpl(aE1, aE);
		}

		public ExpressionList newExpressionListImpl() {
			return new ExpressionListImpl();
		}

		public IExpression newFloatExpressionImpl(final Token aF) {
			return new FloatExpressionImpl(aF);
		}

		public GenerateStatement newGenerateStatementImpl() {
			return new GenerateStatementImpl();
		}

		public IExpression newGetItemExpressionImpl(final IExpression aEe, final IExpression aExpr) {
			return new GetItemExpressionImpl(aEe, aExpr);
		}

		public IdentExpression newIdentExpressionImpl(final Token aR1, final String aFoo, final Context aCur) {
			return new IdentExpressionImpl(aR1, aFoo, aCur);
		}

		public LibraryStatementPart newLibraryStatementPartImpl() {
			return new LibraryStatementPartImpl();
		}

		public IExpression newListExpressionImpl() {
			return new ListExpressionImpl();
		}

		public IExpression newNumericExpressionImpl(final Token aN) {
			return new NumericExpressionImpl(aN);
		}

		public OS_Type newOS_BuiltinType(final BuiltInTypes aBuiltInTypes) {
			return new OS_BuiltinType(aBuiltInTypes);
		}

		public ProcedureCallExpression newProcedureCallExpressionImpl() {
			return new ProcedureCallExpressionImpl();
		}

		public Qualident newQualidentImpl() {
			return new QualidentImpl();
		}

		public IExpression newSetItemExpressionImpl(final GetItemExpression aEe, final IExpression aExpr) {
			return new SetItemExpressionImpl(aEe, aExpr);
		}

		public IExpression newStringExpressionImpl(final Token aS) {
			return new StringExpressionImpl(aS);
		}

		public IExpression newSubExpressionImpl(final IExpression aEe) {
			return new SubExpressionImpl(aEe);
		}

		public CiExpressionList newCiExpressionListImpl() {
			return new CiExpressionListImpl();
		}

		public CiProcedureCallExpression newCiProcedureCallExpressionImpl() {
			return new CiProcedureCallExpressionImpl();
		}
	}

	enum CompilationAlways {
		;

		public enum Tokens {
			;
			public static final DriverToken COMPILATION_RUNNER_FIND_STDLIB2 = DriverToken
					.makeToken("COMPILATION_RUNNER_FIND_STDLIB2");
			public static final DriverToken COMPILATION_RUNNER_START = DriverToken
					.makeToken("COMPILATION_RUNNER_START");
		}

		@NotNull
		public static String defaultPrelude() {
			return "c";
		}
	}

	class CompilationConfig {
		public boolean do_out;
		public boolean showTree = false;
		public boolean silent = false;
		public @NotNull Stages stage = Stages.O; // Output
	}
}
