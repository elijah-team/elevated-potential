package tripleo.elijah.comp;

import org.jetbrains.annotations.*;
import tripleo.elijah.compiler_model.CM_Factory;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.contexts.*;
import tripleo.elijah.g.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

public class ModuleBuilder {
	// private final Compilation compilation;
	private final @NotNull OS_Module mod;
	private boolean     _addToCompilation = false;
	private CM_Filename _fn               = null;

	public ModuleBuilder(@NotNull Compilation aCompilation) {
//			compilation = aCompilation;
		mod = new OS_ModuleImpl();
		mod.setParent(aCompilation);
	}

	public @NotNull ModuleBuilder addToCompilation() {
		_addToCompilation = true;
		return this;
	}

	public OS_Module build() {
		if (_addToCompilation) {
			if (_fn == null) {
				throw new IllegalStateException("Filename not set in ModuleBuilder");
			}

			final @Nullable Compilation compilation = (Compilation) mod.getCompilation();
			final @NotNull LivingRepo   world       = compilation.world();
			world.addModule(mod, _fn, compilation);
		}
		return mod;
	}

	public @NotNull ModuleBuilder setContext() {
		final ModuleContext mctx = new ModuleContext__(mod);
		mod.setContext(mctx);
		return this;
	}

	public ModuleBuilder withFileName(final String aS) {
		return withFileName(CM_Factory.Filename__of(aS));
	}

	public @NotNull ModuleBuilder withFileName(CM_Filename aFn) {
		_fn = aFn;
		mod.setFileName(aFn);
		return this;
	}

	public @NotNull ModuleBuilder withPrelude(String aPrelude) {
		final Operation2<GWorldModule> prelude = mod.getCompilation().findPrelude(aPrelude);

		assert prelude.mode() == Mode.SUCCESS;

		mod.setPrelude(((WorldModule) prelude.success()).module());

		return this;
	}
}
