/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.stages.deduce;

import org.jetbrains.annotations.NotNull;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import org.junit.*;

import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.contexts.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang.types.OS_UserType;
import tripleo.elijah.lang2.BuiltInTypes;
import tripleo.elijah.stages.logging.*;
import tripleo.elijah.util.*;
import tripleo.elijah.stages.deduce.Resolve_Ident_IA.DeduceElementIdent;
import tripleo.elijah.stages.deduce.post_bytecode.DeduceElement3_IdentTableEntry;
import tripleo.elijah.stages.gen_fn.BaseEvaFunction;
import tripleo.elijah.stages.gen_fn.GenType;
import tripleo.elijah.stages.gen_fn.IdentTableEntry;
import tripleo.elijah.test_help.Boilerplate;
import tripleo.elijah.world.i.WorldModule;
import tripleo.elijah.world.impl.DefaultWorldModule;
import tripleo.elijah_elevated.comp.backbone.CompilationEnclosure;

import static tripleo.elijah.util.Helpers.List_of;

/**
 * Useless tests. We really want to know if a TypeName will resolve to the same
 * types
 */
@Ignore
public class DeduceTypesTest {

	private GenType x;

	private boolean genTypeTypenameEquals(OS_Type aType, @NotNull GenType genType) {
		return genType.getTypeName().equals(aType);
	}

	@Before //Each
	public void setUp() {
		final Boilerplate boilerplate = new Boilerplate();
		boilerplate.get();
		boilerplate.getGenerateFiles(boilerplate.defaultMod());

		final OS_Module mod = boilerplate.defaultMod();
		final ModuleContext mctx = new ModuleContext__(mod);
		mod.setContext(mctx);

		final ClassStatement cs = new ClassStatementImpl(mod, mod.getContext());
		final ClassHeader ch = new ClassHeaderImpl(false, List_of());
		ch.setName(Helpers0.string_to_ident("Test"));
		cs.setHeader(ch);
		final FunctionDef fd = cs.funcDef();
		fd.setName(Helpers0.string_to_ident("test"));
		final Scope3 scope3 = new Scope3Impl(fd);
		final VariableSequence vss = scope3.statementClosure().varSeq(fd.getContext());
		final VariableStatement vs = vss.next();
		final IdentExpression x_ident = Helpers0.string_to_ident("x");
		x_ident.setContext(fd.getContext());
		vs.setName(x_ident);
		final Qualident qu = new QualidentImpl();
		qu.append(Helpers0.string_to_ident("Integer"));
		((NormalTypeName) vs.typeName()).setName(qu);
		vs.typeName().setContext(fd.getContext());
		fd.scope(scope3);
		fd.postConstruct();
		cs.postConstruct();
		mod.postConstruct();
		final IFunctionContext fc = (IFunctionContext) fd.getContext(); // TODO needs to be mocked
		final IdentExpression  x1 = Helpers0.string_to_ident("x");
		x1.setContext(fc);

		final Compilation0 compilation = mod.getCompilation();
		final Operation2<GWorldModule> opg = compilation.findPrelude("c");
		final WorldModule success = (WorldModule)opg.success();

		mod.setPrelude(success.module());

		// final PipelineLogic pl = boilerplate.pipelineLogic;

		final ElLog_.Verbosity verbosity = CompilationImpl.gitlabCIVerbosity();
		final DeducePhase      dp        = boilerplate.getDeducePhase();

		var wm = new DefaultWorldModule(mod, (CompilationEnclosure) boilerplate.comp.getCompilationEnclosure());

		final DeduceTypes2 d = dp.deduceModule(wm, dp.generatedClasses, verbosity);

		// final @NotNull GenerateFunctions gf =
		// boilerplate.pr.pipelineLogic().generatePhase.getGenerateFunctions(mod);

		final BaseEvaFunction bgf = mock(BaseEvaFunction.class);

		final IdentTableEntry ite = new IdentTableEntry(0, x1, x1.getContext(), bgf);
		final DeduceElementIdent dei = new DeduceElementIdent(ite);
		final DeduceElement3_IdentTableEntry de3_ite = ite.getDeduceElement3(d, bgf);

		final Operation2<GWorldModule> fpl0 = boilerplate.comp.findPrelude("c");
		assert fpl0.mode() == Mode.SUCCESS;
		// final Operation2<OS_Module> fpl = boilerplate.comp.findPrelude("c");
		mod.setPrelude(((WorldModule) fpl0.success()).module());

		final DeduceElement3_IdentTableEntry xxx = DeduceLookupUtils.deduceExpression2(de3_ite, fc);
		this.x = xxx.genType();
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_2(String.valueOf(this.x));
	}

	/**
	 * TODO This test fails beacause we are comparing a BUILT_IN vs a USER OS_Type.
	 * It fails because Integer is an interface and not a BUILT_IN
	 */
	@Test // (expected = ResolveError.class)
	public void testDeduceIdentExpression1() {
		final BuiltInTypes bi_integer = new OS_BuiltinType(BuiltInTypes.SystemInteger).getBType();
		final BuiltInTypes inferred_t = x.getResolved().getBType();

		assertEquals(bi_integer, inferred_t);
	}

	/**
	 * Now comparing {@link RegularTypeName} to {@link VariableTypeName} works
	 */
	@Test
	public void testDeduceIdentExpression2() {
		final RegularTypeName tn = new RegularTypeNameImpl();
		final Qualident tnq = new QualidentImpl();
		tnq.append(Helpers0.string_to_ident("Integer"));
		tn.setName(tnq);
		assertTrue(genTypeTypenameEquals(new OS_UserType(tn), x/* .getTypeName() */));
	}

	@Test
	public void testDeduceIdentExpression3() {
		final VariableTypeName tn = new VariableTypeNameImpl();
		final Qualident tnq = new QualidentImpl();
		tnq.append(Helpers0.string_to_ident("Integer"));
		tn.setName(tnq);
		assertEquals(new OS_UserType(tn).getTypeName(), x.getTypeName().getTypeName());
		assertTrue(genTypeTypenameEquals(new OS_UserType(tn), x));
	}

	@Test
	public void testDeduceIdentExpression4() {
		final VariableTypeName tn = new VariableTypeNameImpl();
		final Qualident tnq = new QualidentImpl();
		tnq.append(Helpers0.string_to_ident("Integer"));
		tn.setName(tnq);
		assertEquals(new OS_UserType(tn).getTypeName(), x.getTypeName().getTypeName());
		assertTrue(genTypeTypenameEquals(new OS_UserType(tn), x));
		assertEquals(new OS_UserType(tn).toString(), x.getTypeName().toString());
	}

}

//
//
//
