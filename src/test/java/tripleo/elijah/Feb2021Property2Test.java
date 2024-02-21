package tripleo.elijah;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created 9/9/21 4:16 AM
 */
public class Feb2021Property2Test {
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
}
