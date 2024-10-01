/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.comp;

import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevateder.entrypoints.MainClassEntryPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo
 */
@Ignore
public class FindClassesInDemoElNormalTest {

	@Ignore @Test
	public final void testListFolders() throws Exception {
		final List<String> args = List_of("test/demo-el-normal/listfolders/", "-sE");
		final ErrSink      eee  = new EDL_ErrSink();
		final Compilation  c    = new EDL_Compilation(eee, new EDL_IO());

		c.feedCmdLine(args);

		// searches all modules for top-level Main's that are classes
		final List<ClassStatement> aClassList = c.world().findClass("Main");
		assertEquals(1, aClassList.size());

		assertThat(MainClassEntryPoint.isMainClass(aClassList.get(0))).isFalse(); //, "isMainClass");
	}

	@Ignore
	@Test
	public final void testParseFile() throws Exception {
		final List<String> args = List_of("test/demo-el-normal",
				"test/demo-el-normal/main2", "-sE");
		final ErrSink      eee = new EDL_ErrSink();
		final Compilation  c   = new EDL_Compilation(eee, new EDL_IO());

		c.feedCmdLine(args);

		{
			List<String> l = new ArrayList<>();
			c.world().eachModule(wm -> l.add(wm.module().getFileName()));

			assertThat(l.size()).isEqualTo(2); // TODO is this correct?
			// assertThat(l).containsExactlyInAnyOrder("test/demo-el-normal/main2.elijah", "test/demo-el-normal/fact2.elijah");
			assertThat(l).containsExactlyInAnyOrder(
					"Prelude.elijjah", // FIXME this looks bad. (Prelude should have a path)
					"test/demo-el-normal/fact2.elijah"
			);
		}

		final List<ClassStatement> aClassList = c.world().findClass("Main");
		for (final ClassStatement classStatement : aClassList) {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2(classStatement.getPackageName().getName());
		}

		final List<String> classNames = aClassList.stream()
				.map(ClassStatement::getName)
				.collect(Collectors.toList());

		assertThat(classNames).containsExactlyInAnyOrder("Main");

		// assertEquals(1, aClassList.size()); // NOTE this may change. be aware

		// FIXME obviously not (paf)
		assertThat(c.errorCount()).isEqualTo(0);
	}
}

//
//
//
