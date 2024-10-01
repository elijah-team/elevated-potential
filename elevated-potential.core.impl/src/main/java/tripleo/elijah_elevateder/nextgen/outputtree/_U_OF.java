package tripleo.elijah_elevateder.nextgen.outputtree;

import tripleo.elijah.nextgen.outputtree.EOT_FileNameProvider;

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
