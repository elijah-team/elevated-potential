package tripleo.elijah.comp.i;

import org.jetbrains.annotations.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.nextgen.query.*;
import tripleo.elijah.util.*;
import tripleo.elijah.world.i.*;

import java.util.function.*;

public interface CompilationFlow {
	//static CompilationFlowMember findPrelude() {
	//	return new CF_FindPrelude(aCopm);
	//}

	class CF_FindPrelude implements CompilationFlowMember {
		private final Consumer<Operation2<WorldModule>> copm;

		public CF_FindPrelude(final Consumer<Operation2<WorldModule>> aCopm) {
			copm = aCopm;
		}

		@Override
		public void doIt(final @NotNull Compilation cc, final CompilationFlow flow) {
			final Operation2<WorldModule> prl = cc.findPrelude(Compilation.CompilationAlways.defaultPrelude());
			assert (prl.mode() == Mode.SUCCESS);

			copm.accept(prl);
		}
	}

	interface CompilationFlowMember {
		void doIt(Compilation cc, final CompilationFlow flow);
	}

	static @NotNull CompilationFlowMember deduceModuleWithClasses() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {

			}
		};
	}

	static @NotNull CompilationFlowMember findMainClass() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {

			}
		};
	}

	static @NotNull CompilationFlowMember finishModule() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {

			}
		};
	}

	static @NotNull CompilationFlowMember genFromEntrypoints() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {

			}
		};
	}

	static @NotNull CompilationFlowMember getClasses() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {


			}
		};
	}

	static @NotNull CompilationFlowMember parseElijah() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {
				int y = 2;
			}
		};
	}

	static @NotNull CompilationFlowMember returnErrorCount() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {

			}
		};
	}

	static @NotNull CompilationFlowMember runFunctionMapHooks() {
		return new CompilationFlowMember() {
			@Override
			public void doIt(final Compilation cc, final CompilationFlow flow) {

			}
		};
	}

	void add(CompilationFlowMember aFlowMember);

	void run(CompilationImpl aCompilation);
}
