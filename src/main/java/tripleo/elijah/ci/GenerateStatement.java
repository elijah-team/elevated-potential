/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 *Elijjah compiler,copyright Tripleo<oluoluolu+elijah@gmail.com>
 *
 *The contents of this library are released under the LGPL licence v3,
 *the GNU Lesser General Public License text was downloaded from
 *http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.xlang.LocatableString;

import java.util.stream.Stream;

public interface GenerateStatement {
	default void addDirective(Token token, CiExpression expression) { addDirective(LocatableString.of(token), expression); }
	void addDirective(LocatableString token, CiExpression expression);

	Stream<Directive> dirStream();

	public record Directive(@NotNull LocatableString name, CiExpression expression) {
		public boolean sameName(String aName) {
			return this.name.sameString(aName);
		}

//		public CiExpression getExpression() {
//			return this.expression;
//		}
	}
}
