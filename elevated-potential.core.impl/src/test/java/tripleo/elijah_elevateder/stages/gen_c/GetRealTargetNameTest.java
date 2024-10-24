/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.gen_c;

import org.junit.Before;
import org.junit.Test;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.lang_impl.*;
import tripleo.elijah_elevateder.contexts.ModuleContextImpl;
import tripleo.elijah_elevateder.stages.deduce.DeducePhase;
import tripleo.elijah_elevateder.stages.deduce.DeduceTypes2;
import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.elijah_elevateder.stages.instructions.*;
import tripleo.elijah_elevateder.test_help.Boilerplate;
import tripleo.elijah_elevateder.test_help.XX;
import tripleo.elijah_elevateder.util.Helpers0;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetRealTargetNameTest {
	EvaFunction gf;
	private Boilerplate boilerplate; // NOTE hmm. (reduce) boilerplate reductionism

	@Before //Each
	public void setUp() {
		final OS_Module       mod2 = new OS_ModuleImpl();
		final ModuleContext   ctx = new ModuleContextImpl(mod2);
		final ClassStatement  cs = new ClassStatementImpl(mod2, ctx);
		final ClassHeaderImpl ch = new ClassHeaderImpl(false, Collections.emptyList());
		ch.setName(Helpers0.string_to_ident("__FakeClassName__"));
		cs.setHeader(ch);
		final FunctionDef fd = new FunctionDefImpl(cs, ctx);
		gf = new EvaFunction(fd);

		boilerplate = new Boilerplate();
		boilerplate.get();
	}

	//	@Ignore
	@Test // too complicated
	public void testManualXDotFoo() {
		final IdentExpression x_ident   = XX.ident("x");
		final IdentExpression foo_ident = XX.ident("foo", gf.getFD().getContext());

		//
		// create x.foo, where x is a VAR and foo is unknown
		// neither has type information
		// GenerateC#getRealTargetName doesn't use type information
		// TODO but what if foo was a property instead of a member
		//
		final TypeTableEntry    tte   = XX.regularTypeName_specifyTableEntry(x_ident, gf, "X_Type");
		final VariableStatement x_var = XX.sequenceAndVarNamed(x_ident);
		final int               int_index = gf.addVariableTableEntry("x", VariableTableType.VAR, tte, x_var);
		final int               ite_index = gf.addIdentTableEntry(foo_ident, gf.getFD().getContext());
		final IntegerIA         integerIA = new IntegerIA(int_index, gf);
		final IdentIA           ident_ia  = new IdentIA(ite_index, gf);
		ident_ia.setPrev(integerIA);

		Emit.emitting = false;

		//

		final OS_Module mod = boilerplate.defaultMod();
		mod.setParent(boilerplate.comp);

		final DeducePhase  phase        = boilerplate.getDeducePhase();
		final DeduceTypes2 deduceTypes2 = new DeduceTypes2(mod, phase);
		final Context      ctx          = mock(Context.class);

		(gf.getIdentTableEntry(0)).setDeduceTypes2(deduceTypes2, ctx, gf);

		final LookupResultList lrl = new LookupResultListImpl();
		lrl.add(x_ident.getText(), 1, x_var, null);

//		when(ctx.lookup(foo_ident.getText())).thenReturn(lrl);
		when(ctx.lookup(x_ident.getText())).thenReturn(lrl);
		when(ctx.lookup(x_ident.getText())).thenReturn(lrl);

		final GenType genType = new GenTypeImpl();
		genType.setTypeName(tte.getAttached());
		integerIA.getEntry().resolveType(genType);

		//
		//
		//

		final GenerateC generateC = (GenerateC) boilerplate.getGenerateFiles2(mod);
		final ZoneITE   zi        = generateC._zone.get(ident_ia);
		final String    x         = zi.getRealTargetName2(Generate_Code_For_Method.AOG.GET, null);
		assertEquals("vvx->vmfoo", x);
	}
}

//
//
//
