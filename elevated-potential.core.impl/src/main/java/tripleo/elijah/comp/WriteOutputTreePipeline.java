package tripleo.elijah.comp;

import tripleo.elijah.comp.i.CB_Output;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.comp.internal.CompilationImpl;

import tripleo.elijah.g.GPipelineAccess;
import tripleo.elijah.g.GPipelineMember;

import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.i.CP_RootType;

import tripleo.elijah.nextgen.outputstatement.EG_Naming;
import tripleo.elijah.nextgen.outputstatement.EG_SequenceStatement;
import tripleo.elijah.nextgen.outputstatement.EG_SingleStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.nextgen.ER_Node;

import tripleo.elijah.stages.logging.ElLog;
import tripleo.elijah.nextgen.comp_model.CM_UleLog;

import io.smallrye.mutiny.tuples.Functions;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.stages.logging.LogEntry;
import tripleo.elijah.util.Ok;

import java.util.*;

import static tripleo.elijah.util.Helpers.*;

public class WriteOutputTreePipeline extends PipelineMember implements GPipelineMember {
	private final int WRITE_OUTPUT_TREE__ADD_NODE_OUTPUT = 106;

	private static void addLogs(final @NotNull Functions.TriConsumer<List, EOT_FileNameProvider, EG_Statement> outputSink, final List<ElLog> logs) {
		final String s1 = logs.get(0).getFileName();

		for (final ElLog log : logs) {
			final List<EG_Statement> stmts = new ArrayList<>();

			if (log.getEntries().isEmpty())
				continue; // README Prelude.elijjah "fails" here

			for (final LogEntry entry : log.getEntries()) {
				final EG_SingleStatement logEntryStatement = getLogEntryStatement(entry, s1);
				stmts.add(logEntryStatement);
			}

			final EG_SequenceStatement seq      = new EG_SequenceStatement(new EG_Naming("wot.log.seq"), ()->log.getEntries().stream().map(entry->getLogEntryStatement(entry, s1)));
			final String               fileName = log.getFileName().replace("/", "~~");
			//final EOT_OutputFile off = new EOT_OutputFileImpl(List_of(), fileName, EOT_OutputType.LOGS, seq);
			outputSink.accept(List_of(), ()->fileName, seq);
		}
	}

	@NotNull
	private static EG_SingleStatement getLogEntryStatement(final LogEntry entry, final String s1) {
		final String logentry = String.format("[%s] [%tD %tT] %s %s",
											  s1,
											  entry.time(),
											  entry.time(),
											  entry.level(),
											  entry.message());
		final EG_SingleStatement logEntryStatement = new EG_SingleStatement(logentry + "\n");
		return logEntryStatement;
	}

	private final IPipelineAccess pa;

	public WriteOutputTreePipeline(final @NotNull GPipelineAccess pa0) {
		pa = (IPipelineAccess) pa0;
	}

	@Override
	public void run(final Ok st, final CB_Output aOutput) throws Exception {
		final Compilation          compilation = pa.getCompilation();
		final EOT_OutputTree       ot = compilation.getOutputTree();
		final List<EOT_OutputFile> l  = ot.getList();

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
		addLogs((x,y,z) -> {
						final EOT_OutputFileImpl off = new EOT_OutputFileImpl(x, y, EOT_OutputType.LOGS, z);
						ot.add(off);
					},
				compilation.getCompilationEnclosure().getLogs());

		final CP_Paths paths = compilation.paths();

		// TODO maybe move this 06/22
		//  24/01/21 If it doesn't have any effect, leave it where it is
		//   but find out the first location where we would like it
		//   to remove the toodo
		paths.signalCalculateFinishParse();



		final CP_Path   outputRoot = paths.outputRoot();
		final CM_UleLog L          = ((CompilationImpl) compilation).con().getULog();

		for (final EOT_OutputFile outputFile : l) {
			final String oldPath = outputFile.getFilename();
			final EG_Statement seq = outputFile.getStatementSequence();

			if (outputFile.getType() == EOT_OutputType.SOURCES) {
				compilation.reports().addCodeOutput(()-> oldPath, outputFile);
			}

			final CP_Path pp = getNewPathForOutput(outputFile, outputRoot, oldPath);

			L.asv(WRITE_OUTPUT_TREE__ADD_NODE_OUTPUT, pp);
			paths.addNode(CP_RootType.OUTPUT, ER_Node.of(pp, seq));
		}

		paths.renderNodes();
	}

	private static CP_Path getNewPathForOutput(final EOT_OutputFile outputFile,
											   final CP_Path rootPath,
											   final String oldPath) {
		final CP_Path pp;
		switch (outputFile.getType()) {
		case SOURCES -> pp = rootPath.child("code2").child(oldPath);
		case LOGS -> pp = rootPath.child("logs").child(oldPath);
		case INPUTS, BUFFERS -> pp = rootPath.child(oldPath);
		case DUMP -> pp = rootPath.child("dump").child(oldPath);
		case BUILD -> pp = rootPath.child(oldPath);
		case SWW -> pp = rootPath.child("sww").child(oldPath);
		default -> throw new IllegalStateException("Unexpected value: " + outputFile.getType());
		}
		return pp;
	}

	@Override
	public String finishPipeline_asString() {
		return this.getClass().toString();
	}
}
