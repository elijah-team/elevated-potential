package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.util.*;

import java.io.*;
import java.util.*;

class DefaultCompFactory implements CompFactory {
	private final CompilationImpl compilation;

	public DefaultCompFactory(CompilationImpl aCompilation) {
		compilation = aCompilation;
	}

	@Contract("_ -> new")
	@Override
	public @NotNull CompilerBeginning createBeginning(final @NotNull CompilationRunner aCompilationRunner) {
		final CompilerInstructions rootCI = compilation.getRootCI();
		final List<CompilerInput> inputs = compilation.getInputs();
		final IProgressSink progressSink = aCompilationRunner.getProgressSink();
		final Compilation.CompilationConfig cfg = compilation.cfg();

		return new CompilerBeginning(compilation, rootCI, inputs, progressSink, cfg);
	}

	@Override
	public @NotNull ICompilationAccess createCompilationAccess() {
		return new DefaultCompilationAccess(compilation);
	}

	@Contract(" -> new")
	@Override
	public @NotNull ICompilationBus createCompilationBus() {
		return new DefaultCompilationBus(Objects.requireNonNull(compilation.getCompilationEnclosure()));
	}

	@Override
	public @NotNull InputRequest createInputRequest(final File aFile, final boolean aDo_out,
			final @Nullable LibraryStatementPart aLsp) {
		return new InputRequest(aFile, aDo_out, aLsp);
	}

	@Override
	public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
		return new EIT_ModuleInput(aModule, compilation);
	}

	@Override
	public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
		Qualident R = new QualidentImpl();
		for (String s : sl) {
			R.append(Helpers0.string_to_ident(s));
		}
		return R;
	}

	@Override
	public CK_ObjectTree createObjectTree() {
		return compilation.new DefaultObjectTree();
	}

	@Override
	public CY_ElijahSpecParser defaultElijahSpecParser(final ElijahCache elijahCache) {
		return new CY_ElijahSpecParser() {
			@Override
			public Operation2<OS_Module> parse(ElijahSpec spec) {
				var c = compilation;
				return CX_realParseElijjahFile2.realParseElijjahFile2(spec, elijahCache, c);
			}
		};
	}

	@Override
	public CY_EzSpecParser defaultEzSpecParser(final EzCache aEzCache) {
/*
		return new CY_ElijahSpecParser() {
			@Override
			public Operation2<OS_Module> parse(ElijahSpec spec) {
				Compilation c = compilation;
				return CX_realParseElijjahFile2.realParseElijjahFile2(spec, elijahCache, c);
			}
		};
*/
		return null;
	}
}
