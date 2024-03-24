package tripleo.elijah_prolific.v;

import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResult;
import tripleo.elijah_durable_elevated.elijah.stages.gen_generic.GenerateResultItem;

import java.io.PrintStream;

public class V {
	public static void asv(final e aE, final String aKey) {
		System.err.println("{{V.asv}} " + aE + " " + aKey);
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
			System.err.println("{{V.gr}} " + ty + " " + ou + " " + ns);
		}
	}

	public static void exit() {
		System.err.println("{{V.exit}}");
	}

	public enum e {f202_writing_logs, _putSeq, DT2_1785, d399_147, IO_openWrite, WMP_write_lsp, WMP_write_prelude, WMP_write_root, WP_write_files, DT2_2304}
}
