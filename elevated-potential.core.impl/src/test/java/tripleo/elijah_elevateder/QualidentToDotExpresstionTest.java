package tripleo.elijah_elevateder;

import org.junit.Test;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah_elevated_durable.lang_impl.QualidentImpl;
import tripleo.elijah_elevateder.util.Helpers0;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class QualidentToDotExpresstionTest {

	@Test
	public void qualidentToDotExpression2() {
		final Qualident q = new QualidentImpl();
		q.append(Helpers0.string_to_ident("a"));
		q.append(Helpers0.string_to_ident("b"));
		q.append(Helpers0.string_to_ident("c"));
		final IExpression e = Helpers0.qualidentToDotExpression2(q);
		SimplePrintLoggerToRemoveSoon.println_out_2(e);
		assertThat(e.asString()).isEqualTo("a.b.c");
	}
}
