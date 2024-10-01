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
import tripleo.elijah_elevated_durable.comp.EDL_ErrSink;
import tripleo.elijah_elevated_durable.comp.EDL_IO;
import tripleo.elijah_elevated_durable.comp.EDL_Compilation;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.world.i.WorldModule;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;
import tripleo.wrap.File;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tripleo.elijah_fluffy.util.Helpers.*;

/**
 * @author Tripleo(envy)
 */
//@Ignore
public class CompilationTest {

	@Ignore
	@Test
	public final void testEz() throws Exception {
		final List<String> args = List_of("test/comp_test/main3", "-sE"/* , "-out" */);
		final ErrSink     eee = new EDL_ErrSink();
		final Compilation c   = new EDL_Compilation(eee, new EDL_IO());

		c.feedCmdLine(args);

		assertTrue(c.getIO().recordedRead(new File("test/comp_test/main3/main3.ez")));

//		assertTrue(c.getIO().recordedRead(new File("test/comp_test/main3/main3.elijah")));
		assertTrue(c.reports().containsInput("test/comp_test/main3/main3.elijah"));

		assertTrue(c.getIO().recordedRead(new File("test/comp_test/fact1.elijah")));

//		assertTrue(c.instructionCount() > 0);

		Collection<WorldModule> worldModules = c.world().modules();

		worldModules.stream().forEach(wm -> {
			var mod = wm.module();
			SimplePrintLoggerToRemoveSoon.println_out_2(String.format("**48** %s %s", mod, mod.getFileName()));
		});

		assertEquals(3/*7*//* 12 */, worldModules.size());

		SimplePrintLoggerToRemoveSoon.println_err_4("CompilationTest -- 53 " + worldModules.size());
		assertTrue(worldModules.size() > 2);
	}

}

//
//
//
