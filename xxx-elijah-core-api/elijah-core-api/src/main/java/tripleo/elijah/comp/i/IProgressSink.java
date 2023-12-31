package tripleo.elijah.comp.i;

public interface IProgressSink {
	enum Codes {
		LazyCompilerInstructions_inst(750),
		DefaultCCI_accept(131),
		EzM__realParseEzFile(166),
		DefaultCompilationBus__pollProcess(9191);

		private final int value;

		Codes(final int aValue) {
			value = aValue;
		}

		public int value() {
			return value;
		}
	}

	void note(Codes aCode, ProgressSinkComponent aCci, int aType, Object[] aParams);
}
