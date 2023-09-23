/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.junit.jupiter.api.Test;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.entrypoints.MainClassEntryPoint;
import tripleo.elijah.lang.i.ClassStatement;
import tripleo.elijah.util.Helpers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Tripleo
 */
public class FindClassesInDemoElNormal {

	@org.junit.jupiter.api.Test
	public final void testParseFile() throws Exception {
		final List<String> args = tripleo.elijah.util.Helpers.List_of("test/demo-el-normal", "test/demo-el-normal/main2", "-sE");
		final ErrSink      eee  = new StdErrSink();
		final Compilation  c    = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		final List<ClassStatement> aClassList = c.world().findClass("Main");
		for (final ClassStatement classStatement : aClassList) {
			tripleo.elijah.util.Stupidity.println_out_2(classStatement.getPackageName().getName());
		}
		assertEquals(1, aClassList.size());  // NOTE this may change. be aware
	}


	@Test
	public final void testListFolders() throws Exception {
		final List<String> args = Helpers.List_of("test/demo-el-normal/listfolders/", "-sE");
		final ErrSink      eee  = new StdErrSink();
		final Compilation  c    = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		// searches all modules for top-level Main's that are classes (only the first from each module though)
		final List<ClassStatement> aClassList = c.world().findClass("Main");
		assertEquals(1, aClassList.size());

		assertFalse(MainClassEntryPoint.isMainClass(aClassList.get(0)), "isMainClass");
	}

}

//
//
//
