package tripleo.elijah.comp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.contexts.ModuleContext;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.lang.impl.OS_ModuleImpl;
import tripleo.elijah.util.Mode;
import tripleo.elijah.util.Operation2;
import tripleo.elijah.world.i.WorldModule;

public class ModuleBuilder {
	// private final Compilation compilation;
	private final @NotNull OS_Module mod;
	private boolean _addToCompilation = false;
	private @Nullable String _fn = null;

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
			if (_fn == null)
				throw new IllegalStateException("Filename not set in ModuleBuilder");
			mod.getCompilation().world().addModule(mod, _fn, mod.getCompilation());
		}
		return mod;
	}

	public @NotNull ModuleBuilder setContext() {
		final ModuleContext mctx = new ModuleContext(mod);
		mod.setContext(mctx);
		return this;
	}

	public @NotNull ModuleBuilder withFileName(String aFn) {
		_fn = aFn;
		mod.setFileName(aFn);
		return this;
	}

	public @NotNull ModuleBuilder withPrelude(String aPrelude) {
		final Operation2<WorldModule> prelude = mod.getCompilation().findPrelude(aPrelude);

		assert prelude.mode() == Mode.SUCCESS;

		mod.setPrelude(prelude.success().module());

		return this;
	}
}
