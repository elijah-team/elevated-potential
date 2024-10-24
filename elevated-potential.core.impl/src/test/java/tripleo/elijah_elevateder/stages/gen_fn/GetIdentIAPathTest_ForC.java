/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.stages.gen_fn;

import org.jetbrains.annotations.NotNull;
import org.junit.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;
import tripleo.elijah_elevated_durable.comp.*;
import tripleo.elijah_elevated_durable.lang_impl.*;
import tripleo.elijah_elevated_durable.world_impl.DefaultWorldModule;
import tripleo.elijah_elevateder.stages.gen_c.*;
import tripleo.elijah_elevateder.stages.gen_generic.GenerateResultEnv;
import tripleo.elijah_elevateder.stages.gen_generic.OutputFileFactoryParams;
import tripleo.elijah_elevateder.stages.instructions.*;
import tripleo.elijah_elevateder.test_help.Boilerplate;
import tripleo.elijah_elevateder.util.Helpers0;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetIdentIAPathTest_ForC {

	EvaFunction gf;
	OS_Module   mod;
	private GenerateC       generateC;
	private EDL_Compilation compilation;
	private EDL_ErrSink     errSink;

	String getIdentIAPath(final @NotNull IdentIA ia2, EvaFunction generatedFunction, @NotNull GenerateC gc,
			CompilationEnclosure ce) {
		final CReference reference = new CReference(gc.repo(), ce);
		var x = reference.getIdentIAPath2(ia2, Generate_Code_For_Method.AOG.GET, null);
		SimplePrintLoggerToRemoveSoon.println_err_4("258 " + x);
		return x;// reference.build();
	}

	@Before //Each
	public void setUp() throws Exception {
		mod = mock(OS_Module.class);
		FunctionDef fd = mock(FunctionDef.class);
		gf = new EvaFunction(fd);

		Emit.emitting = false;

		errSink = new EDL_ErrSink();
		compilation = new EDL_Compilation(errSink, new EDL_IO());

		final CompilationEnclosure ce = compilation.getCompilationEnclosure();
		ce.setCompilationAccess(new EDL_CompilationAccess(compilation));

		GenerateResultEnv fileGen = null;
		generateC = new GenerateC(new OutputFileFactoryParams(new DefaultWorldModule(mod, ce), ce), fileGen);
	}

	@Ignore
	@Test
	public void testManualXDotFoo() {
		@NotNull
		IdentExpression x_ident = IdentExpressionImpl.forString("X");
		@NotNull
		IdentExpression foo_ident = IdentExpressionImpl.forString("foo");
		//
		VariableSequence vsq = new VariableSequenceImpl(null);
		vsq.setParent(mock(ClassStatement.class));
		VariableStatement foo_vs = new VariableStatementImpl(vsq);
		foo_vs.setName(foo_ident);
		//
		OS_Type         type      = null;
		TypeTableEntry  tte       = gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, type, x_ident);
		int             int_index = gf.addVariableTableEntry("x", VariableTableType.VAR, tte, mock(VariableStatement.class));
		int             ite_index = gf.addIdentTableEntry(foo_ident, null);
		IdentTableEntry ite       = gf.getIdentTableEntry(ite_index);
		ite.setResolvedElement(foo_vs);
		ite.setBacklink(new IntegerIA(int_index, gf));
		IdentIA ident_ia = new IdentIA(ite_index, gf);
		String x = getIdentIAPath(ident_ia, gf, generateC, compilation.getCompilationEnclosure());
		assertEquals("vvx->vmfoo", x);
	}

	@Ignore
	@Test
	public void testManualXDotFoo2() {
		@NotNull
		IdentExpression x_ident = IdentExpressionImpl.forString("x");
		@NotNull
		IdentExpression foo_ident = IdentExpressionImpl.forString("foo");
		//
		final OS_Element mock_class = mock(ClassStatement.class);
		when(gf.getFD().getParent()).thenReturn(mock_class);
		when(gf.getFD().getParent()).thenReturn(mock_class);
		// replay(gf.getFD());

		VariableSequence vsq = new VariableSequenceImpl(null);
		vsq.setParent(mock(ClassStatement.class));
		VariableStatement foo_vs = new VariableStatementImpl(vsq);
		foo_vs.setName(foo_ident);
		VariableSequence vsq2 = new VariableSequenceImpl(null);
		vsq.setParent(mock(ClassStatement.class));
		VariableStatement x_vs = new VariableStatementImpl(vsq2);
		x_vs.setName(x_ident);

		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final GeneratePhase generatePhase = boilerplate.pipelineLogic().generatePhase;

		/*
		 * when(mod.pullPackageName()).thenReturn(OS_Package.default_package);
		 * mod.add(anyObject(ClassStatement.class)); replay(mod); ClassStatement el1 =
		 * new ClassStatementImpl(mod, null);
		 */

		GenerateFunctions gen = generatePhase.getGenerateFunctions(mod);
		Context ctx = mock(Context.class);
		//
		DotExpression expr = new DotExpressionImpl(x_ident, foo_ident);
		InstructionArgument xx = gen.simplify_expression(expr, gf, ctx);
		//
		@NotNull
		IdentTableEntry x_ite = gf.getIdentTableEntry(0); // x
		x_ite.setResolvedElement(x_vs);
		@NotNull
		IdentTableEntry foo_ite = gf.getIdentTableEntry(1); // foo
		foo_ite.setResolvedElement(foo_vs);
		//
		IdentIA ident_ia = (IdentIA) xx;
		String x = getIdentIAPath(ident_ia, gf, generateC, compilation.getCompilationEnclosure());
//		assertEquals("vvx->vmfoo", x);  // TODO real expectation, IOW output below is wrong
		// FIXME actually compiler should comlain that it can't find x
		// assertEquals("->vmx->vmfoo", x);
		assertEquals("vmx->vmfoo", x);
	}

	@Ignore
	@Test
	public void testManualXDotFoo3() {
		IdentExpression x_ident = Helpers0.string_to_ident("x");
		@NotNull
		IdentExpression foo_ident = Helpers0.string_to_ident("foo");
		//
		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final GeneratePhase generatePhase = boilerplate.pipelineLogic().generatePhase;

		GenerateFunctions gen = generatePhase.getGenerateFunctions(mod);
		Context ctx = mock(Context.class);
		//
		OS_Type type = null;
		TypeTableEntry tte = gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, type, x_ident);
		int int_index = gf.addVariableTableEntry("x", VariableTableType.VAR, tte, mock(VariableStatement.class));
		//
		DotExpression expr = new DotExpressionImpl(x_ident, foo_ident);
		InstructionArgument xx = gen.simplify_expression(expr, gf, ctx);
		//

		// int ite_index = gf.addIdentTableEntry(foo_ident);
		// IdentTableEntry ite = gf.getIdentTableEntry(ite_index);
		// ite.backlink = new IntegerIA(int_index);

		VariableSequence vsq = new VariableSequenceImpl(null);
		vsq.setParent(mock(ClassStatement.class));
		VariableStatement foo_vs = new VariableStatementImpl(vsq);
		foo_vs.setName(foo_ident);

		IdentIA ident_ia = (IdentIA) xx;
		@NotNull
		IdentTableEntry ite = ((IdentIA) xx).getEntry();
		ite.setResolvedElement(foo_vs);

		String x = getIdentIAPath(ident_ia, gf, generateC, compilation.getCompilationEnclosure());
//		assertEquals("vvx->vmfoo", x); // TODO real expectation
		assertEquals("vvx->vmfoo", x);
	}

	@Ignore
	@Test
	public void testManualXDotFooWithFooBeingFunction() {
		@NotNull
		IdentExpression x_ident = Helpers0.string_to_ident("x");
		@NotNull
		IdentExpression foo_ident = Helpers0.string_to_ident("foo");
		//
		Context ctx = mock(Context.class);
		Context mockContext = mock(Context.class);

		LookupResultList lrl = new LookupResultListImpl();
		LookupResultList lrl2 = new LookupResultListImpl();

		when(mod.pullPackageName()).thenReturn(LangGlobals.default_package);
		when(mod.getFileName()).thenReturn("filename.elijah");
		// mod.add(anyObject(ClassStatement.class));
		// replay(mod);

		ClassStatement classStatement = new ClassStatementImpl(mod, ctx);
		classStatement.setName(Helpers0.string_to_ident("X")); // README not explicitly necessary

//		when(mockContext.lookup(foo_ident.getText())).thenReturn(lrl2);

//		when(classStatement.getContext().lookup(foo_ident.getText())).thenReturn(lrl2);

		lrl.add(x_ident.getText(), 1, classStatement, ctx);
		when(ctx.lookup(x_ident.getText())).thenReturn(lrl);

		FunctionDef functionDef = new FunctionDefImpl(classStatement, classStatement.getContext());
		functionDef.setName(foo_ident);
		lrl2.add(foo_ident.getText(), 1, functionDef, mockContext);

		//
		// SET UP EXPECTATIONS
		//
		// replay(ctx, mockContext);

		LookupResultList lrl_expected = ctx.lookup(x_ident.getText());

		//
		// VERIFY EXPECTATIONS
		//

		//
		final OS_Type type = classStatement.getOS_Type();
		TypeTableEntry tte = gf.newTypeTableEntry(TypeTableEntry.Type.SPECIFIED, type, x_ident);
		int int_index = gf.addVariableTableEntry("x", VariableTableType.VAR, tte, mock(VariableStatement.class));
		//
		DotExpression expr = new DotExpressionImpl(x_ident, foo_ident);
		//
		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final GeneratePhase generatePhase = boilerplate.pipelineLogic().generatePhase;
		GenerateFunctions gen = generatePhase.getGenerateFunctions(mod);
		InstructionArgument xx = gen.simplify_expression(expr, gf, ctx);

		//
		// This is the Deduce portion.
		// Not very extensive is it?
		//
		IdentIA ident_ia = (IdentIA) xx;
		IdentTableEntry ite = ident_ia.getEntry();
		ite.setStatus(BaseTableEntry.Status.KNOWN, new GenericElementHolder(functionDef));

		// This assumes we want a function call
		// but what if we want a function pointer or a curry or function reference?
		// IOW, a ProcedureCall is not specified
		String x = getIdentIAPath(ident_ia, gf, generateC, compilation.getCompilationEnclosure());

		// verify(mod, ctx, mockContext);

		assertEquals("Z-1foo(vvx)", x);
	}

}

//
//
//
