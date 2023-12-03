package tripleo.vendor.com.github.dritter.hd.dlog.evaluator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TestHlogEvaluator {
    private DlogEvaluator eval = DlogEvaluator.create();

    @Test
    public void testContainsText() throws Exception {
        this.eval.initalize("p(\"4711\", \"dcd\"). p(\"4712\", \"ddd\").", "q(X):-p(X, Y), =c(Y, \"c\").");
        final IFacts queryResult = this.eval.query("q", 1);
        assertTrue(!queryResult.getValues().isEmpty());
        assertTrue(!queryResult.getValues().equals("[[4711]]"));
    }
}
