package tripleo.elijah.comp.internal;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.CK_ObjectTree;
import tripleo.elijah.comp.i.CY_ElijahSpecParser;
import tripleo.elijah.comp.i.ICompilationAccess;
import tripleo.elijah.comp.i.ICompilationBus;
import tripleo.elijah.comp.nextgen.*;
import tripleo.elijah.comp.nextgen.pw.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.i.Qualident;
import tripleo.elijah.lang.impl.QualidentImpl;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTree;
import tripleo.elijah.nextgen.outputtree.EOT_OutputTreeImpl;
import tripleo.elijah.util.Helpers0;
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
	                                                final @Nullable LibraryStatementPart aLsp) {
		return new InputRequest(aFile, aLsp);
	}

	@Contract("_ -> new")
	@Override
	public @NotNull EIT_ModuleInput createModuleInput(final OS_Module aModule) {
		return new EIT_ModuleInputImpl(aModule, compilation);
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
				final Compilation          c  = compilation;
				final Operation2<OS_Module> om = CX_realParseElijjahFile2.realParseElijjahFile2(spec, elijahCache, c);
				return om;
			}
		};
	}

	@Override
	public WorldModule createWorldModule(OS_Module aModule) {
		return new DefaultWorldModule(aModule, compilation.getCompilationEnclosure());
	}

	@Override
	public PW_PushWorkQueue createWorkQueue() {
		return new PW_PushWorkQueue_Blocking();
		//return new PW_PushWorkQueue_Concurrent();
	}

	@Override
	public Startable askConcurrent(final Runnable aRunnable, final String aThreadName) {
		final Thread thread = new Thread(aRunnable);
		thread.setName(aThreadName);
		return new Startable() {
			@Override
			public void start() {
				thread.start();
			}

			@Deprecated @Override
			public Thread stealThread() {
				return thread;
			}
		};
	}

	@Override
	public EOT_OutputTree createOutputTree() {
		return new EOT_OutputTreeImpl();
	}

	@Override
	public EIT_InputTree createInputTree() {
		return new EIT_InputTreeImpl();
	}

	@Override
	public CX_ParseElijahFile.ElijahSpecReader defaultElijahSpecReader(final CP_Path aLocalPrelude) {
		return new DefaultElijahSpecReader(aLocalPrelude, compilation);
	}
}
