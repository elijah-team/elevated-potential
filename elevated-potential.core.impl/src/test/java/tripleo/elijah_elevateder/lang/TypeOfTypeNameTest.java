/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.lang;

import org.junit.Ignore;
import org.junit.Test;
import tripleo.elijah.comp.Compilation0;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.g.GResolveError;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated_durable.comp.EDL_ErrSink;
import tripleo.elijah_elevated_durable.lang_impl.*;
import tripleo.elijah_elevated_durable.pipelines.PipelineLogic;
import tripleo.elijah_elevateder.stages.deduce.DeduceTypes2;
import tripleo.elijah_elevateder.test_help.Boilerplate;
import tripleo.elijah_elevateder.util.Helpers0;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tripleo.elijah_fluffy.util.Helpers.List_of;

public class TypeOfTypeNameTest {

	@Ignore
	@Test
	public void typeOfComplexQualident() throws GResolveError {
		//
		// CREATE MOCKS
		//
		Context ctx = mock(Context.class);
		OS_Module    mod = mock(OS_Module.class);
		Compilation0 c   = mock(Compilation0.class);

		//
		// CREATE VARIABLES
		//
		ErrSink e = new EDL_ErrSink();

		String typeNameString = "package.AbstractFactory";

		VariableStatementImpl var_x = new VariableStatementImpl(null);
		var_x.setName(Helpers0.string_to_ident("x")); // not necessary
		RegularTypeName rtn = new RegularTypeNameImpl(ctx);
		rtn.setName(Helpers0.string_to_qualident(typeNameString));
		var_x.setTypeName(rtn);

		LookupResultList lrl = new LookupResultListImpl();
		lrl.add("x", 1, var_x, ctx);

		//
		// CREATE VARIABLE UNDER TEST
		//
		TypeOfTypeName t = new TypeOfTypeNameImpl(ctx);
		t.typeOf(Helpers0.string_to_qualident("x"));

		//
		// SET UP EXPECTATIONS
		//
		when(mod.getFileName()).thenReturn("foo.elijah");
		when(mod.getCompilation()).thenReturn(c);
		when(c.getErrSink()).thenReturn(e);
		when(ctx.lookup("x")).thenReturn(lrl);

		//
		// VERIFY EXPECTATIONS
		//
		Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final PipelineLogic pl = boilerplate.pipelineLogic;
		final DeduceTypes2 deduceTypes2 = new DeduceTypes2(mod, pl.dp);
		TypeName tn = t.resolve(ctx, deduceTypes2);
//		tripleo.elijah.util.Stupidity.println_out_2(tn);
		assertEquals(typeNameString, tn.toString());
	}

	@Ignore
	@Test
	public void typeOfComplexQualident2() throws GResolveError {
		//
		// CREATE MOCK
		//
		Context ctx = mock(Context.class);
		Context ctx4 = mock(Context.class);

		//
		// CREATE VARIABLES
		//
		String typeNameString1 = "AbstractFactory";
		final String typeNameString = "SystemInteger";

		OS_Module mod = new OS_ModuleImpl();
		mod.setParent(mock(Compilation0.class));
		Context mod_ctx = mod.getContext();

		ClassStatement st_af = new ClassStatementImpl(mod, mod_ctx);
		ClassHeader ch = new ClassHeaderImpl(false, List_of());
		ch.setName(IdentExpressionImpl.forString("AbstractFactory"));
		st_af.setHeader(ch);
		ClassStatement sysint = new ClassStatementImpl(mod, mod_ctx);
		ClassHeader ch2 = new ClassHeaderImpl(false, List_of());
		ch2.setName(IdentExpressionImpl.forString("SystemInteger"));
		sysint.setHeader(ch2);

		VariableSequence vs = new VariableSequenceImpl(st_af.getContext());
		VariableStatement var_y = vs.next();
		var_y.setName(IdentExpressionImpl.forString("y"));
		RegularTypeName rtn_y = new RegularTypeNameImpl(ctx);
		rtn_y.setName(Helpers0.string_to_qualident(typeNameString));
		var_y.setTypeName(rtn_y);

		st_af.add(vs);

		VariableStatementImpl var_x = new VariableStatementImpl(null);
		var_x.setName(Helpers0.string_to_ident("x")); // not necessary
		RegularTypeName rtn_x = new RegularTypeNameImpl(ctx);
		rtn_x.setName(Helpers0.string_to_qualident(typeNameString1));
		var_x.setTypeName(rtn_x);

		LookupResultList lrl = new LookupResultListImpl();
		lrl.add("x", 1, var_x, ctx);
		LookupResultList lrl2 = new LookupResultListImpl();
		lrl2.add(typeNameString1, 1, st_af, ctx);
		LookupResultList lrl3 = new LookupResultListImpl();
		lrl3.add("SystemInteger", 1, sysint, ctx);
		LookupResultList lrl4 = new LookupResultListImpl();
		lrl4.add("y", 1, var_y, ctx4);

		//
		// CREATE VARIABLE UNDER TEST
		//
		TypeOfTypeName t = new TypeOfTypeNameImpl(ctx);
		t.typeOf(Helpers0.string_to_qualident("x.y"));

		//
		// SET UP EXPECTATIONS
		//
		Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final PipelineLogic pl = boilerplate.pipelineLogic;
		final DeduceTypes2 deduceTypes2 = new DeduceTypes2(mod, pl.dp);

//		when(mod.getFileName()).thenReturn("foo.elijah");
		when(ctx.lookup("x")).thenReturn(lrl);
//		when(ctx.lookup("y")).thenReturn(lrl4);
		when(ctx.lookup(typeNameString1)).thenReturn(lrl2);
//		when(ctx.lookup("SystemInteger")).thenReturn(lrl3);

		//
		// VERIFY EXPECTATIONS
		//
		TypeName tn = t.resolve(ctx, deduceTypes2);
//		tripleo.elijah.util.Stupidity.println_out_2(tn);
		assertEquals(typeNameString, tn.toString());
	}

	@Ignore
	@Test
	public void typeOfComplexQualident3() throws GResolveError {
		//
		// CREATE MOCK
		//
		Context ctx = mock(Context.class);

		//
		// CREATE VARIABLES
		//
		String typeNameString1 = "package1.AbstractFactory";
		final String typeNameString = "SystemInteger";

		OS_Module mod = new OS_ModuleImpl();
		Context mod_ctx = mod.getContext();

		ClassStatement st_af = new ClassStatementImpl(mod, mod_ctx);
		st_af.setName(IdentExpressionImpl.forString("AbstractFactory"));
		final OS_Package package1 = new OS_PackageImpl(Helpers0.string_to_qualident("package1"), 1);
		st_af.setPackageName(package1);

		VariableSequenceImpl vs = new VariableSequenceImpl(st_af.getContext());
		VariableStatementImpl var_y = new VariableStatementImpl(vs);
		var_y.setName(IdentExpressionImpl.forString("y"));
		RegularTypeName rtn_y = new RegularTypeNameImpl(ctx);
		rtn_y.setName(Helpers0.string_to_qualident(typeNameString));
		var_y.setTypeName(rtn_y);

		st_af.add(vs);

		VariableStatementImpl var_x = new VariableStatementImpl(null);
		var_x.setName(Helpers0.string_to_ident("x")); // not necessary
		RegularTypeName rtn_x = new RegularTypeNameImpl(ctx);
		rtn_x.setName(Helpers0.string_to_qualident(typeNameString1));
		var_x.setTypeName(rtn_x);

		LookupResultList lrl = new LookupResultListImpl();
		lrl.add("x", 1, var_x, ctx);
		LookupResultList lrl2 = new LookupResultListImpl();
		lrl2.add("package1", 1, null, ctx);

		//
		// CREATE VARIABLE UNDER TEST
		//
		TypeOfTypeName t = new TypeOfTypeNameImpl(ctx);
		t.typeOf(Helpers0.string_to_qualident("x.y"));

		//
		// SET UP EXPECTATIONS
		//
		when(ctx.lookup("x")).thenReturn(lrl);
		when(ctx.lookup("package1")).thenReturn(lrl2);

		//
		// VERIFY EXPECTATIONS
		//
		Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final PipelineLogic pl = boilerplate.pipelineLogic;
		final DeduceTypes2 deduceTypes2 = new DeduceTypes2(mod, pl.dp);

		TypeName tn = t.resolve(ctx, deduceTypes2);
//		tripleo.elijah.util.Stupidity.println_out_2(tn);
		assertEquals(typeNameString, tn.toString());
	}

	@Ignore
	@Test
	public void typeOfSimpleQualident() throws GResolveError {
		//
		// CREATE MOCKS
		//
		Context ctx = mock(Context.class);
		OS_Module    mod = mock(OS_Module.class);
		Compilation0 c   = mock(Compilation0.class);

		//
		// CREATE VARIABLES
		//
		ErrSink e = new EDL_ErrSink();

		String typeNameString = "AbstractFactory";

		VariableStatement var_x = new VariableStatementImpl(null);
		var_x.setName(Helpers0.string_to_ident("x")); // not necessary
		RegularTypeName rtn = new RegularTypeNameImpl(ctx);
		rtn.setName(Helpers0.string_to_qualident(typeNameString));
		var_x.setTypeName(rtn);

		LookupResultList lrl = new LookupResultListImpl();
		lrl.add("x", 1, var_x, ctx);

		//
		// CREATE VARIABLE UNDER TEST
		//
		TypeOfTypeName t = new TypeOfTypeNameImpl(ctx);
		t.typeOf(Helpers0.string_to_qualident(var_x.getName()));

		//
		// SET UP EXPECTATIONS
		//
		when(mod.getFileName()).thenReturn("foo.elijah");
		when(c.getErrSink()).thenReturn(e);
		when(mod.getCompilation()).thenReturn(c);
		when(ctx.lookup(var_x.getName())).thenReturn(lrl);

		//
		// VERIFY EXPECTATIONS
		//

		Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final PipelineLogic pl = boilerplate.pipelineLogic;
		final DeduceTypes2 deduceTypes2 = new DeduceTypes2(mod, pl.dp);
		final TypeName tn = t.resolve(ctx, deduceTypes2);
//		tripleo.elijah.util.Stupidity.println_out_2(tn);
		assertEquals(typeNameString, tn.toString());
	}

}

//
//
//
