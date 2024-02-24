package tripleo.elijah;

import tripleo.elijah.comp.internal.CompilationImpl;

import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
		assertEquals(curious_that_this_does_not_fail, t.errorCount());

		var c1 = t.c();
		var c2 = (CompilationImpl)c1;
		final boolean[] xx = new boolean[1];
		c2.endSignal.then(x-> xx[0] =true);
		await()
				.atMost(5, TimeUnit.SECONDS)
				.ignoreExceptions();
		assertTrue( xx[0] );
	}

	@Test
	public void testHier() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/hier/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertEquals(curious_that_this_does_not_fail, t.errorCount());

		// TODO 10/15 cucumber??

		/* TODO investigate: */assertTrue(t.assertLiveClass("Bar"));  // .inFile("test/feb2021/hier/hier.elijah")
		/* TODO investigate: */assertTrue(t.assertLiveClass("Foo"));  // ...
		/* TODO investigate: */assertTrue(t.assertLiveClass("Main")); // ...

		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
			//		throw new AcceptedFailure();
		}
	}

	@Test
	public void testProperty() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/property/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertEquals(curious_that_this_does_not_fail, t.errorCount());

		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
			//		throw new AcceptedFailure();
		}
	}

	@Test
	public void testProperty2() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/property2/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertEquals(curious_that_this_does_not_fail, t.errorCount());

		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
			//		throw new AcceptedFailure();
		}
	}

	@Test
	public void testProperty3() throws Exception {
		var t = TestCompilation.simpleTest()
				.setFile("test/feb2021/property3/")
				.run();

		final int curious_that_this_does_not_fail = 1_000_100;
		assertEquals(curious_that_this_does_not_fail, t.errorCount());

		//assert t.c().reports().codeOutputSize() > 0;
		if (t.c().reports().codeOutputSize() < 1) {
			//		throw new AcceptedFailure();
		}
	}
}

//
//
//
