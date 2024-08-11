package tripleo.elijah_prolific.v;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.nextgen.outputstatement.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.gen_generic.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import static tripleo.elijah.util.Helpers.*;

public class V {
	private static final List<String> logs = new ArrayList<>();

	public static void asv(final e aType, final String aMessage) {
		final String x = "{{V.asv}} " + aType + " " + aMessage;
		addLog(x);
	}

	private static void addLog(final String x) {
		logs.add(x);
		System.err.println(x);
	}

	public static void gri(final GenerateResult gr) {
		final PrintStream stream = System.out;

		for (GenerateResultItem ab : gr.results()) {
//			stream.println(ab.counter);
			final String ty = "" + ab.__ty();
//			stream.println(ty);
			final String ou = ab.output();
//			stream.println(ou);
			final String ns = ab.node().identityString();
//			stream.println(ns);
			final String bt = ab.buffer().getText();
//			stream.println(bt);

			final String x = "{{V.gr}} " + ty + " " + ou + " " + ns;

			addLog(x);
		}
	}

	public static void exit(final Compilation0 aCompilation) {
		final String x = "{{V.exit}}";
		addLog(x);

		final @NotNull EOT_OutputTree ot            = aCompilation.getOutputTree();
		final EG_Naming               naming        = new EG_Naming("placeholder-naming");
		final List<EG_Statement>      statementList = logs.stream().map(EG_SingleStatement::new).collect(Collectors.toList());
		final EG_Statement            statement     = new EG_SequenceStatement(naming, statementList);
		final EOT_OutputFile          off           = new EOT_OutputFileImpl(List_of(), "out", EOT_OutputType.DUMP_LOG, statement);
		ot.add(off);

	}

	public enum e {f202_writing_logs, _putSeq, DT2_1785, d399_147, IO_openWrite, WMP_write_lsp, WMP_write_prelude, WMP_write_root, WP_write_files, CR_State_FinishedPipeline, DT2_2304}
}
