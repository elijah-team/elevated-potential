/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import io.reactivex.rxjava3.annotations.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.ElijahTestCli;
import tripleo.elijah.comp.Finally;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.AssOutFile;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.impl.DefaultCompilationEnclosure;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.comp.inputs.CompilerInput_;
import tripleo.elijah.comp.process.CPX_RunStepLoop;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_c.Emit;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.world.i.LivingRepo;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
//@Disabled
public class TestBasic {

	private final boolean DISABLED = false;

	@NotNull
	private static List<CompilerInput> _convertStringListToInputs(final List<String> ls, final Compilation c) {
		final List<CompilerInput> inputs = new ArrayList<>();
		for (String s : ls) {
			final CompilerInput i1 = new CompilerInput_(s, Optional.of(c));
			inputs.add(i1);
		}
		return inputs;
	}

	@Disabled
	@Test
	public final void testBasicParse() throws Exception {
		final List<String> ez_files = Files.readLines(new java.io.File("test/basic/ez_files.txt"), Charsets.UTF_8);

		final ElijahCli c = ElijahCli.createDefaultWithArgs(ez_files, "-sE"); // CompilerInput(Change.of(STAGE_E))

		int errorCount = c.obtainErrorCount();

		assertEquals(0, errorCount);
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
			final ElijahCli c = ElijahCli.createDefault();

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

		final var c = ElijahTestCli.createDefault();

		if (!DISABLED) {
			Emit.emitting = false;

			@NotNull final List<String> x = List_of(s, "-sO");
			c.feedCmdLine(x);
			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

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
		}

		c.cli.getComp()
		     .signals()
		     .subscribeRunStepLoop(new CPX_RunStepLoop() {
			     @Override
			     public void notify_CPX_RunStepLoop(final ErrSink aErrSink, final EOT_OutputTree aOutputTree, final CK_ObjectTree aObjectTree) {
				     tb_lf3(aErrSink, aOutputTree, aObjectTree, c);
			     }
		     });
	}

	private void tb_lf3(final ErrSink aErrSink, final EOT_OutputTree aOutputTree, final CK_ObjectTree aObjectTree, ElijahTestCli c) {
		final Finally REPORTS = c.reports();

		assertThat(c.errorCount()).isEqualTo(2);

		assertThat(REPORTS.inputCount()).isEqualTo(2);  // TODO is this correct?

		//[-- Ez CIL change ] CompilerInput{ty=ROOT, inp='test/basic/listfolders3/listfolders3.ez'} ROOT
		final String aaa = "test/basic/import_demo.elijjah";
		final String aab = "test/basic/listfolders3/listfolders3.elijah";
		assertThat(REPORTS.inputFilenames()).containsExactly(aaa, aab);

		final String baa = "/Prelude/Arguments.h";
		final String bae = "/Prelude/Arguments.c";
		final String bab = "/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.c";
		final String bac = "/listfolders3/wpkotlin_c.demo.list_folders/MainLogic.h";
		final String baf = "/listfolders3/Main.c";
		final String bah = "/listfolders3/Main.h";
		assertThat(REPORTS.outputFilenames()).containsExactly(baf, bah, baa, bae, bab, bac);
		assertThat(REPORTS.outputCount()).isEqualTo(6);

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
		c.getCompilationEnclosure().getCompilation().world().eachModule(m -> l.add(m.module().getFileName()));
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("184 " + l);

		assertThat(l).containsExactly("1");
//    const fun = function (f) { // <--

//		/sww/modules-sw-writer
		int y = 2;
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

	public boolean assertLiveClass(final String aClassName, final String aPackageName, final ElijahTestCli c0) {
		final Compilation          c       = c0.cli.getComp();
		final LivingRepo           world   = c.world();
		final List<ClassStatement> classes = world.findClass(aClassName);

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

	@Disabled
	@Test
	public final void testBasic_listfolders3__() {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final ElijahTestCli cli = ElijahTestCli.createDefault();

		if (!DISABLED) {
			final List<String> args = List_of(s, "-sE");
			cli.feedCmdLine(args);

			if (cli.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", cli.errorCount(), s);

//			var qq = c.con().createQualident(List_of("std", "io"));
//
//			assertTrue(c.isPackage(qq.toString()));

			final Qualident qq2 = cli.cli.getComp().con().createQualident(List_of("wpkotlin_c", "demo", "list_folders"));

			assertTrue(cli.isPackage(qq2.toString()));

			assertEquals(2, cli.errorCount());
		}
	}

	@Disabled
	@Test
	public final void testBasic_listfolders4() {
		final String s = "test/basic/listfolders4/listfolders4.ez";
		final ElijahCli cli = ElijahCli.createDefault();
		cli.feedCmdLine(List_of(s, "-sO"));

		if (cli.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", cli.errorCount(), s);
		}

		assertThat(cli.errorCount())
				.isEqualTo(4);  // TODO Error count obviously should be 0
	}

	@Disabled
	@Test
	public final void testBasic_fact1() {
		final String        s   = "test/basic/fact1/main2";
		final ElijahTestCli cli = new ElijahTestCli();
		cli.feedCmdLine(List_of(s));

		if (cli.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", cli.errorCount(), s);
		}

		if (!DISABLED) {
			assertThat(cli.errorCount())
					.isEqualTo(25); // TODO Error count obviously should be 0

//			assertThat(cli.cli.getComp().getOutputTree().getList())
//					.hasSize(0);
			assertFalse(cli.outputTree_isEmpty());

			assertThat(cli.cli.getComp().getIO().recordedwrites())
					.hasSize(0);

			final DefaultCompilationEnclosure.@NonNull OFA aofs = cli.getCompilationEnclosure().OutputFileAsserts();
			for (Triple<AssOutFile, EOT_FileNameProvider, NG_OutputRequest> aof : aofs) {
				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(aof);
			}

			assertThat(aofs)
					.contains("/Prelude/Prelude.c");
		}
	}

//	@Disabled
	@Test
	public final void testBasic_fact1_002() {
		final String s = "test/basic/fact1/main2";
		final ElijahCli c = ElijahCli.createDefault();
		c.feedCmdLine(List_of(s, "-sO"));

		c.getComp().signals()
				 .subscribeRunStepLoop(new CPX_RunStepLoop() {
					 @Override
					 public void notify_CPX_RunStepLoop(final ErrSink aErrSink, final EOT_OutputTree aOutputTree, final CK_ObjectTree aObjectTree) {
						 for (Pair<ErrSink.Errors, Object> errorsObjectPair : aErrSink.list()) {
							 final ErrSink.Errors left = errorsObjectPair.getLeft();
							 System.err.println("998-336 " + left);
							 System.err.println("998-336 " + left);
						 }

						 assertThat(aErrSink.errorCount())
								 .isEqualTo(25); // TODO Error count obviously should be 0
					 }
				 });

		assertThat(c.errorCount())
				.isEqualTo(25); // TODO Error count obviously should be 0

		final EOT_OutputTree                 cot = c.getComp().getOutputTree();
		final Multimap<String, EG_Statement> mms = ArrayListMultimap.create();

		cot.getList().stream()
				.filter(outputFile -> outputFile.getType() != EOT_OutputType.SOURCES)
				.forEach(outputFile -> {
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
*/

					tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(ss);
				});

		final List<Pair<String, String>> sspl = new ArrayList<>();

		for (Map.Entry<String, Collection<EG_Statement>> entry : mms.asMap().entrySet()) {
			var fn = entry.getKey();
			var ss = Helpers.String_join("\n", (entry.getValue()).stream().map(st -> st.getText()).collect(Collectors.toList()));

			//tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("216 "+fn+" "+ss);

			sspl.add(Pair.of(fn, ss));
		}

		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(sspl);

		//tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("nothing");
	}
}

//
//
//
