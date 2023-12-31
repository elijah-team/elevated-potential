package tripleo.elijah.nextgen.outputstatement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tripleo Nova
 */
public interface EG_Statement {
	@Contract(value = "_, _ -> new", pure = true)
	static @NotNull EG_Statement of(@NotNull String aText, @NotNull EX_Explanation aExplanation) {
		// FIXME 11/29
		// ElIntrinsics.checkNotNull(aText);
		// ElIntrinsics.checkNotNull(aExplanation);

		return new EG_Statement() {
			@Override
			public EX_Explanation getExplanation() {
				return aExplanation;
			}

			@Override
			public String getText() {
				return aText;
			}
		};
	}

	EX_Explanation getExplanation();

	String getText();
}
