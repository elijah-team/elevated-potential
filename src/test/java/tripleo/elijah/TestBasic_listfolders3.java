package tripleo.elijah;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.ElijahTestCli;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.process.CPX_RunStepLoop;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.stages.gen_c.Emit;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertTrue;
import static tripleo.elijah.util.Helpers.List_of;

public class TestBasic_listfolders3 {
	private void tb_lf3(final ErrSink aErrSink, final EOT_OutputTree aOutputTree, final CK_ObjectTree aObjectTree, ElijahTestCli c) {
		final Finally REPORTS = c.reports();

		assertThat(c.errorCount()).isEqualTo(2);

		assertThat(REPORTS.inputCount()).isEqualTo(2);  // TODO is this correct?

		//[-- Ez CIL change ] CompilerInput{ty=ROOT, inp='test/basic/listfolders3/listfolders3.ez'} ROOT
		final String aaa = "test/basic/import_demo.elijjah";
		final String aab = "test/basic/listfolders3/listfolders3.elijah";
		assertThat(REPORTS.inputFilenames()).containsExactly(aaa, aab);

		final String baa = "/Prelude/Arguments.h";
		final String bab = "/Prelude/Arguments.c";
		final String bac = "/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.c";
		final String bad = "/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.h";
		final String bae = "/listfolders3/Main.c";
		final String baf = "/listfolders3/Main.h";
		assertThat(REPORTS.outputFilenames()).containsExactly(baf, bad , baa, bae, bab, bac);
		assertThat(REPORTS.outputCount()).isEqualTo(6);

		assertTrue(_TB.assertLiveClass("MainLogic", "wpkotlin_c.demo.list_folders", c));

		// TODO fails; assertTrue(assertLiveClass("Main", null, c));
		// TODO fails; assertTrue(assertLiveClass("Arguments", null, c)); // TODO specify lsp/ez Prelude

		// TODO investigate; assertTrue(assertLiveClass("Directory", "std.io", c));
		// TODO investigate; assertTrue(assertLiveClass("List", "std.collections", c)); // TODO specify generic `String +/- FilesystemObject'

		// TODO investigate; assertTrue(assertLiveFunction("Main",  "main", c)); // TODO specify arguments for functions
		// TODO investigate; assertTrue(assertLiveFunction("Arguments",  "argument", c)); // TODO specify live-as-superclass
		// TODO investigate; assertTrue(assertLiveFunction("MainLogic",  "main", c));
		// TODO investigate; assertTrue(assertLiveFunction("FileSystemObject",  "isDirectory", c)); // TODO live in subclasses: File, Directory
		// TODO investigate; assertTrue(assertLiveFunction("Directory",  "listFiles", c)); //
		// TODO investigate; assertTrue(assertLiveFunction("List",  "forEach", c)); //

		// TODO investigate; assertTrue(assertLiveConstructor("Directory",  c)); // FIXME package name

		// TODO investigate; assertTrue(assertLiveNsMemberVariable("Prelude", "ExitCode", c));
		// TODO investigate; assertTrue(assertLiveNsMemberVariable("Prelude", "ExitSuccess", c));

		var l = new ArrayList<>();
		c.getCompilationEnclosure().getCompilation().world().eachModule(m -> l.add(m.module().getFileName()));
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("184 " + l);

		assertThat(l).containsExactly("1");
//    const fun = function (f) { // <--

//		/sww/modules-sw-writer
		int y = 2;
	}

	String        s;
	ElijahTestCli c;

	@BeforeEach
	public void setUp() {
		s = "test/basic/listfolders3/listfolders3.ez";
		c             = ElijahTestCli.createDefault();
		Emit.emitting = false;

		@NotNull final List<String> x = List_of(s, "-sO");
		c.feedCmdLine(x);
	}

	@Test
	public void testOne() {
		if (c.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}

		//assertEquals(2, c.errorCount()); // TODO Error count obviously should be 0


		final List<Pair<ErrSink.Errors, Object>> list = c.errSinkList();

		int i = 0;

		for (Pair<ErrSink.Errors, Object> pair : list) {
			var l = pair.getLeft();
			var r = pair.getRight();

			System.out.print(Integer.valueOf(i) + " ");
			i++;

			if (l == ErrSink.Errors.DIAGNOSTIC) {
				((Diagnostic) r).report(System.out);
			} else {
				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(r);
			}
		}

		c.cli.getComp()
		     .signals()
		     .subscribeRunStepLoop(new CPX_RunStepLoop() {
			     @Override
			     public void notify_CPX_RunStepLoop(final ErrSink aErrSink, final EOT_OutputTree aOutputTree,
			                                        final CK_ObjectTree aObjectTree) {
				     tb_lf3(aErrSink, aOutputTree, aObjectTree, c);
			     }
		     });
	}
}
