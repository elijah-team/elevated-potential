package tripleo.elijah.nextgen.outputstatement;

import java.util.stream.*;

import tripleo.elijah.ngosDebugFlags;

class __ {
	public static String String_join(String separator, Iterable<String> stringIterable) {
		if (ngosDebugFlags.FORCE_IGNORE) {
			final StringBuilder sb = new StringBuilder();

			for (final String part : stringIterable) {
				sb.append(part);
				sb.append(separator);
			}
			final String ss = sb.toString();
			final String substring = separator.substring(0, ss.length() - separator.length());
			return substring;
		}
		// since Java 1.8
		return String.join(separator, stringIterable);
	}

	// NOTE 11/30 this can be prettier ... ??
	public static String String_join(String separator, Stream<String> stringIterable) {
		final StringBuilder sb = new StringBuilder();

		stringIterable
				.forEach(part -> {
					sb.append(part);
					sb.append(separator);
				});

		final String ss = sb.toString();
		final String substring = separator.substring(0, ss.length() - separator.length());
		return substring;
	}
}
