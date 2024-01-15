package tripleo.elijah.util;

import org.jetbrains.annotations.Contract;

public enum SimplePrintLoggerToRemoveSoon {
	;

	@Contract(pure = true)
	public static void println_err(final String aS) {
		//System.err.println(aS);
	}

	public static void println_err_2(final String aS) {
		// System.err.println(String.valueOf(aS));
	}

	@Contract(pure = true) // ??
	public static void println_err_3(final String aS) {
		//System.err.println(aS);
	}

	@Contract(pure = true)
	public static void println_err2(final String aS) {
		//System.err.println(""+aS);
	}

	@Contract(pure = true)
	public static void println_out(final String aS) {
//		System.out.println(aS);
	}

	@Contract(pure = true)
	public static void println_out_2(final Object aS) {
		// System.out.println("" + aS);
	}

	public static void println_out_3(final String aS) {
//		System.out.println(aS);
	}

	@Contract(pure = true)
	public static void println2(final String aS) {
//		System.out.println(""+aS);
	}

	public static void println_err_4(final Object aS) {
		//System.err.println(""+aS);
	}

	public static void println_out_4(final Object aS) {
		System.out.println("SimplePrintLoggerToRemoveSoon::println_out_4 >> " + aS);
	}

	public static void println_out_4(final int code, final String message) {
		if (false) {
			SimplePrintLoggerToRemoveSoon.println_out_4(code + " " + message);
		}
	}

	public static void println_err_5(final String aString) {
		System.err.println("println_err_5 " + aString);
	}

	public static void println_out_5(final String aString) {
		System.out.println("println_out_5 " + aString);
	}
}
