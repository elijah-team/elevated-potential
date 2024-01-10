/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.ci_impl;

import antlr.Token;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.GenerateStatement;
import tripleo.elijah.g.GDirective;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.xlang.LocatableString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Created 9/6/20 12:04 PM
 */
public class GenerateStatementImpl implements GenerateStatement {
	public final List<Directive> dirs = new ArrayList<>();

	// FIXME 24/01/10 TokenExtensions.addDirectve[To](...)
	@Override
	public void addDirective(final @NotNull Token token, final IExpression expression) {
		dirs.add(new Directive(LocatableString.of(token), expression));
	}

	@Override
	public Stream<GDirective> dirStream() {
		return dirs.stream()
		           .map(d -> (GDirective) d);
	}

	@Getter
	public static class Directive implements GDirective {
		private final          IExpression     expression;
		private final @NotNull LocatableString name;

		public Directive(final @NotNull LocatableString token_, final IExpression expression_) {
			name       = token_;
			expression = expression_;
		}

//		public IExpression getExpression() {
//			return expression;
//		}
//
//		public String getName() {
//			return name;
//		}

		@Override
		public boolean sameName(String aName) {
			Preconditions.checkNotNull(aName);
			return Objects.equals(aName, this.name);
		}
	}
}
