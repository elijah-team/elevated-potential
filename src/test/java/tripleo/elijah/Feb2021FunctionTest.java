package tripleo.elijah;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created 9/9/21 4:16 AM
 */
public class Feb2021FunctionTest {
    @Test
    public void testFunction() throws Exception {
        var t = TestCompilation.simpleTest()
                .setFile("test/feb2021/function/")
                .run();

        final int curious_that_this_does_not_fail = 1_000_100;
        assertEquals(curious_that_this_does_not_fail, t.errorCount());
    }

}
