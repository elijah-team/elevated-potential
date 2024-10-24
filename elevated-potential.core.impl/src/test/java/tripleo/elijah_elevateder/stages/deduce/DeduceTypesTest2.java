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
import org.jetbrains.annotations.Nullable;
import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.contexts.IFunctionContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.lang_impl.*;
import tripleo.elijah_elevated_durable.world_impl.DefaultWorldModule;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.lang.types.OS_UserType;
import tripleo.elijah_elevateder.stages.gen_fn.GenType;
import tripleo.elijah_elevateder.test_help.Boilerplate;
import tripleo.elijah_elevateder.util.Helpers0;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.util.Objects;

import static org.junit.Assert.assertTrue;
import static tripleo.elijah_fluffy.util.Helpers.List_of;

@Ignore
public class DeduceTypesTest2 {

	static class ClassBuilder {

		private @Nullable OS_Module _mod = null;
		private @Nullable String _name = null;

		public @NotNull ClassStatement build() {
			assert _mod != null;
			assert _name != null;

			final ClassStatement cs = new ClassStatementImpl(_mod, _mod.getContext());
			final ClassHeader ch = new ClassHeaderImpl(false, List_of());
			ch.setName(Helpers0.string_to_ident(_name));
			cs.setHeader(ch);

			return cs;
		}

		public @NotNull ClassBuilder withModule(final OS_Module aMod) {
			_mod = aMod;
			return this;
		}

		public @NotNull ClassBuilder withName(final String aS) {
			_name = aS;
			return this;
		}
	}

	private boolean genTypeEquals(@NotNull GenType a, @NotNull GenType b) {
		// TODO hack
		return a.getTypeName().isEqual(b.getTypeName()) && a.getResolved().isEqual(b.getResolved());
	}

	@Test
	public void testDeduceIdentExpression() throws ResolveError {
		final Boilerplate b = new Boilerplate();
		b.get();
		final Compilation0 c = b.comp;

		// b.defaultMod();
		final OS_Module mod = ((Compilation)c).moduleBuilder().withPrelude("c").setContext().build();

		mod.setParent(c); // !!??

		final ClassStatement cs = new ClassBuilder().withModule(mod).withName("Test").build();

		final FunctionDef fd = cs.funcDef();
		fd.setName((Helpers0.string_to_ident("test")));
		Scope3 scope3 = new Scope3Impl(fd);
		final VariableSequence vss = scope3.varSeq();
		final VariableStatement vs = vss.next();
		vs.setName((Helpers0.string_to_ident("x")));
		final Qualident qu = new QualidentImpl();
		qu.append(Helpers0.string_to_ident("SystemInteger"));
		((NormalTypeName) vs.typeName()).setName(qu);
		final IFunctionContext fc = (IFunctionContext) fd.getContext();
		vs.typeName().setContext(fc);
		final IdentExpression x1 = Helpers0.string_to_ident("x");
		x1.setContext(fc);
		fd.scope(scope3);
		fd.postConstruct();
		cs.postConstruct();
		mod.postConstruct();

		//
		//
		//

		/*
		 * // Called Boilerplate##get final ICompilationAccess aca = new
		 * DefaultCompilationAccess(c); assert c.__cr == null; c.__cr = new
		 * CompilationRunner(aca);
		 */

		final CompilationEnclosure ce = ((Compilation) c).getCompilationEnclosure();
		assert ce.getCompilationRunner().getCrState() != null; // always true

		final DeducePhase dp = b.getDeducePhase();

		final DefaultWorldModule wm = new DefaultWorldModule(mod, ce);

		final DeduceTypes2 d = dp.deduceModule(wm);

		final GenType x = DeduceLookupUtils.deduceExpression(d, x1, fc);
		SimplePrintLoggerToRemoveSoon.println_out_2("-- deduceExpression >>" + x);
//		assertEquals(new OS_BuiltInType(BuiltInTypes..SystemInteger).getBType(), x.getBType());
//		final RegularTypeName tn = new RegularTypeNameImpl();
		final VariableTypeName tn = new VariableTypeNameImpl();
		final Qualident tnq = new QualidentImpl();
		tnq.append(Helpers0.string_to_ident("SystemInteger"));
		tn.setName(tnq);
		tn.setContext(fd.getContext());

//		assertEquals(new OS_UserType(tn).getTypeName(), x.getTypeName());
		assertTrue(genTypeEquals(d.resolve_type(new OS_UserType(tn), tn.getContext()), Objects.requireNonNull(x)));
//		assertEquals(new OS_UserType(tn).toString(), x.toString());
	}
}
