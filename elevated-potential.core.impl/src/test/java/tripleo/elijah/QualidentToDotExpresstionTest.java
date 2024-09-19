package tripleo.elijah;

import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.QualidentImpl;
import tripleo.elijah.util.*;

import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QualidentToDotExpresstionTest {

	@Test
	public void qualidentToDotExpression2() {
		final Qualident q = new QualidentImpl();
		q.append(tripleo.elijah.util.Helpers0.string_to_ident("a"));
		q.append(tripleo.elijah.util.Helpers0.string_to_ident("b"));
		q.append(tripleo.elijah.util.Helpers0.string_to_ident("c"));
		final IExpression e = Helpers0.qualidentToDotExpression2(q);
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2(e);
		assertThat(e.asString()).isEqualTo("a.b.c");
	}
}
