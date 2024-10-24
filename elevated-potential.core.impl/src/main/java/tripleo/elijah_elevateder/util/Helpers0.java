/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.util;

import antlr.CommonToken;
import antlr.Token;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah.lang.i.*;
import tripleo.elijah_fluffy.util.Operation;
import tripleo.elijah_fluffy.util.TabbedOutputStream;
import tripleo.elijah_elevated_durable.lang_impl.*;
import tripleo.elijah_elevated_durable.parser.antlr2.ElijjahTokenTypes;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created 9/10/20 3:44 PM
 */
public enum Helpers0 {
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

	// TODO this method is just ugly
	@Nullable
	public static String getHashForFilenameJava(@NotNull String aFilename, @NotNull ErrSink aErrSink)
			throws IOException {
		final MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException aE) {
			aErrSink.exception(aE);
			return null;
		}

		final File file = new File(aFilename);
		long size = file.length();
		byte[] ba = new byte[64 * 1024]; // README Counting on reasonable sizes here
		FileInputStream bb = null;
		try {
			bb = new FileInputStream(file);

			int n = 0, m = 0;

			while (n != -1) {
				n = bb.read(ba);
				m += n;
				md.update(ba);
			}

			assert m == size - 1;

			byte[] hashBytes = md.digest();

			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		} finally {
			if (bb != null)
				bb.close();
		}
	}

	@NotNull
	public static <E> List<E> List_of(@NotNull final E... e1) {
		final List<E> r = new ArrayList<E>();
		Collections.addAll(r, e1);
		return r;
	}

	public static @NotNull Token makeToken(final String aText) {
		final CommonToken t = new CommonToken();
		t.setText(aText);
		return t;
	}

	public static <T> List<String> mapCollectionElementsToString(final List<T> instructionArguments) {
		return instructionArguments.stream().map(x -> x.toString()).collect(Collectors.toList());
	}

	public static void printXML(final Object obj, @NotNull final TabbedOutputStream tos) {
		SimplePrintLoggerToRemoveSoon.println_err_4("** XStream support has been disabled");
		/*
		 * final XStream x = new XStream(); //x.setMode(XStream.ID_REFERENCES);
		 * x.toXML(obj, tos.getStream());
		 */
	}

	public static @Nullable IExpression qualidentToDotExpression2(@NotNull final List<IdentExpression> ts) {
		return qualidentToDotExpression2(ts, 1);
	}

	public static @Nullable IExpression qualidentToDotExpression2(@NotNull final List<IdentExpression> ts, int i) {
		if (ts.size() == 1)
			return ts.get(0);
		if (ts.size() == 0)
			return null;
		IExpression r = ts.get(0);
//		int i=1;
		while (ts.size() > i) {
			final IExpression dotExpression = qualidentToDotExpression2(ts.subList(i, ts.size()), i + 1);
			i++;
			if (dotExpression == null)
				break;
//			r.setRight(dotExpression);
			r = new DotExpressionImpl(r, dotExpression);
		}
		return r;
	}

	public static @Nullable IExpression qualidentToDotExpression2(@NotNull final Qualident q) {
		return qualidentToDotExpression2(q.parts(), 1);
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

	@NotNull
	public static IdentExpression string_to_ident(final String txt) {
		final CommonToken t = new CommonToken(ElijjahTokenTypes.IDENT, txt);
		return new IdentExpressionImpl(t, "<inline-absent>");
	}

	public static @NotNull Qualident string_to_qualident(@NotNull String x) {
		Qualident q = new QualidentImpl();
		for (String xx : x.split("\\.")) {
			q.append(string_to_ident(xx));
		}
		return q;
	}
}

//
//
//
