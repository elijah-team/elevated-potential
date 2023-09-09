/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.comp.internal.CompilationImpl;
import tripleo.elijah.util.Stupidity;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * @author Tripleo(envy)
 */
public class CompilationTest {

	@Test
	public final void testEz() throws Exception {
		final List<String> args = List_of("test/comp_test/main3", "-sE"/*, "-out"*/);
		final ErrSink      eee  = new StdErrSink();
		final Compilation  c    = new CompilationImpl(eee, new IO());

		c.feedCmdLine(args);

		Assert.assertTrue(c.getIO().recordedRead(new File("test/comp_test/main3/main3.ez")));
		Assert.assertTrue(c.getIO().recordedRead(new File("test/comp_test/main3/main3.elijah")));
		Assert.assertTrue(c.getIO().recordedRead(new File("test/comp_test/fact1.elijah")));

		Assert.assertTrue(c.instructionCount() > 0);

		c.livingRepo().modules()
				.stream()
				.forEach(wm -> {
					var mod = wm.module();
					Stupidity.println_out_2(String.format("**48** %s %s", mod, mod.getFileName()));
				});

		assertThat(c.livingRepo().modules().size(), new IsEqual<Integer>(0));

		System.err.println("CompilationTest -- 53 " + c.livingRepo().modules().size());
		//Assert.assertTrue(c.livingRepo().modules().size() > 2);
	}

}

//
//
//
