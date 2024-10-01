package tripleo.elijah_elevated_durable.pipelines.write;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah.nextgen.inputtree.EIT_Input;
import tripleo.elijah.nextgen.inputtree.EIT_InputType;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.nextgen.outputstatement.EX_Explanation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static tripleo.elijah_fluffy.util.Helpers.List_of;

public class LSPrintStream implements XPrintStream {
	private final StringBuilder sb = new StringBuilder();
	private final List<String>  ff = new ArrayList<>();

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

	public record LSResult(List<String> buffer, List<String> fs) {
		public List<EIT_Input> fs2(final Compilation c) {
			return fs.stream().map(s -> new MyEIT_Input(c, s)).collect(Collectors.toList());
		}

		public List<EG_Statement> getStatement() {
			return buffer.stream().map(str -> EG_Statement.of(str, EX_Explanation.withMessage("WriteBuffers")))
					.collect(Collectors.toList());
		}

		//@Override
		//public String toString() {
		//	return "LSResult[" +
		//			"buffer=" + buffer + ", " +
		//			"fs=" + fs + ']';
		//}
	}

	public static class MyEIT_Input implements EIT_Input {
		private final Compilation c;
		private final String      s;

		public MyEIT_Input(final Compilation aC, final String aS) {
			c = aC;
			s = aS;
		}

		@Override
		public EIT_InputType getType() {
			return EIT_InputType.ELIJAH_SOURCE;
		}
	}
}
