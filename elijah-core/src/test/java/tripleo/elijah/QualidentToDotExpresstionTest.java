package tripleo.elijah;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.QualidentImpl;
import tripleo.elijah.util.*;

public class QualidentToDotExpresstionTest {

	@Test
	public void qualidentToDotExpression2() {
		final Qualident q = new QualidentImpl();
		q.append(tripleo.elijah.util.Helpers0.string_to_ident("a"));
		q.append(tripleo.elijah.util.Helpers0.string_to_ident("b"));
		q.append(tripleo.elijah.util.Helpers0.string_to_ident("c"));
		final IExpression e = Helpers0.qualidentToDotExpression2(q);
		tripleo.elijah.util.Stupidity.println_out_2(e);
		assertEquals("a.b.c", e.toString());
	}
}
