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
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.internal_move_soon.*;
import tripleo.elijah.comp.notation.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.stages.gen_generic.*;
import tripleo.elijah.stages.gen_generic.pipeline_impl.*;
import tripleo.elijah.stages.instructions.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Created 8/21/21 10:16 PM
 */
public class EvaPipeline extends PipelineMember implements AccessBus.AB_LgcListener {
	public static class FunctionStatement implements EG_Statement {
		private final IEvaFunctionBase evaFunction;
		private String filename;

		public FunctionStatement(final IEvaFunctionBase aEvaFunction) {
			evaFunction = aEvaFunction;
		}

		@Override
		public @NotNull EX_Explanation getExplanation() {
			return EX_Explanation.withMessage("FunctionStatement");
		}

		public @NotNull String getFilename(@NotNull IPipelineAccess pa) {
			// HACK 07/07 register if not registered
			EvaFunction v = (EvaFunction) evaFunction;
			int code = v.getCode();

			var ce = pa.getCompilationEnclosure();

			if (code == 0) {
				var cr = ce.getPipelineLogic().dp.getCodeRegistrar();
				cr.registerFunction1(v);

				code = v.getCode();
				assert code != 0;
			}

			filename = "F_" + evaFunction.getCode() + evaFunction.getFunctionName();
			return filename;
		}

		@Override
		public @NotNull String getText() {
			final StringBuilder sb = new StringBuilder();

			final String str = "FUNCTION %d %s %s\n".formatted(evaFunction.getCode(), evaFunction.getFunctionName(),
					((OS_Element2) evaFunction.getFD().getParent()).name());
			sb.append(str);

			final EvaFunction gf = (EvaFunction) evaFunction;

			sb.append("Instructions \n");
			for (Instruction instruction : (gf).instructionsList) {
				sb.append("\t" + instruction + "\n");
			}
			{
				// EvaFunction.printTables(gf);
				{
					for (FormalArgListItem formalArgListItem : gf.getFD().getArgs()) {
						sb.append("ARGUMENT " + formalArgListItem.name() + " " + formalArgListItem.typeName() + "\n");
					}
					sb.append("VariableTable \n");
					for (VariableTableEntry variableTableEntry : gf.vte_list) {
						sb.append("\t" + variableTableEntry + "\n");
					}
					sb.append("ConstantTable \n");
					for (ConstantTableEntry constantTableEntry : gf.cte_list) {
						sb.append("\t" + constantTableEntry + "\n");
					}
					sb.append("ProcTable     \n");
					for (ProcTableEntry procTableEntry : gf.prte_list) {
						sb.append("\t" + procTableEntry + "\n");
					}
					sb.append("TypeTable     \n");
					for (TypeTableEntry typeTableEntry : gf.tte_list) {
						sb.append("\t" + typeTableEntry + "\n");
					}
					sb.append("IdentTable    \n");
					for (IdentTableEntry identTableEntry : gf.idte_list) {
						sb.append("\t" + identTableEntry + "\n");
					}
				}
			}

			return sb.toString();
		}
	}

	private final          CompilationEnclosure ce;
	private final @NotNull IPipelineAccess      pa;
	private final @NotNull DefaultGenerateResultSink grs;
	private final @NotNull DoubleLatch<List<EvaNode>> latch2;
	private List<EvaNode> _lgc;
	private final @NotNull List<FunctionStatement> functionStatements = new ArrayList<>();
	private PipelineLogic pipelineLogic;
	private CB_Output     _processOutput;

	private CR_State _processState;

	@Contract(pure = true)
	public EvaPipeline(@NotNull IPipelineAccess pa0) {
		pa = pa0;

		//

		ce = pa0.getCompilationEnclosure();

		//

		final AccessBus ab = pa.getAccessBus();
		ab.subscribePipelineLogic(aPl -> pipelineLogic = aPl);
		ab.subscribe_lgc(aLgc -> _lgc = aLgc);

		//

		latch2 = new DoubleLatch<List<EvaNode>>(this::lgc_slot);

		//

		grs = new DefaultGenerateResultSink(pa);
		pa.registerNodeList(att -> latch2.notifyData(att));
		pa.setGenerateResultSink(grs);

		pa.setEvaPipeline(this);

		pa.install_notate(Provenance.EvaPipeline__lgc_slot, GN_GenerateNodesIntoSink.class,
				GN_GenerateNodesIntoSinkEnv.class);
	}

	public void addFunctionStatement(final FunctionStatement aFunctionStatement) {
		functionStatements.add(aFunctionStatement);
	}

	public DefaultGenerateResultSink grs() {
		return grs;
	}

	@Override
	public void lgc_slot(final @NotNull List<EvaNode> aLgc1) {
		var aLgc = new ArrayList<>(aLgc1);

		final List<ProcessedNode> nodes = processLgc(aLgc);

		for (EvaNode evaNode : aLgc) {
			_processOutput.logProgress(160, "EvaPipeline.recieve >> " + evaNode);
		}

		EOT_OutputFileImpl.FileNameProvider filename1;

		//final @NotNull EOT_OutputTree cot = pa.getCompilation().getOutputTree();
		for (EvaNode evaNode : aLgc) {
			String filename = null;
			final StringBuffer sb = new StringBuffer();

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

				filename1 = new EOT_OutputFileImpl.FileNameProvider() {
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

				filename1 = new EOT_OutputFileImpl.FileNameProvider() {
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
				filename1 = new EOT_OutputFileImpl.FileNameProvider() {
					@Override
					public String getFilename() {
						final String functionName = evaFunction.getFunctionName();
						var filename2 = "F_" + finalCode + functionName;
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

			final EG_Statement seq = EG_Statement.of(sb.toString(), EX_Explanation.withMessage("dump"));
			final EOT_OutputFile off = new EOT_OutputFileImpl(List_of(), filename1, EOT_OutputType.DUMP, seq);
			// cot.add(off);
		}

		for (FunctionStatement functionStatement : functionStatements) {
			final String filename = functionStatement.getFilename(pa);
			final EG_Statement seq = EG_Statement.of(functionStatement.getText(), EX_Explanation.withMessage("dump2"));
			final EOT_OutputFile off = new EOT_OutputFileImpl(List_of(), filename, EOT_OutputType.DUMP, seq);
			// cot.add(off);
		}

		final CompilationEnclosure compilationEnclosure = pa.getCompilationEnclosure();

		compilationEnclosure.getPipelineAccessPromise().then((pa) -> {
			final var env = new GN_GenerateNodesIntoSinkEnv(
					nodes,
					grs,
					null, //pa.pipelineLogic().mods(),
					compilationEnclosure.getCompilationAccess().testSilence(),
					pa.getAccessBus().gr,
					pa,
					compilationEnclosure
			);

			_processOutput.logProgress(117, "EvaPipeline >> GN_GenerateNodesIntoSink");
			pa.notate(Provenance.EvaPipeline__lgc_slot, env);
		});
	}

	public static @NotNull List<ProcessedNode> processLgc(final @NotNull List<EvaNode> aLgc) {
		final List<ProcessedNode> l = new ArrayList<>();

		for (EvaNode evaNode : aLgc) {
			l.add(new ProcessedNode1(evaNode));
		}

		return l;
	}

//	@Override
	public void run(final CR_State aSt, final CB_Output aOutput) {
		_processState = aSt;
		_processOutput = aOutput;

		latch2.notifyLatch(true);
	}

//	@Override
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
