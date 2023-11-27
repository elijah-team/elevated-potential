/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.DebugFlags;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleList;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 8/21/21 10:16 PM
 */
public class EvaPipeline extends PipelineMember implements AccessBus.AB_LgcListener {
	private final          CompilationEnclosure       ce;
	private final @NotNull IPipelineAccess            pa;
	private final @NotNull DefaultGenerateResultSink  grs;
	private final @NotNull DoubleLatch<List<EvaNode>> latch2;
	private final @NotNull List<FunctionStatement>    functionStatements = new ArrayList<>();
	private                CB_Output                  _processOutput;
	private                CR_State                   _processState;
	@SuppressWarnings("unused")
	private                List<EvaNode>              _lgc;
	@SuppressWarnings("unused")
	private                PipelineLogic              pipelineLogic;


	@Contract(pure = true)
	public EvaPipeline(@NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;

		//

		ce = pa.getCompilationEnclosure();

		//

		pa.subscribePipelineLogic(aPl -> pipelineLogic = aPl);
		pa.subscribe_lgc(aLgc -> _lgc = aLgc);

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
		int y=2;

		for (EvaNode evaNode : nodesThatWereProcesed) {
			_processOutput.logProgress(160, "EvaPipeline.recieve >> " + evaNode);
		}

		nodesThatWereProcesed.forEach(this::nodeToOutputFileDump);
		functionStatements.forEach(this::functionStatementToOutputFileDump);

		final CompilationEnclosure compilationEnclosure = pa.getCompilationEnclosure();

		compilationEnclosure.getPipelineAccessPromise().then((pa) -> {
			final var env = new GN_GenerateNodesIntoSinkEnv(
					nodes,
					grs,
					(EIT_ModuleList) pa.pipelineLogic().mods(),
					compilationEnclosure.getCompilationAccess().testSilence(),
					pa.getAccessBus().gr,
					pa,
					compilationEnclosure
			);

			_processOutput.logProgress(117, "EvaPipeline >> GN_GenerateNodesIntoSink");
			pa.notate(Provenance.EvaPipeline__lgc_slot, env);
		});
	}

	private void functionStatementToOutputFileDump(final FunctionStatement functionStatement) {
		final String         filename = functionStatement.getFilename(pa);
		final EG_Statement   seq      = EG_Statement.of(functionStatement.getText(), EX_Explanation.withMessage("dump2"));
		if (DebugFlags.writeDumps) {
			final var cot = ce.getCompilation().getOutputTree();
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
	public void run(final CR_State aSt, final CB_Output aOutput) {
		_processState  = aSt;
		_processOutput = aOutput;

		latch2.notifyLatch(true);
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}

//	public void simpleUsageExample() {
//		Parseable pbr = Parsers.newParseable("{:x 1, :y 2}");
//		Parser    p = Parsers.newParser(defaultConfiguration());
//		Map<?, ?> m = (Map<?, ?>) p.nextValue(pbr);
////		assertEquals(m.get(newKeyword("x")), 1L);
////		assertEquals(m.get(newKeyword("y")), 2L);
////		assertEquals(Parser.END_OF_INPUT, p.nextValue(pbr));
//	}

}

//
//
//
