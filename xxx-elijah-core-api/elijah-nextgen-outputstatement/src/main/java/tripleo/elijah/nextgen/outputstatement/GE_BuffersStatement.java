package tripleo.elijah.nextgen.outputstatement;

import java.util.*;
import java.util.stream.*;

import org.jetbrains.annotations.*;

import tripleo.util.buffer.*;

// TODO 11/29 looks a little bit like it doesn't belong
public class GE_BuffersStatement implements EG_Statement {
	private final Map.Entry<String, Collection<Buffer>> entry;

	@Contract(pure = true)
	public GE_BuffersStatement(final Map.Entry<String, Collection<Buffer>> aEntry) {
		entry = aEntry;
	}

	@Override
	public @NotNull EX_Explanation getExplanation() {
		return new GE_BuffersExplanation(this);
	}

	@Override
	public @NotNull String getText() {
		final List<String> stringIterable = entry.getValue()
				.stream()
				//.filter(entry)
				.map(Buffer::getText)
				.collect(Collectors.toList())
				;
		return __.String_join("\n\n", stringIterable);
	}

	// TODO 11/29 why is message and getText different here?
	private static class GE_BuffersExplanation implements EX_Explanation {
		private final GE_BuffersStatement st;
		private String message = "buffers to statement";

		public GE_BuffersExplanation(final GE_BuffersStatement aGEBuffersStatement) {
			st = aGEBuffersStatement;
		}

		@Override
		public @NotNull String message() {
			return "GE_BuffersExplanation";
		}

		public void setMessage(final String aMessage) {
			message = aMessage;
		}

		public @NotNull String getText() {
			return message;
		}

		public GE_BuffersStatement getStatement() {
			return st;
		}
	}
}
