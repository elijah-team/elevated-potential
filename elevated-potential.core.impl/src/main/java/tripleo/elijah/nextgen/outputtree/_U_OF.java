package tripleo.elijah.nextgen.outputtree;

public class _U_OF {
	public static class DefaultFileNameProvider implements EOT_FileNameProvider {
		private final String r;

		public DefaultFileNameProvider(final String aR) {
			r = aR;
		}

		@Override
		public String getFilename() {
			return r;
		}
	}
}
