package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.CompFactory;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.comp.InputRequest;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.CY_ElijahSpecParser;
import tripleo.elijah.comp.i.CY_EzSpecParser;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.comp.i.IProgressSink;
import tripleo.elijah.comp.nextgen.CX_realParseEzFile2;
import tripleo.elijah.comp.specs.ElijahCache;
import tripleo.elijah.comp.specs.ElijahSpec;
import tripleo.elijah.comp.specs.EzCache;
import tripleo.elijah.comp.specs.EzSpec;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.QualidentImpl;
import tripleo.elijah.nextgen.inputtree.EIT_ModuleInput;
import tripleo.elijah.util.Helpers0;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.*;
import tripleo.elijah.world.impl.*;

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
		final CompilerInstructions          rootCI       = compilation.getRootCI();
		final List<CompilerInput>           inputs       = compilation.getInputs();
		final IProgressSink                 progressSink = aCompilationRunner.getProgressSink();
		final Compilation.CompilationConfig cfg          = compilation.cfg();

		return new CompilerBeginning(compilation, rootCI, inputs, progressSink, cfg);
	}

	@Contract(" -> new")
	@Override
	public @NotNull ICompilationAccess createCompilationAccess() {
		return new DefaultCompilationAccess(compilation);
	}

	@Contract(" -> new")
	@Override
	public @NotNull ICompilationBus createCompilationBus() {
		return new DefaultCompilationBus(Objects.requireNonNull(compilation.getCompilationEnclosure()));
	}

	@Contract("_,_,_ -> new")
	@Override
	public @NotNull InputRequest createInputRequest(final File aFile,
	                                                final boolean aDo_out,
	                                                final @Nullable LibraryStatementPart aLsp) {
		return new InputRequest(aFile, aDo_out, aLsp);
	}

	@Contract("_ -> new")
	@Override
	public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
		return new EIT_ModuleInput(aModule, compilation);
	}

	@Contract("_ -> new")
	@Override
	public @NotNull Qualident createQualident(final @NotNull List<String> sl) {
		final Qualident R = new QualidentImpl();
		// README 10/13 avoid inclination to
		for (final String s : sl) {
			R.append(Helpers0.string_to_ident(s));
		}
		return R;
	}

	@Contract(" -> new")
	@Override
	public CK_ObjectTree createObjectTree() {
		return new DefaultObjectTree(compilation);
	}

	@Contract("_ -> new")
	@Override
	public CY_ElijahSpecParser defaultElijahSpecParser(final ElijahCache elijahCache) {
		return new CY_ElijahSpecParser() {
			@Override
			public Operation2<OS_Module> parse(ElijahSpec spec) {
				final Compilation c = compilation;
				final Operation2<OS_Module> om = CX_realParseElijjahFile2.realParseElijjahFile2(spec, elijahCache, c);
				return om;
			}
		};
	}

	@Contract("_ -> new")
	@Override
	public CY_EzSpecParser defaultEzSpecParser(final EzCache aEzCache) {
		return new CY_EzSpecParser() {
			@Override
			public Operation2<CompilerInstructions> parse(EzSpec spec) {
				final Compilation c = compilation;
				final Operation<CompilerInstructions> cio = CX_realParseEzFile2.realParseEzFile(c, spec, aEzCache);
				return Operation2.convert(cio);
			}
		};
	}

	@Override
	public WorldModule createWorldModule(OS_Module aModule) {
		return new DefaultWorldModule(aModule, compilation.getCompilationEnclosure());
	}
}
