package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.i.extra.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.g.*;
import tripleo.elijah.nextgen.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.logging.*;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class WriteOutputTreePipeline extends PipelineMember implements GPipelineMember {
	private static void addLogs(final @NotNull List<EOT_OutputFile> l, final @NotNull IPipelineAccess aPa) {
		final List<ElLog> logs = aPa.getCompilationEnclosure().getLogs();
		final String s1 = logs.get(0).getFileName();

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

			final EG_SequenceStatement seq = new EG_SequenceStatement(new EG_Naming("wot.log.seq"), stmts);
			final String fileName = log.getFileName().replace("/", "~~");
			final EOT_OutputFile off = new EOT_OutputFileImpl(List_of(), fileName, EOT_OutputType.LOGS, seq);
			l.add(off);
		}
	}

	private final IPipelineAccess pa;

	public WriteOutputTreePipeline(final @NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;
	}

	@Override
	public void run(final @NotNull CR_State st, final CB_Output aOutput) throws Exception {
		final Compilation   compilation = (Compilation) st.ca().getCompilation();
		final EOT_OutputTree ot          = compilation.getOutputTree();
		final List<EOT_OutputFile> l = ot.getList();

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

		final CP_Paths paths = compilation.paths();
		paths.signalCalculateFinishParse(); // TODO maybe move this 06/22
		CP_Path r = paths.outputRoot();

		for (final EOT_OutputFile outputFile : l) {
			final String path0 = outputFile.getFilename();
			final EG_Statement seq = outputFile.getStatementSequence();

			CP_Path pp;

			switch (outputFile.getType()) {
			case SOURCES -> {
				pp = r.child("code2").child(path0);

				compilation.reports().addCodeOutput(()->path0, outputFile);
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

}
