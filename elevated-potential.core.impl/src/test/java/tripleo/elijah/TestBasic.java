package tripleo.elijah;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.internal.DefaultCompilerController;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.factory.NonOpinionatedBuilder;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.gen_c.Emit;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah_elevated.comp.input.CompilerInput_;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tripleo.elijah._TestBasicHelper.assertLiveClass;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
public class TestBasic {
	@Disabled
	@Test
	public final void testBasicParse() throws Exception {
		final List<String> ez_files = Files.readLines(new java.io.File("test/basic/ez_files.txt"), Charsets.UTF_8);
		final List<String> args     = new ArrayList<String>();
		args.addAll(ez_files);
		args.add("-sE");
		final ErrSink      eee = new StdErrSink();
		final Compilation0 c   = new CompilationImpl(eee, new IO_());

		c.feedCmdLine(args);

		assertEquals(0, c.errorCount());
	}

	@Test
	public final void testBasic_listfolders3() throws Exception {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation0 c = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());

		if (!DISABLED) {
			Emit.emitting = false;

			final NonOpinionatedBuilder nob = new NonOpinionatedBuilder();

			final ICompilationAccess3 compilationAccess3 = ((CompilationImpl) c).getCompilationAccess3();
			final CompilerController  controller         = nob.createCompilerController(compilationAccess3.getComp());
			final List<CompilerInput> inputs             = nob.inputs(List_of(s, "-sO"));
			c.feedInputs(inputs, controller);

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

			final List<Pair<ErrSink.Errors, Object>> list = c.getErrSink().list();

			int i = 0;

			for (Pair<ErrSink.Errors, Object> pair : list) {
				var l = pair.getLeft();
				var r = pair.getRight();

				System.out.print(Integer.valueOf(i) + " ");
				i++;

				if (l == ErrSink.Errors.DIAGNOSTIC) {
					((Diagnostic) r).report(System.out);
				} else {
					SimplePrintLoggerToRemoveSoon.println_out_4(r);
				}
			}
		}

		// TODO Error count obviously should be 0
		assertThat(c.errorCount(), equalTo(2));

		assertTrue(c.reports().containsInput("test/basic/import_demo.elijjah"));
		assertTrue(c.reports().containsInput("test/basic/listfolders3/listfolders3.elijah"));

		assertEquals(2, c.reports().inputCount()); // TODO is this correct?

		assertTrue(c.reports().containsCodeOutput("/listfolders3/Main.c"));
		assertTrue(c.reports().containsCodeOutput("/listfolders3/Main.h"));

		//[-- Ez CIL change ] CompilerInput{ty=ROOT, inp='test/basic/listfolders3/listfolders3.ez'} ROOT
		//var aaa = "test/basic/import_demo.elijjah";
		//var aab = "test/basic/listfolders3/listfolders3.elijah";

		var baa = "/Prelude/Arguments.h"; assertTrue(c.reports().containsCodeOutput(baa));
		var bae = "/Prelude/Arguments.c"; assertTrue(c.reports().containsCodeOutput(bae));

		assertEquals(6, c.reports().codeOutputSize());

		var bab = "/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.c"; assertTrue(c.reports().containsCodeOutput(bab));
		var bac = "/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.h"; assertTrue(c.reports().containsCodeOutput(bac));

		assertEquals(6, c.reports().outputCount()); // TODO is this correct?

		assertTrue(assertLiveClass("MainLogic", "wpkotlin_c.demo.list_folders", c));
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
		((CompilationImpl)c).world().eachModule(m -> l.add(m.module().getFileName()));
		SimplePrintLoggerToRemoveSoon.println_err_4("184 "+l);

//    const fun = function (f) { // <--

//		/sww/modules-sw-writer
	}

	private final boolean DISABLED = false;


	@Test
	public final void testBasic_listfolders3__() {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation0 c = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());

		if (!DISABLED) {
			final NonOpinionatedBuilder nob = new NonOpinionatedBuilder();

			c.feedInputs(
					nob.inputs(List_of(s, "-sE")),  // -sD??
					new DefaultCompilerController(((CompilationImpl) c).getCompilationAccess3()));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

//			var qq = c.con().createQualident(List_of("std", "io"));
//
//			assertTrue(c.isPackage(qq.toString()));

			var qq2 = ((Compilation)c).con().createQualident(List_of("wpkotlin_c", "demo", "list_folders"));

			assertTrue(c.isPackage(qq2.toString()));

			assertEquals(2, c.errorCount());
		}
	}

	@Test
	public final void testBasic_listfolders4() throws Exception {
		String s = "test/basic/listfolders4/listfolders4.ez";

		final ErrSink      eee = new StdErrSink();
		final Compilation0 c   = new CompilationImpl(eee, new IO_());

		c.feedCmdLine(List_of(s, "-sO"));

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		// TODO Error count obviously should be 0
		assertThat(c.errorCount(), equalTo(4));
	}

	@Test
	public final void testBasic_fact1() throws Exception {
		final String        s  = "test/basic/fact1/main2";
		final Compilation   c  = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());

		final CompilerInput i1 = new CompilerInput_(s);
		final CompilerInput i2 = new CompilerInput_("-sO");
		c.feedInputs(List_of(i1, i2), new DefaultCompilerController(((CompilationImpl) c).getCompilationAccess3()));

		if (c.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}

		final @NotNull EOT_OutputTree cot = c.getOutputTree();
		// pancake has 28
		//assertThat(cot.getList().size(), equalTo(6)); // TODO why not 6?;
		assertThat(cot.getList().size(), equalTo(28));
		assertThat(cot.getList().size(), equalTo(29)); // TODO why not 6?


		if (!DISABLED) {
			assertEquals(25, c.errorCount()); // TODO Error count obviously should be 0
			assertTrue(c.getOutputTree().getList().size() > 0);
			assertTrue(c.getIO().recordedwrites().size() > 0);

			var aofs = c.getCompilationEnclosure().OutputFileAsserts();
			for (Triple<AssOutFile, EOT_FileNameProvider, NG_OutputRequest> aof : aofs) {
				SimplePrintLoggerToRemoveSoon.println_err_4(aof);
			}

			assertTrue(aofs.contains("/Prelude/Prelude.c"));
		}
	}

	@Test
	public final void testBasic_fact1_002() throws Exception {
		testBasic_fact1 f = new testBasic_fact1();
		f.start();

		//assertEquals(25, f.c.errorCount()); // TODO Error count obviously should be 0

		final EOT_OutputTree                 cot = f.c.getOutputTree();
		final Multimap<String, EG_Statement> statementMultimap = ArrayListMultimap.create();

		for (EOT_OutputFile outputFile : cot.getList()) {
			if (outputFile.getType() != EOT_OutputType.SOURCES) continue;

			final String filename = outputFile.getFilename();
			SimplePrintLoggerToRemoveSoon.println_err_4(filename);

			var ss = outputFile.getStatementSequence();

			statementMultimap.put(filename, ss);
		}

		List<Pair<String, String>> sspl = new ArrayList<>();

		for (Map.Entry<String, Collection<EG_Statement>> entry : statementMultimap.asMap().entrySet()) {
			var fn = entry.getKey();
			var ss = Helpers.String_join("\n", (entry.getValue()).stream().map(st -> st.getText()).collect(Collectors.toList()));

			SimplePrintLoggerToRemoveSoon.println_out_4("216 " + fn + " " + ss);

			sspl.add(Pair.of(fn, ss));
		}

		SimplePrintLoggerToRemoveSoon.println_err_4("sspl");
		SimplePrintLoggerToRemoveSoon.println_err_4("-----");
		for (final Pair<String, String> pair : sspl) {
			final String formatted = ("**--** %s %s").formatted(pair.getKey(), pair.getRight());
			SimplePrintLoggerToRemoveSoon.println_err_4(formatted);
		}

		SimplePrintLoggerToRemoveSoon.println_err_4("All done in #testBasic_fact1_002");
	}

	static class testBasic_fact1 {
		Compilation0 c;

		public void start() throws Exception {
			String s = "test/basic/fact1/main2";

			c = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());

			c.feedCmdLine(List_of(s, "-sO"));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}
	}
}
