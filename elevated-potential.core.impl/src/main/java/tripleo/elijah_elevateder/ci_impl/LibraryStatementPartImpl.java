/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.ci_impl;

import antlr.Token;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.ci.CiExpression;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.LibraryStatementPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 9/6/20 12:06 PM
 */
public class LibraryStatementPartImpl implements LibraryStatementPart {
	private CompilerInstructions ci;
	private String               dirName;
	private String               name;
	private @Nullable List<Directive> dirs = null;

	@Override
	public void addDirective(final Token token, final CiExpression iExpression) {
		if (dirs == null)
			dirs = new ArrayList<Directive>();
		dirs.add(new Directive(token, iExpression));
	}

	@Override
	public String getDirName() {
		return dirName;
	}

	@Override
	public void setDirName(final @NotNull Token dirName) {
		this.dirName = dirName.getText();
	}

	@Override
	public CompilerInstructions getInstructions() {
		return ci;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final @NotNull Token i1) {
		name = i1.getText();
	}

	@Override
	public void setInstructions(CompilerInstructions instructions) {
		ci = instructions;
	}

	public class Directive {

		private final CiExpression expression;
		private final String       name;

		public Directive(final @NotNull Token token_, final CiExpression expression_) {
			name       = token_.getText();
			expression = expression_;
		}
	}

}

//
//
//
