/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_durable_elevated.elijah.lang.impl;

import antlr.Token;
import com.google.common.collect.Collections2;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.Helpers;

import java.util.*;

/**
 * Created Mar 27, 2019 at 2:24:09 PM
 *
 * @author Tripleo(sb)
 */
public class QualidentImpl implements tripleo.elijah.lang.i.Qualident {
	private final List<IdentExpression> parts = new ArrayList<>();

	/**
	 * Look into creating a {@link DotExpression} from here
	 */
	@Override
	public void append(final @NotNull IdentExpression r1) {
		if (r1.getText().contains(".")) {
			throw new IllegalArgumentException("trying to create a Qualident with a dot from a user created Token");
		}
		parts.add(r1);
	}

	@Override
	public void appendDot(final Token d1) {
//		_syntax.appendDot(d1, parts.size());//parts.add(d1);
	}

	@Override
	@NotNull
	public String asSimpleString() {
		return Helpers.String_join(".", Collections2.transform(parts, input -> input != null ? input.getText() : ""));
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
		if (this == o) {
			return true;
		}
		if (!(o instanceof final Qualident qualident)) {
			return false;
		}
		if (qualident.parts().size() != parts.size()) {
			return false;
		}
		for (int i = 0; i < parts.size(); i++) {
			final IdentExpression ppart = qualident.parts().get(i);
			final IdentExpression part = parts.get(i);
//			if (!equivalentTokens(ppart.token(), part.token()))
			if (!part.getText().equals(ppart.getText()))
				return false;
//			if (!qualident.parts.contains(token))
//				return false;
		}
//		if (Objects.equals(parts, qualident.parts))
		return true;// Objects.equals(_type, qualident._type);
	}

	public @NotNull List<FormalArgListItem> getArgs() {
		return null;
	}

	@Override
	public @NotNull ExpressionKind getKind() {
		return ExpressionKind.QIDENT;
	}

	@Override
	public @NotNull IExpression getLeft() {
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(parts/* , _type */);
	}

	@Override
	public boolean is_simple() {
		return true; // TODO is this true?
	}

	@Override
	public @NotNull List<IdentExpression> parts() {
		return parts;
	}

	@Override
	public String repr_() {
		return String.format("Qualident (%s)", this);
	}

	public void setArgs(final ExpressionList aExpressionList) {
		throw new UnintendedUseException();
	}

	@Override
	public void setKind(final ExpressionKind aExpressionKind) {
		throw new UnintendedUseException(); // TODO 10/15 eventually remove this
	}

	/**
	 * Not sure what this should do
	 */
	@Override
	public void setLeft(final IExpression iexpression) {
		throw new UnintendedUseException(); // TODO 10/15 eventually remove this
	}

	@Override
	public @NotNull String toString() {
		return asSimpleString();
	}

}

//
//
//
