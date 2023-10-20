package tripleo.elijah.comp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.*;
import tripleo.elijah.comp.internal.OStageProcess;
import tripleo.elijah.util.NotImplementedException;

public enum Stages implements GStages {
	D("D") {
		@Override
		public @NotNull RuntimeProcess getProcess(final ICompilationAccess aCa, final GProcessRecord aPr) {
			return new DStageProcess(aCa, (ProcessRecord) aPr);
		}

		@Override
		public void writeLogs(final @NotNull ICompilationAccess aCompilationAccess) {
			aCompilationAccess.writeLogs();
		}
	},
	E("E") {
		@Override
		public @NotNull RuntimeProcess getProcess(final ICompilationAccess aCa, final GProcessRecord aPr) {
			return new EmptyProcess(aCa, (ProcessRecord) aPr);
		}

		@Override
		public void writeLogs(final ICompilationAccess aCompilationAccess) {
			NotImplementedException.raise();
		}
	},
	O("O") {
		@Override
		public @NotNull RuntimeProcess getProcess(final ICompilationAccess aCa, final @NotNull GProcessRecord aPr) {
			return new OStageProcess(aCa, (ProcessRecord) aPr);
		}

		@Override
		public void writeLogs(final @NotNull ICompilationAccess aCompilationAccess) {
			aCompilationAccess.writeLogs();
		}
	}, // Output, // ??
	S("S") {
		@Override
		public RuntimeProcess getProcess(final ICompilationAccess aCa, final GProcessRecord aPr) {
			throw new NotImplementedException();
		}

		@Override
		public void writeLogs(final @NotNull ICompilationAccess aCompilationAccess) {
			aCompilationAccess.writeLogs();
		}
	};

	private final String s;

	@Contract(pure = true)
	Stages(final String aO) {
		s = aO;
	}

}
