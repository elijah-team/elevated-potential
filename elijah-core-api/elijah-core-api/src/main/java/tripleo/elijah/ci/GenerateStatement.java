/*
 *   -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 *Elijjah compiler,copyright Tripleo<oluoluolu+elijah@gmail.com>
 *
 *The contents of this library are released under the LGPL licence v3,
 *the GNU Lesser General Public License text was downloaded from
 *http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci;

import antlr.Token;
import com.google.common.collect.Collections2;
import tripleo.elijah.ci_impl.GenerateStatementImpl;
import tripleo.elijah.g.GDirective;
import tripleo.elijah.lang.i.IExpression;

import java.util.stream.Stream;

public interface GenerateStatement {
	void addDirective(Token token, IExpression expression);

	Stream<GDirective> dirStream();
}
