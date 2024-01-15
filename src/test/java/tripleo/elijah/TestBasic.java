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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.ElijahTestCli;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.AssOutFile;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.impl.DefaultCompilationEnclosure;
import tripleo.elijah.comp.process.CPX_RunStepLoop;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;
import tripleo.elijah.util.Helpers;

import java.nio.file.Path;
import java.util.*;
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

	boolean assertLiveNsMemberVariable(final String aClassName, final String aNsMemberVariablName, final Compilation0 c) {
		return false;
	}

	boolean assertLiveConstructor(final String aClassName, final Compilation0 c) {
		return false;
	}

	boolean assertLiveFunction(final String aClassName, final String aFunctionName, final Compilation0 c) {
		return false;
	}

//	@Disabled
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
