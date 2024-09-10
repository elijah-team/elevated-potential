package tripleo.elijah.comp.i;

import org.jetbrains.annotations.NotNull;

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
			return true;
		}

		@Override
		public String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams) {
			String result;
			switch (aType) {
			case 5784 -> {
				/*DCB_Startable.DefaultCompilationBus_POLL_PROCESS*/
				result = "*** DefaultCompilationBus ->> Start polling";
			}
			case 5757 -> {
				result = "*** DefaultCompilationBus ->> %s".formatted(aParams[0]);
				break;
			}
			case 5758 -> {
				/*DCB_Startable.DefaultCompilationBus_POLL_PROCESS_EMPTY_BEGIN*/
				result = "*** DefaultCompilationBus ->> poll returns null";
				break;
			}
			case 5789 -> {
				/*DCB_Startable.DefaultCompilationBus_POLL_PROCESS_EMPTY_END*/
				result = "*** DefaultCompilationBus ->> poll process empty end";
				break;
			}
			default -> throw new IllegalStateException("Unexpected value: " + aType);
			}
			return result;
		}
	};

	public abstract boolean isPrintErr(final IProgressSink.Codes aCode, final int aType);

	public abstract String printErr(final IProgressSink.Codes aCode, final int aType, final Object[] aParams);
}
