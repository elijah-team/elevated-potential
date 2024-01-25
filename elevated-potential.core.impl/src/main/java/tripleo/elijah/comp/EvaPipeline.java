/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.DebugFlags;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.CB_OutputString;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.internal.Provenance;
import tripleo.elijah.comp.internal_move_soon.CompilationEnclosure;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSink;
import tripleo.elijah.comp.notation.GN_GenerateNodesIntoSinkEnv;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.lang.i.FunctionDef;
import tripleo.elijah.lang.i.OS_Element2;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.DoubleLatch;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.util.Ok;

import java.util.*;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 8/21/21 10:16 PM
 */
public class EvaPipeline extends PipelineMember implements AccessBus.AB_LgcListener {
	private final          CompilationEnclosure      ce;
	private final @NotNull IPipelineAccess            pa;
	private final @NotNull DefaultGenerateResultSink  grs;
	private final @NotNull DoubleLatch<List<EvaNode>> latch2;
	private final @NotNull List<FunctionStatement> functionStatements = new ArrayList<>();
	private CB_Output               _processOutput;
	private Object/*CR_State*/      _processState;
	@SuppressWarnings("unused")
	private List<EvaNode> _lgc;
	@SuppressWarnings("unused")
	private                PipelineLogic              pipelineLogic;


	@Contract(pure = true)
	public EvaPipeline(@NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;

		//

		ce = pa.getCompilationEnclosure();

		//

		pa.subscribePipelineLogic(aPl -> pipelineLogic = aPl);
		//pa.subscribe_lgc(aLgc -> _lgc = aLgc);

		//

		latch2 = new DoubleLatch<List<EvaNode>>(this::lgc_slot);

		//

		grs = new DefaultGenerateResultSink(pa);
		pa.registerNodeList(att -> latch2.notifyData(att));
		pa.setGenerateResultSink(grs);

		pa.setEvaPipeline(this);

		pa.install_notate(Provenance.EvaPipeline__lgc_slot, GN_GenerateNodesIntoSink.class, GN_GenerateNodesIntoSinkEnv.class);
	}

	@Override
	public void lgc_slot(final @NotNull List<EvaNode> aLgc1) {
		var nodesThatWereProcesed = new ArrayList<>(aLgc1);

		final List<ProcessedNode> nodes = processLgc(nodesThatWereProcesed);
		int                       y     =2;

		for (EvaNode evaNode : nodesThatWereProcesed) {
			logProgress(160, "EvaPipeline.recieve >> " + evaNode);
		}

		nodesThatWereProcesed.forEach(this::nodeToOutputFileDump);
		functionStatements.forEach(this::functionStatementToOutputFileDump);

		final CompilationEnclosure compilationEnclosure = pa.getCompilationEnclosure();

		compilationEnclosure.getPipelineAccessPromise().then((pa) -> {
			final var env = new GN_GenerateNodesIntoSinkEnv(
					nodes,
					grs,
					//ce.getCompilation().world().modules(),
					compilationEnclosure.getCompilationAccess().testSilence(),
					pa.getAccessBus().gr,
					compilationEnclosure
			);

			logProgress(117, "EvaPipeline >> GN_GenerateNodesIntoSink");
			pa.notate(Provenance.EvaPipeline__lgc_slot, env);
		});
	}

	private void logProgress(final int code, final String message) {
		_processOutput.logProgress(code, message);
	}

	private void functionStatementToOutputFileDump(final FunctionStatement functionStatement) {
		final String       filename = functionStatement.getFilename(pa);
		final EG_Statement seq      = EG_Statement.of(functionStatement.getText(), EX_Explanation.withMessage("dump2"));
		if (DebugFlags.writeDumps) {
			final var            cot = ce.getCompilation().getOutputTree();
			final EOT_OutputFile off = new EOT_OutputFileImpl(List_of(), filename, EOT_OutputType.DUMP, seq);
			cot.add(off);
		}
	}

	private void nodeToOutputFileDump(final EvaNode evaNode) {
		EOT_FileNameProvider filename1;
		String               filename = null;
		final StringBuffer   sb       = new StringBuffer();

		if (evaNode instanceof EvaClass aEvaClass) {
			filename = "C_" + aEvaClass.getCode() + aEvaClass.getName();
			sb.append("CLASS %d %s\n".formatted(aEvaClass.getCode(), aEvaClass.getName()));
			for (EvaContainer.VarTableEntry varTableEntry : aEvaClass.varTable) {
				sb.append("MEMBER %s %s".formatted(varTableEntry.nameToken, varTableEntry.varType));
			}
			for (Map.Entry<FunctionDef, EvaFunction> functionEntry : aEvaClass.functionMap.entrySet()) {
				EvaFunction v = functionEntry.getValue();
				sb.append("FUNCTION %d %s\n".formatted(v.getCode(), v.getFD().getNameNode().getText()));

				pa.activeFunction(v);
			}

			filename1 = new EOT_FileNameProvider() {
				@Override
				public String getFilename() {
					var filename2 = "C_" + aEvaClass.getCode() + aEvaClass.getName();
					return filename2;
				}
			};

			pa.activeClass(aEvaClass);
		} else if (evaNode instanceof EvaNamespace aEvaNamespace) {
			filename = "N_" + aEvaNamespace.getCode() + aEvaNamespace.getName();
			sb.append("NAMESPACE %d %s\n".formatted(aEvaNamespace.getCode(), aEvaNamespace.getName()));
			for (EvaContainer.VarTableEntry varTableEntry : aEvaNamespace.varTable) {
				sb.append("MEMBER %s %s\n".formatted(varTableEntry.nameToken, varTableEntry.varType));
			}
			for (Map.Entry<FunctionDef, EvaFunction> functionEntry : aEvaNamespace.functionMap.entrySet()) {
				EvaFunction v = functionEntry.getValue();
				sb.append("FUNCTION %d %s\n".formatted(v.getCode(), v.getFD().getNameNode().getText()));
			}

			filename1 = new EOT_FileNameProvider() {
				@Override
				public String getFilename() {
					var filename2 = "N_" + aEvaNamespace.getCode() + aEvaNamespace.getName();
					return filename2;
				}
			};

			pa.activeNamespace(aEvaNamespace);
		} else if (evaNode instanceof EvaFunction evaFunction) {
			int code = evaFunction.getCode();

			if (code == 0) {
				var cr = ce.getPipelineLogic().dp.getCodeRegistrar();
				cr.registerFunction1(evaFunction);

				code = evaFunction.getCode();
				assert code != 0;
			}

			final String functionName = evaFunction.getFunctionName();
			filename = "F_" + code + functionName;

			final int finalCode = code;
			filename1 = new EOT_FileNameProvider() {
				@Override
				public String getFilename() {
					final String functionName = evaFunction.getFunctionName();
					var          filename2    = "F_" + finalCode + functionName;
					return filename2;
				}
			};

			final String str = "FUNCTION %d %s %s\n".formatted(code, functionName,
															   ((OS_Element2) evaFunction.getFD().getParent()).name());
			sb.append(str);
			pa.activeFunction(evaFunction);
		} else {
			throw new IllegalStateException("Can't determine node");
		}

		final EG_Statement   seq = EG_Statement.of(sb.toString(), EX_Explanation.withMessage("dump"));
		if (DebugFlags.writeDumps) {
			final @NotNull EOT_OutputTree cot = pa.getCompilation().getOutputTree();
			final EOT_OutputFile          off = new EOT_OutputFileImpl(List_of(), filename1, EOT_OutputType.DUMP, seq);
			cot.add(off);
		}
	}

	public static @NotNull List<ProcessedNode> processLgc(final @NotNull List<EvaNode> aLgc) {
		final List<ProcessedNode> l = new ArrayList<>();

		for (EvaNode evaNode : aLgc) {
			l.add(new ProcessedNode1(evaNode));
		}

		return l;
	}

	public void addFunctionStatement(final FunctionStatement aFunctionStatement) {
		functionStatements.add(aFunctionStatement);
	}

	public DefaultGenerateResultSink grs() {
		return grs;
	}

	@Override
	public void run(final Ok aSt, final CB_Output aOutput) {
		_processState  = null;
		_processOutput = new CB_Output() {
			private final List<CB_OutputString> _o = new ArrayList<>();

			@Override
			public @NotNull List<CB_OutputString> get() {
				return //_o;
						aOutput.get();
			}

			@Override
			public void logProgress(final int number, final String text) {
				aOutput.logProgress(number, text);
			}

			@Override
			public void print(final String s) {
				aOutput.print(s);
			}

			@Override
			public void logProgress(final Diagnostic aDiagnostic) {
				aOutput.logProgress(aDiagnostic);
			}
		};

		latch2.notifyLatch(Ok.instance());
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}
}

//
//
//
