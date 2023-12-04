/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.comp;

import org.junit.jupiter.api.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.world.i.*;
import tripleo.wrap.File;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static tripleo.elijah.util.Helpers.*;

/**
 * @author Tripleo(envy)
 */
//@Disabled
public class CompilationTest {

	@Disabled @Test
	public final void testEz() throws Exception {
		final List<String> args = List_of("test/comp_test/main3", "-sE"/* , "-out" */);
		final ErrSink      eee = new StdErrSink();
		final Compilation  c   = new CompilationImpl(eee, new IO_());

		c.feedCmdLine(args);

		assertTrue(c.getIO().recordedRead(new File("test/comp_test/main3/main3.ez")));

//		assertTrue(c.getIO().recordedRead(new File("test/comp_test/main3/main3.elijah")));
		assertTrue(c.reports().containsInput("test/comp_test/main3/main3.elijah"));

		assertTrue(c.getIO().recordedRead(new File("test/comp_test/fact1.elijah")));

		assertTrue(c.instructionCount() > 0);

		Collection<WorldModule> worldModules = c.world().modules();

		worldModules.stream().forEach(wm -> {
			var mod = wm.module();
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2(String.format("**48** %s %s", mod, mod.getFileName()));
		});

		assertEquals(3/*7*//* 12 */, worldModules.size());

		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("CompilationTest -- 53 " + worldModules.size());
		assertTrue(worldModules.size() > 2);
	}

}

//
//
//
