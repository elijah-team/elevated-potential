/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah;

import org.junit.jupiter.api.Test;
import tripleo.elijah.test_help.*;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created 3/5/21 4:32 AM
 */
public class ClassInstantiationTest {

	@Test
	public void classInstantiation() throws Exception {
		String f = "test/basic1/class_instantiation/";

		var t = TestCompilation.simpleTest()
				.setFile(f)
				.run();

		SimplePrintLoggerToRemoveSoon.println_err_4("Errorcount is " + t.errorCount());


		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
	//		throw new AcceptedFailure();
		}

		final int curious_that_this_does_not_fail = 0/*100*/;
		assertEquals(curious_that_this_does_not_fail, t.errorCount());
	}

	@Test
	public void classInstantiation2() throws Exception {
		final String f = "test/basic1/class_instantiation2/";
		final SimpleTest t = TestCompilation.simpleTest()
				.setFile(f)
				.run();

		SimplePrintLoggerToRemoveSoon.println_err_4("Errorcount is " + t.errorCount());

		final int curious_that_this_does_not_fail = 0/*100*/;
		assertThat(t.errorCount())
				.isEqualTo(curious_that_this_does_not_fail);

		assertThat(t.errorCount())
				.isEqualTo(2);

		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
			//		throw new AcceptedFailure();
		}
	}

	@Test
	public void classInstantiation3() throws Exception {
		final String f = "test/basic1/class_instantiation3/";
		final SimpleTest t = TestCompilation.simpleTest()
				.setFile(f)
				.run();

		SimplePrintLoggerToRemoveSoon.println_err_4("Errorcount is " + t.errorCount());

		final int curious_that_this_does_not_fail = 0/*100*/;
		assertThat(t.errorCount())
				.isEqualTo(curious_that_this_does_not_fail);

		assertThat(t.errorCount())
				.isEqualTo(2);

		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
			//		throw new AcceptedFailure();
		}
	}

	@Test
	public void classInstantiation4() throws Exception {
		final String f = "test/basic1/class_instantiation4/";
		final SimpleTest t = TestCompilation.simpleTest()
				.setFile(f)
				.run();

		SimplePrintLoggerToRemoveSoon.println_err_4("Errorcount is " + t.errorCount());

		final int curious_that_this_does_not_fail = 0/*100*/;
		assertThat(t.errorCount())
				.isEqualTo(curious_that_this_does_not_fail);

		assertThat(t.errorCount())
				.isEqualTo(2);

		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
			//		throw new AcceptedFailure();
		}
	}
}

//
//
//
