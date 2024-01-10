package tripleo.elijah.comp.i;

import org.jetbrains.annotations.*;

// FIXME this is ugly
public enum ProgressSinkComponent {
	CCI {
		@Override
		public boolean isPrintErr(final IProgressSink.Codes aCode, final int aType) {
			return aCode.value() == 131 && aType == -1;
		}

		@Override
		public @NotNull String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams) {
			return "[-- CCI progress ] " + aType + " " + aParams[0]; // ci.getName
		}
	},
	CompilationBus_ {
		@Override
		public boolean isPrintErr(final IProgressSink.Codes aCode, final int aType) {
			return true;
		}

		@Override
		public String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams) {
			return "*** CompilationBus ->> " + aParams[0];
		}
	}, DefaultCompilationBus {
		@Override
		public boolean isPrintErr(final IProgressSink.Codes aCode, final int aType) {
			return false;
		}

		@Override
		public String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams) {
			return switch (aType) {
			case 5784 -> "*** DefaultCompilationBus ->> Start polling";
			case 5757 -> "*** DefaultCompilationBus ->> %s".formatted(aParams[0]);
			case 5758 -> "*** DefaultCompilationBus ->> poll returns null";
			default -> throw new IllegalStateException("Unexpected value: " + aType);
			};
		}
	};

	public abstract boolean isPrintErr(final IProgressSink.Codes aCode, final int aType);

	public abstract String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams);
}
