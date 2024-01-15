package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.internal.CR_State;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.i.CP_RootType;
import tripleo.elijah.comp.nextgen.pw.PW_Controller;
import tripleo.elijah.comp.nextgen.pw.PW_signalCalculateFinishParse;
import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GPipelineMember;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFileImpl;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.stages.logging.LogEntry;

import java.util.ArrayList;
import java.util.List;

import static tripleo.elijah.util.Helpers.List_of;

public class WriteOutputTreePipeline extends PipelineMember implements GPipelineMember {
	private final IPipelineAccess pa;

	public WriteOutputTreePipeline(final @NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;
	}

	private static void addLogs(final @NotNull List<EOT_OutputFile> l, final @NotNull IPipelineAccess aPa) {
		final List<ElLog> logs = aPa.getCompilationEnclosure().getLogs();
		final String      s1   = logs.get(0).getFileName();

		for (final ElLog log : logs) {
			final List<EG_Statement> stmts = new ArrayList<>();

			if (log.getEntries().size() == 0)
				continue; // README Prelude.elijjah "fails" here

			for (final LogEntry entry : log.getEntries()) {
				final String logentry = String.format("[%s] [%tD %tT] %s %s",
				                                      s1,
				                                      entry.time(),
				                                      entry.time(),
				                                      entry.level(),
				                                      entry.message());
				stmts.add(new EG_SingleStatement(logentry + "\n"));
			}

			final EG_SequenceStatement seq      = new EG_SequenceStatement(new EG_Naming("wot.log.seq"), stmts);
			final String               fileName = log.getFileName().replace("/", "~~");
			final EOT_OutputFile       off      = new EOT_OutputFileImpl(List_of(), fileName, EOT_OutputType.LOGS, seq);
			l.add(off);
		}
	}

	@Override
	public void run(final @NotNull CR_State st, final CB_Output aOutput) throws Exception {
		final Compilation          compilation = (Compilation) st.ca().getCompilation();
		final EOT_OutputTree       ot          = compilation.getOutputTree();
		final List<EOT_OutputFile> l           = ot.getList();

		//
		//
		//
		//
		//
		//
		//
		// HACK should be done earlier in process
		//
		//
		//
		//
		//
		//
		//
		addLogs(l, compilation.pa());

		final __PW_Controller__FAKE pwcc = new __PW_Controller__FAKE(compilation);
		PW_signalCalculateFinishParse.instance().handle(pwcc, null);

		final CP_Paths paths = pwcc.paths();
		final CP_Path  r     = paths.outputRoot();

		for (final EOT_OutputFile outputFile : l) {
			final String       path0 = outputFile.getFilename();
			final EG_Statement seq   = outputFile.getStatementSequence();

			CP_Path pp;

			switch (outputFile.getType()) {
				case SOURCES -> {
					pp = r.child("code2").child(path0);

					compilation.reports().addCodeOutput(() -> path0, outputFile);
				}
				case LOGS -> pp = r.child("logs").child(path0);
				case INPUTS, BUFFERS -> pp = r.child(path0);
				case DUMP -> pp = r.child("dump").child(path0);
				case BUILD -> pp = r.child(path0);
				case SWW -> pp = r.child("sww").child(path0);
				default -> throw new IllegalStateException("Unexpected value: " + outputFile.getType());
			}

			// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("106 " + pp);

			paths.addNode(CP_RootType.OUTPUT, ER_Node.of(pp, seq));
		}

		paths.renderNodes();
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}

	public static class __PW_Controller__FAKE implements PW_Controller {
		private final Compilation c;

		public __PW_Controller__FAKE(Compilation aC) {
			c = aC;
		}

		public CP_Paths paths() {
			return c.paths();
		}
	}

	public static class __PW_PushWork__FAKE {

	}
}
