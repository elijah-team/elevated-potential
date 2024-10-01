package tripleo.elijah_elevateder.stages.gen_c;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static tripleo.elijah.util.Helpers.String_join;

/**
 * (Unrealized) Intent: provide annotations in output code to show what
 * generated text is generated from
 */
class SpecialText {
	@Contract("_ -> new")
	public static @NotNull SpecialText compose(final List<String> aStringList) {
		return new SpecialText(String_join(".", aStringList));
	}

	private final String text;

	public SpecialText(final String aText) {
		text = aText;
	}

	public String getText() {
		return text;
	}
}
