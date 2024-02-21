package tripleo.elijah;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created 9/9/21 4:16 AM
 */
public class Feb2021HierTest {
    @Test
    public void testHier() throws Exception {
        var t = TestCompilation.simpleTest()
                .setFile("test/feb2021/hier/")
                .run();

        final int curious_that_this_does_not_fail = 1_000_100;
        assertEquals(curious_that_this_does_not_fail, t.errorCount());

        // TODO 10/15 cucumber??

        /* TODO investigate: */
        assertTrue(t.assertLiveClass("Bar"));  // .inFile("test/feb2021/hier/hier.elijah")
        /* TODO investigate: */
        assertTrue(t.assertLiveClass("Foo"));  // ...
        /* TODO investigate: */
        assertTrue(t.assertLiveClass("Main")); // ...

        //assert t.c().reports().codeOutputSize() > 0;
        if (t.c().reports().codeOutputSize() < 1) {
            //		throw new AcceptedFailure();
        }
    }

}
