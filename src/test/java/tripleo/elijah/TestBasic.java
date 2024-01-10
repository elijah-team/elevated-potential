/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.i.AssOutFile;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.impl.DefaultCompilerController;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.stages.gen_c.Emit;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;
import tripleo.elijah.util.Helpers;

import org.jetbrains.annotations.NotNull;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
//@Disabled
public class TestBasic {

	private final boolean DISABLED = false;

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

	@Disabled
	@Test
	@SuppressWarnings("JUnit3StyleTestMethodInJUnit4Class")
	public final void testBasic() throws Exception {
		final List<String>          ez_files   = Files.readLines(Path.of("test/basic/ez_files.txt").toFile(), Charsets.UTF_8);
		final Map<Integer, Integer> errorCount = new HashMap<Integer, Integer>();
		int                         index      = 0;

		for (String s : ez_files) {
//			List<String> args = List_of("test/basic", "-sO"/*, "-out"*/);
			final ErrSink      eee = new StdErrSink();
			final Compilation0 c   = new CompilationImpl(eee, new IO_());

			c.feedCmdLine(List_of(s, "-sO"));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
			errorCount.put(index, c.errorCount());
			index++;
		}

		// README this needs changing when running make
		assertEquals(7, (int) errorCount.get(0)); // TODO Error count obviously should be 0
		assertEquals(20, (int) errorCount.get(1)); // TODO Error count obviously should be 0
		assertEquals(9, (int) errorCount.get(2)); // TODO Error count obviously should be 0
	}

	//@Ignore
	@Test
	public final void testBasic_listfolders3() throws Exception {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation0 c = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());

		if (!DISABLED) {
			Emit.emitting = false;

			c.feedInputs(
					List_of(s, "-sO").stream()
							.map(CompilerInput_::new)
							.collect(Collectors.toList()),
					new DefaultCompilerController(((CompilationImpl) c).getCompilationAccess3()));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

			//assertEquals(2, c.errorCount()); // TODO Error count obviously should be 0


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
					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4(r);
				}
			}
		}

		assertEquals(2, c.errorCount());

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
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("184 "+l);

//    const fun = function (f) { // <--

//		/sww/modules-sw-writer
	}

	private boolean assertLiveNsMemberVariable(final String aClassName, final String aNsMemberVariablName, final Compilation0 c) {
		return false;
	}

	private boolean assertLiveConstructor(final String aClassName, final Compilation0 c) {
		return false;
	}

	private boolean assertLiveFunction(final String aClassName, final String aFunctionName, final Compilation0 c) {
		return false;
	}

	public boolean assertLiveClass(final String aClassName, final String aPackageName, final @NotNull Compilation0 c0) {
		CompilationImpl c = (CompilationImpl) c0;
		var ce = c.getCompilationEnclosure();
		var world = c.world();

		var classes = world.findClass(aClassName);

		var xy = ce.getCompilation();

		final Predicate<ClassStatement> predicate = new Predicate<>() {
			@Override
			public boolean test(final ClassStatement classStatement) {
				boolean result;
				if (aPackageName == null) {
					//result = Objects.equals(classStatement.getPackageName(), WorldGlobals.defaultPackage());
					result = classStatement.getPackageName().getName() == null;
				} else {
					result = Helpers.String_equals(classStatement.getPackageName().getName(), aPackageName);
				}
				return result;
			}
		};

		//noinspection UnnecessaryLocalVariable,SimplifyStreamApiCallChains
		boolean result = classes.stream()
				.filter(predicate)
				.findAny()
				.isPresent();

		return result;
	}


	@Disabled @Test
	public final void testBasic_listfolders3__() {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation0 c = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());

		if (!DISABLED) {
			c.feedInputs(
					List_of(s, "-sE").stream() // -sD??
							.map(CompilerInput_::new)
							.collect(Collectors.toList()),
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

	@Disabled
	@Test
	public final void testBasic_listfolders4() throws Exception {
		String s = "test/basic/listfolders4/listfolders4.ez";

		final ErrSink      eee = new StdErrSink();
		final Compilation0 c   = new CompilationImpl(eee, new IO_());

		c.feedCmdLine(List_of(s, "-sO"));

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		assertEquals(4, c.errorCount()); // TODO Error count obviously should be 0
	}

	@Disabled @Test
	public final void testBasic_fact1() throws Exception {
		final String        s  = "test/basic/fact1/main2";
		final Compilation   c  = CompilationFactory.mkCompilation(new StdErrSink(), new IO_());
		final CompilerInput i1 = new CompilerInput_(s);
		final CompilerInput i2 = new CompilerInput_("-sO");
		c.feedInputs(List_of(i1, i2), new DefaultCompilerController(((CompilationImpl) c).getCompilationAccess3()));

		if (c.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}

		if (!DISABLED) {
			assertEquals(25, c.errorCount()); // TODO Error count obviously should be 0
			assertTrue(c.getOutputTree().getList().size() > 0);
			assertTrue(c.getIO().recordedwrites().size() > 0);

			var aofs = c.getCompilationEnclosure().OutputFileAsserts();
			for (Triple<AssOutFile, EOT_FileNameProvider, NG_OutputRequest> aof : aofs) {
				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(aof);
			}

			assertTrue(aofs.contains("/Prelude/Prelude.c"));
		}
	}

	@Disabled @Test
	public final void testBasic_fact1_002() throws Exception {

		testBasic_fact1 f = new testBasic_fact1();
		f.start();

		//assertEquals(25, f.c.errorCount()); // TODO Error count obviously should be 0

		var cot = f.c.getOutputTree();


		Multimap<String, EG_Statement> mms = ArrayListMultimap.create();


		for (EOT_OutputFile outputFile : cot.getList()) {
			if (outputFile.getType() != EOT_OutputType.SOURCES) continue;

			final String filename = outputFile.getFilename();
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(filename);

			var ss = outputFile.getStatementSequence();

			mms.put(filename, ss);
/*
			if (ss instanceof EG_SequenceStatement seq) {
				for (EG_Statement statement : seq._list()) {
					var exp = statement.getExplanation();

					String txt = statement.getText();
				}
			}

			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(ss);
*/
		}

		List<Pair<String, String>> sspl = new ArrayList<>();

		for (Map.Entry<String, Collection<EG_Statement>> entry : mms.asMap().entrySet()) {
			var fn = entry.getKey();
			var ss = Helpers.String_join("\n", (entry.getValue()).stream().map(st -> st.getText()).collect(Collectors.toList()));

			//tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("216 "+fn+" "+ss);

			sspl.add(Pair.of(fn, ss));
		}

		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(sspl);

		//tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("nothing");
	}

	class testBasic_fact1 {

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

//
//
//
