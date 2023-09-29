/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.util;

import org.apache.commons.codec.digest.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.security.*;
import java.util.*;
import java.util.stream.*;

/**
 * Created 9/10/20 3:44 PM
 */
public enum Helpers {
	;

	public static @NotNull String getHash(byte[] aBytes) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");

//		String input;
//		md.update(input.getBytes(StandardCharsets.UTF_8));
		md.update(aBytes);

		byte[] hashBytes = md.digest();

		StringBuilder sb = new StringBuilder();
		for (byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	public static @NotNull Operation<String> getHashForFilename(final @NotNull String aFilename) {
		try {
			final String hdigest = new DigestUtils(MessageDigestAlgorithms.SHA_256).digestAsHex(new File(aFilename));

			if (hdigest != null) {
				return Operation.success(hdigest);
			} else {
				return Operation.failure(new Exception("apache digest returns null"));
			}
		} catch (IOException aE) {
			return Operation.failure(aE);
		}
	}

	@NotNull
	public static <E> List<E> List_of(@NotNull final E... e1) {
		final List<E> r = new ArrayList<E>();
		Collections.addAll(r, e1);
		return r;
	}

	public static <T> List<String> mapCollectionElementsToString(final List<T> instructionArguments) {
		return instructionArguments.stream().map(x -> x.toString()).collect(Collectors.toList());
	}

	@NotNull
	public static String remove_single_quotes_from_string(final @NotNull String s) {
		return s.substring(1, s.length() - 1);
	}

	public static @NotNull String String_join(@NotNull String separator, @NotNull Iterable<String> stringIterable) {
		if (false) {
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
}

//
//
//
