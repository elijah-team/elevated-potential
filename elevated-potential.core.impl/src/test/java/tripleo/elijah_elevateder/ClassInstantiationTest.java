/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder;

import org.junit.Test;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import static org.assertj.core.api.Assertions.assertThat;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;

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

		assertThat(t.c().reports().codeOutputSize()).isEqualTo(0);
		           //, greaterThan(0));
				   // FIXME paf

		final int curious_that_this_does_not_fail = 0/*100*/;
		assertThat(t.errorCount()).isEqualTo(curious_that_this_does_not_fail);
	}

	@Test
	public void classInstantiation2() throws Exception {
		final String f = "test/basic1/class_instantiation2/";
		final SimpleTest t = TestCompilation.simpleTest()
				.setFile(f)
				.run();

		SimplePrintLoggerToRemoveSoon.println_err_4("Errorcount is " + t.errorCount());

		final int curious_that_this_does_not_fail = 1_000_000;
		//assertThat(t.errorCount()).isEqualTo(curious_that_this_does_not_fail));
		assertThat(t.errorCount()).isEqualTo(0);

		assertThat(t.errorCount()).isEqualTo(0);
		           //equalTo(2);

		assertThat(t.c().reports().codeOutputSize()).isEqualTo(0);
		           //, greaterThan(0));
				   // FIXME paf
	}

	@Test
	public void classInstantiation3() throws Exception {
		final String f = "test/basic1/class_instantiation3/";
		final SimpleTest t = TestCompilation.simpleTest()
				.setFile(f)
				.run();

		SimplePrintLoggerToRemoveSoon.println_err_4("Errorcount is " + t.errorCount());

		final int curious_that_this_does_not_fail = 0/*100*/;
		assertThat(t.errorCount()).isEqualTo(curious_that_this_does_not_fail);

		assertThat(t.errorCount()
				//equalTo(2);
				).isEqualTo(0);

		assertThat(t.c().reports().codeOutputSize()
		           //, greaterThan(0));
				).isEqualTo(0); // FIXME paf
	}

	@Test
	public void classInstantiation4() throws Exception {
		final String f = "test/basic1/class_instantiation4/";
		final SimpleTest t = TestCompilation.simpleTest()
				.setFile(f)
				.run();

		SimplePrintLoggerToRemoveSoon.println_err_4("Errorcount is " + t.errorCount());

		final int curious_that_this_does_not_fail = 0/*100*/;
		assertThat(t.errorCount()).isEqualTo(curious_that_this_does_not_fail);

		assertThat(t.errorCount()
//				equalTo(2);
				).isEqualTo(0);

		assertThat(t.c().reports().codeOutputSize()
		           //, greaterThan(0));
				).isEqualTo(0); // FIXME paf
	}
}

//
//
//
