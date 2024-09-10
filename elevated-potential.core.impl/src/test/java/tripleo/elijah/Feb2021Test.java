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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created 9/9/21 4:16 AM
 */
public class Feb2021Test {

	@Test
	public void testFunction() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/function/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertThat(t.errorCount(),
				//equalTo(curious_that_this_does_not_fail);
				equalTo(0));
	}

	@Test
	public void testHier() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/hier/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertThat(t.errorCount(),
				//equalTo(curious_that_this_does_not_fail);
				equalTo(0));

		// TODO 10/15 cucumber??

		/* TODO investigate: */assertTrue(t.assertLiveClass("Bar"));  // .inFile("test/feb2021/hier/hier.elijah")
		/* TODO investigate: */assertTrue(t.assertLiveClass("Foo"));  // ...
		/* TODO investigate: */assertTrue(t.assertLiveClass("Main")); // ...

		assertThat(t.c().reports().codeOutputSize(),
				greaterThan(0));
				//,equalTo(0));
	}

	@Test
	public void testProperty() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/property/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertThat(t.errorCount(),
				//equalTo(curious_that_this_does_not_fail);
				equalTo(0));

		assertThat(t.c().reports().codeOutputSize()
				,greaterThan(0));
				//,equalTo(0));
	}

	@Test
	public void testProperty2() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/property2/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertThat(t.errorCount()
				//equalTo(curious_that_this_does_not_fail);
				,equalTo(0));

		assertThat(t.c().reports().codeOutputSize()
				,greaterThan(0));
				//,equalTo(0));
	}

	@Test
	public void testProperty3() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/property3/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertThat(t.errorCount()
				//equalTo(curious_that_this_does_not_fail);
				,equalTo(0));

		assertThat(t.c().reports().codeOutputSize()
				,greaterThan(0));
				//,equalTo(0));
	}
}

//
//
//
