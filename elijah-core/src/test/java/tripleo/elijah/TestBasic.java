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
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.StdErrSink;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.comp.internal.DefaultCompilerController;
import tripleo.elijah.diagnostic.Diagnostic;
import tripleo.elijah.factory.comp.CompilationFactory;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputtree.EOT_OutputFile;
import tripleo.elijah.nextgen.outputtree.EOT_OutputType;
import tripleo.elijah.stages.gen_c.Emit;
import tripleo.elijah.stages.write_stage.pipeline_impl.NG_OutputRequest;
import tripleo.elijah.util.Helpers;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
public class TestBasic {

	static class testBasic_fact1 {

		Compilation c;

		public void start() throws Exception {
			String s = "test/basic/fact1/main2";

			c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

			c.feedCmdLine(List_of(s, "-sO"));

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}
	}

	private final boolean TestBasic_DISABLED = false;

	@Disabled
	@Test
	public final void testBasic() throws Exception {
		final List<String> ez_files = Files.readLines(new File("test/basic/ez_files.txt"), Charsets.UTF_8);
		final Map<Integer, Integer> errorCount = new HashMap<>();
		int index = 0;

		for (String s : ez_files) {
//			List<String> args = List_of("test/basic", "-sO"/*, "-out"*/);
			final ErrSink eee = new StdErrSink();
			final Compilation c = new CompilationImpl(eee, new IO());

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

	@Test
	public final void testBasic_fact1() {
		final String s = "test/basic/fact1/main2";
		final Compilation c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());
		final CompilerInput i1 = new CompilerInput(s);
		final CompilerInput i2 = new CompilerInput("-sO");
		c.feedInputs(List_of(i1, i2), new DefaultCompilerController());

		if (c.errorCount() != 0) {
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
		}

		if (!TestBasic_DISABLED) {
			assertEquals(25, c.errorCount()); // TODO Error count obviously should be 0
			assertFalse(c.getOutputTree().getList().isEmpty());
			assertFalse(c.getIO().recordedwrites.isEmpty());

			var aofs = c.getCompilationEnclosure().OutputFileAsserts();
			for (Triple<AssOutFile, EOT_OutputFile.FileNameProvider, NG_OutputRequest> aof : aofs) {
				System.err.println(aof);
			}

//			assertTrue(aofs.contains("/Prelude/Prelude.c"));
//			assertTrue(aofs.contains("/Prelude/String[].h"));

			assertEquals(14, c.reports().codeOutputSize());

			assertTrue(c.reports().containsCodeOutput("/Prelude/String[].h"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/String[].c"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Boolean[].c"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Boolean[].h"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Integer64[].c"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Integer64[].h"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Arguments[].c"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Arguments[].h"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/ConstString.c"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/ConstString.h"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Unsigned64[].c"));
			assertTrue(c.reports().containsCodeOutput("/Prelude/Unsigned64[].h"));

			assertTrue(c.reports().containsCodeOutput("/main2/Main.h"));
			assertTrue(c.reports().containsCodeOutput("/main2/Main.c"));

			/*

-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/String[].h
-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Boolean[].h
-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Boolean[].c
-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/ConstString.c
-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Integer64[].h
-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Integer64[].c
-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/main2/Main.c
--401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Arguments[].c
--401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/ConstString.h
--401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Arguments[].h
--401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/String[].c
-- 401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/main2/Main.h
--401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Unsigned64[].c
--401b Writing path: COMP/9d9243020fb844e1f97f48542eff75703f2cf006a44d3024fc29f2186ff3cf4d/2023-09-26_07.13.11/code2/Prelude/Unsigned64[].h



			 */
		}
	}

	@Test
	public final void testBasic_fact1_002() throws Exception {

		testBasic_fact1 f = new testBasic_fact1();
		f.start();

		// assertEquals(25, f.c.errorCount()); // TODO Error count obviously should be 0

		var cot = f.c.getOutputTree();

		Multimap<String, EG_Statement> mms = ArrayListMultimap.create();

		for (EOT_OutputFile outputFile : cot.getList()) {
			if (outputFile.getType() != EOT_OutputType.SOURCES)
				continue;

			final String filename = outputFile.getFilename();
			System.err.println(filename);

			var ss = outputFile.getStatementSequence();

			mms.put(filename, ss);
			/*
			 * if (ss instanceof EG_SequenceStatement seq) { for (EG_Statement statement :
			 * seq._list()) { var exp = statement.getExplanation();
			 * 
			 * String txt = statement.getText(); } }
			 * 
			 * System.err.println(ss);
			 */
		}

		List<Pair<String, String>> sspl = new ArrayList<>();

		for (Map.Entry<String, Collection<EG_Statement>> entry : mms.asMap().entrySet()) {
			var fn = entry.getKey();
			var ss = Helpers.String_join("\n",
					(entry.getValue()).stream().map(EG_Statement::getText).collect(Collectors.toList()));

			// System.out.println("216 "+fn+" "+ss);

			sspl.add(Pair.of(fn, ss));
		}

		System.err.println(sspl);

		// System.err.println("nothing");
	}

	@Test
	public final void testBasic_listfolders3() {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

		if (!TestBasic_DISABLED) {
			Emit.emitting = false;

			List<CompilerInput> inputs = List_of(s, "-sO")
					.stream()
					.map(CompilerInput::new)
					.collect(Collectors.toList());

			DefaultCompilerController controller = new DefaultCompilerController();

			c.feedInputs(inputs, controller);

			if (c.errorCount() != 0) {
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);
			}

			assertEquals(2, c.errorCount()); // TODO Error count obviously should be 0

			final List<Pair<ErrSink.Errors, Object>> list = c.getErrSink().list();

			int i = 0;

			for (Pair<ErrSink.Errors, Object> pair : list) {
				var l = pair.getLeft();
				var r = pair.getRight();

				System.out.print(i + " ");
				i++;

				if (l == ErrSink.Errors.DIAGNOSTIC) {
					((Diagnostic) r).report(System.out);
				} else {
					System.out.println(r);
				}
			}
		}
	}

	@Test
	public final void testBasic_listfolders3__() {
		String s = "test/basic/listfolders3/listfolders3.ez";

		final Compilation c = CompilationFactory.mkCompilation(new StdErrSink(), new IO());

		if (!TestBasic_DISABLED) {
			c.feedInputs(List_of(s, "-sE").stream() // -sD??
					.map(CompilerInput::new).collect(Collectors.toList()), new DefaultCompilerController());

			if (c.errorCount() != 0)
				System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

			var qq = c.con().createQualident(List_of("std", "io"));

			assertTrue(c.isPackage(qq.toString()));
		}
	}

	@Disabled
	@Test
	public final void testBasic_listfolders4() throws Exception {
		String s = "test/basic/listfolders4/listfolders4.ez";

		final ErrSink eee = new StdErrSink();
		final Compilation c = new CompilationImpl(eee, new IO());

		c.feedCmdLine(List_of(s, "-sO"));

		if (c.errorCount() != 0)
			System.err.printf("Error count should be 0 but is %d for %s%n", c.errorCount(), s);

		assertEquals(4, c.errorCount()); // TODO Error count obviously should be 0
	}

	@Disabled
	@Test
	public final void testBasicParse() throws Exception {
		final List<String> ez_files = Files.readLines(new File("test/basic/ez_files.txt"), Charsets.UTF_8);
		final List<String> args     = new ArrayList<>(ez_files);
		args.add("-sE");
		final ErrSink eee = new StdErrSink();
		final Compilation c = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		assertEquals(0, c.errorCount());
	}
}

//
//
//
