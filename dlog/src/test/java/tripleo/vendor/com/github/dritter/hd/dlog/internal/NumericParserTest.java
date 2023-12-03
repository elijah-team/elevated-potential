package tripleo.vendor.com.github.dritter.hd.dlog.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import tripleo.vendor.com.github.dritter.hd.dlog.IRule;
import tripleo.vendor.com.github.dritter.hd.dlog.parser.DlogParser;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumericParserTest {
    /**
     * DlogParser.
     */
    private DlogParser hp = null;

    /**
     * Set up DlogParser for test.
     */
    @BeforeEach
    public void setUp() {
        hp = new DlogParser();
    }

    /**
     * Tear down test.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test simple numeric rule with multiple body literals.
     */
    @Test
    public void testNumericGreaterRule() {
        final String input = "q(A, B) :- q(B, A), >(B, 3).";
        final String expected = "q(A, B) :- q(B, A), B > \"3\".";
        assertEquals(expected, parseSingleRule(input));
    }

    /**
     * Test simple numeric rule with multiple body literals.
     */
    @Test
    public void testNumericEqualsRule() {
        final String input = "q(A, B) :- q(B, A), =(B, 3).";
        final String expected = "q(A, B) :- q(B, A), B = \"3\".";
        assertEquals(expected, parseSingleRule(input));
    }

    /**
     * Test simple numeric rule with multiple body literals.
     */
    @Test
    public void testNumericRule() {
        String expected = "q(A, B) :- q(B, A), r(B, \"3\").";
        assertEquals(expected, parseSingleRule(expected));
    }

    /**
     * @param expected
     * @return String
     */
    private String parseSingleRule(final String expected) {
        hp.parse(expected);
        final List<IRule> rules = this.hp.getRules();
        return rules.get(0).toString();
    }
}
