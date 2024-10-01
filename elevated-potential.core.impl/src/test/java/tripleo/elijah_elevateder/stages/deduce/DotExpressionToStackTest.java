/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */

package tripleo.elijah_elevateder.stages.deduce;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_impl.DotExpressionImpl;
import tripleo.elijah_elevateder.util.Helpers0;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class DotExpressionToStackTest {

	@Test
	public void test_dot_expression_to_stack() {
//		DeduceTypes2 d = new DeduceTypes2(null);
		//
		IdentExpression c = Helpers0.string_to_ident("c");
		IdentExpression b = Helpers0.string_to_ident("b");
		IdentExpression a = Helpers0.string_to_ident("a");
		//
		DotExpression de2 = new DotExpressionImpl(b, c);
		DotExpression de = new DotExpressionImpl(a, de2);
		//
		@NotNull
		Stack<IExpression> s = DeduceLookupUtils.dot_expression_to_stack(de);
//		IExpression[] sa = (IExpression[]) s.toArray();
		assertEquals(a, s.pop());
		assertEquals(b, s.pop());
		assertEquals(c, s.pop());
	}

	@Test
	public void test_dot_expression_to_stack2() {
//		DeduceTypes2 dt2 = new DeduceTypes2(null);
		//
		IdentExpression e = Helpers0.string_to_ident("e");
		IdentExpression d = Helpers0.string_to_ident("d");
		IdentExpression c = Helpers0.string_to_ident("c");
		IdentExpression b = Helpers0.string_to_ident("b");
		IdentExpression a = Helpers0.string_to_ident("a");
		//
		DotExpression de4 = new DotExpressionImpl(d, e);
		DotExpression de3 = new DotExpressionImpl(c, de4);
		DotExpression de2 = new DotExpressionImpl(b, de3);
		DotExpression de = new DotExpressionImpl(a, de2);
		//
		@NotNull
		Stack<IExpression> s = DeduceLookupUtils.dot_expression_to_stack(de);
//		IExpression[] sa = (IExpression[]) s.toArray();
		assertEquals(a, s.pop());
		assertEquals(b, s.pop());
		assertEquals(c, s.pop());
		assertEquals(d, s.pop());
		assertEquals(e, s.pop());
	}
}
