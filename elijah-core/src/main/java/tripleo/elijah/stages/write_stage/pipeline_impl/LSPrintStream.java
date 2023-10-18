package tripleo.elijah.stages.write_stage.pipeline_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.inputtree.EIT_InputType;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static tripleo.elijah.util.Helpers.List_of;

public class LSPrintStream implements XPrintStream {
	public static final class LSResult {
		private final List<String> buffer;
		private final List<String> fs;

		public LSResult(List<String> buffer, List<String> fs) {
			this.buffer = buffer;
			this.fs     = fs;
		}

		public List<EIT_Input> fs2(final Compilation c) {
				return fs.stream().map(s -> new MyEIT_Input(c, s)).collect(Collectors.toList());
			}

			public List<EG_Statement> getStatement() {
				return buffer.stream().map(str -> EG_Statement.of(str, EX_Explanation.withMessage("WriteBuffers")))
						.collect(Collectors.toList());
			}

		public List<String> buffer() {
			return buffer;
		}

		public List<String> fs() {
			return fs;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) return true;
			if (obj == null || obj.getClass() != this.getClass()) return false;
			var that = (LSResult) obj;
			return Objects.equals(this.buffer, that.buffer) &&
					Objects.equals(this.fs, that.fs);
		}

		@Override
		public int hashCode() {
			return Objects.hash(buffer, fs);
		}

		@Override
		public String toString() {
			return "LSResult[" +
					"buffer=" + buffer + ", " +
					"fs=" + fs + ']';
		}

		}

	public static class MyEIT_Input implements EIT_Input {
		private final Compilation c;
		private final String s;

		public MyEIT_Input(final Compilation aC, final String aS) {
			c = aC;
			s = aS;
		}

		@Override
		public EIT_InputType getType() {
			return EIT_InputType.ELIJAH_SOURCE;
		}
	}

	private final StringBuilder sb = new StringBuilder();

	private final List<String> ff = new ArrayList<>();

	public void addFile(final String aS) {
		ff.add(aS);
	}

	public LSResult getResult() {
		return new LSResult(List_of(getString()), ff);
	}

	public @NotNull String getString() {
		return sb.toString();
	}

	@Override
	public void println(final String aS) {
		sb.append(aS);
		sb.append('\n');
	}
}
