package tripleo.elijah.nextgen.outputstatement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ReasonedStringListStatement implements EG_Statement {
	private final static EX_Explanation default_explanation = EX_Explanation.withMessage("xyz");
	private final List<IReasonedString> rss = new ArrayList<>();
	private EX_Explanation explanation = default_explanation;

	public ReasonedStringListStatement() {
	}

	public ReasonedStringListStatement(final EX_Explanation aExplanation) {
		explanation = aExplanation;
	}

	@Override
	public EX_Explanation getExplanation() {
		return explanation;
	}

	@Override
	public String getText() {
		final StringBuilder result = new StringBuilder();
		for (IReasonedString reasonedString : rss) {
			result.append(reasonedString.text());
		}
		return result.toString();
	}

	public void append(final String aText, final String aReason) {
		rss.add(new ReasonedString(aText, aReason));
	}

	public void append(final IReasonedString aReasonedString) {
		rss.add(aReasonedString);
	}

	public void append(final Supplier<String> aText, final String aReason) {
		rss.add(new ReasonedSuppliedString(aText, aReason));
	}

	public void append(final EG_Statement aText, final String aReason) {
		rss.add(new ReasonedStatementString(aText, aReason));
	}
}
