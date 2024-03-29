package tripleo.elijah.ci.cil;

import antlr.Token;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CiExpressionList;
import tripleo.elijah.ci.ExpressionKind;
import tripleo.elijah.ci.cii.DotExpression;
import tripleo.elijah.ci.cii.IdentExpression;
import tripleo.elijah.ci.cii.Qualident;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created Mar 27, 2019 at 2:24:09 PM
 *
 * @author Tripleo(sb)
 */
public class QualidentImpl implements Qualident {
	private final List<IdentExpression> parts = new ArrayList<IdentExpression>();

	/**
	 * Look into creating a {@link DotExpression} from here
	 */
	@Override
	public void append(final IdentExpression r1) {
		if (r1.getText().contains("."))
			throw new IllegalArgumentException("trying to create a Qualident with a dot from a user created Token");
		parts.add(r1);
	}

	@Override
	public void appendDot(final Token d1) {
//		_syntax.appendDot(d1, parts.size());//parts.add(d1);
	}


	@Override
	@NotNull
	public String asSimpleString() {
		return Helpers.String_join(".", Collections2.transform(parts, new Function<IdentExpression, String>() {
			@Nullable
			@Override
			public String apply(@Nullable IdentExpression input) {
				assert input != null;
				return input.getText();
			}
		}));
//		final StringBuilder sb=new StringBuilder();
//		for (final Token part : parts) {
//			sb.append(part.getText());
//			sb.append('.');
//		}
//		final String s = sb.toString();
//		final String substring = s.substring(0, s.length() - 1);
//		return substring;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Qualident))
			return false;
		final Qualident qualident = (Qualident) o;
		if (qualident.parts().size() != parts.size())
			return false;
		for (int i = 0; i < parts.size(); i++) {
			final IdentExpression ppart = qualident.parts().get(i);
			final IdentExpression part  = parts.get(i);
//			if (!equivalentTokens(ppart.token(), part.token()))
			if (!part.getText().equals(ppart.getText()))
				return false;
//			if (!qualident.parts.contains(token))
//				return false;
		}
//		if (Objects.equals(parts, qualident.parts))
		return true;// Objects.equals(_type, qualident._type);
	}

	//public @NotNull List<FormalArgListItem> getArgs() {
	//	return null;
	//}

	public void setArgs(final CiExpressionList ael) {

	}

	@Override
	public ExpressionKind getKind() {
		return ExpressionKind.QIDENT;
	}

	@Override
	public CiExpression getLeft() {
		return this;
	}

	@Override
	public boolean is_simple() {
		return true; // TODO is this true?
	}

	@Override
	public List<IdentExpression> parts() {
		return parts;
	}

	@Override
	public String repr_() {
		return String.format("Qualident (%s)", toString());
	}

	/**
	 * Not sure what this should do
	 */
	@Override
	public void setLeft(final CiExpression iexpression) {
		throw new IllegalArgumentException(); // TODO is this right?
	}

	@Override
	public void setKind(final ExpressionKind aIncrement) {
		throw new IllegalArgumentException(); // TODO is this right?
	}

	@Override
	public int hashCode() {
		return Objects.hash(parts);
	}

	@Override
	public String toString() {
		return asSimpleString();
	}
}
