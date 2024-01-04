package tripleo.elijah.javac_model.annotation.processing;

import javax.annotation.processing.Completion;

public enum Completions {
	;

	private static class SimpleCompletion implements Completion {
        private final String value;
        private final String message;

        SimpleCompletion(String value, String message) {
            if (value == null || message == null)
                throw new NullPointerException("Null completion strings not accepted.");
            this.value = value;
            this.message = message;
        }

        @Override
		public String getValue() {
            return value;
        }

        @Override
		public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "[\"" + value + "\", \"" + message + "\"]";
        }
        // Default equals and hashCode are fine.
    }

    public static Completion of(String value, String message) {
        return new SimpleCompletion(value, message);
    }

    public static Completion of(String value) {
        return new SimpleCompletion(value, "");
    }
}
