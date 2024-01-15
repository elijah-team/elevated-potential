package tripleo.elijah_elevated.gu;

public class R {
	public static void asv(final int code, final String message, final R_Hook aHook) {
		aHook.hook(code, message);
		switch (code) {
		case 117117 -> {
			assert false;
		}
		default -> {
			assert false;
		}
		}
	}

	public interface R_Hook {
		void hook(int code, String message);
	}
}
