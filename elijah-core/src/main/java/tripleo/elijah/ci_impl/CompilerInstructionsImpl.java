/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah.ci_impl;

import antlr.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;

import java.util.*;

/**
 * Created 9/6/20 11:20 AM
 */
public class CompilerInstructionsImpl implements CompilerInstructions {
	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<>();
	private         CiIndexingStatement        _idx;
	private         String                     filename;
	private         GenerateStatement          gen;
	@Getter
	private         String                     name;

	@Override
	public void add(final GenerateStatement generateStatement) {
		assert gen == null;
		gen = generateStatement;
	}

	@Override
	public void add(final @NotNull LibraryStatementPart libraryStatementPart) {
		libraryStatementPart.setInstructions(this);
		lsps.add(libraryStatementPart);
	}

	@Override
	public @Nullable String genLang() {
		@SuppressWarnings("UnnecessaryLocalVariable") final Optional<String> genLang = ((GenerateStatementImpl) gen).dirs.stream()
				.filter(input -> input.getName().equals("gen"))
				.findAny() // README if you need more than one, comment this out
				.stream().map((GenerateStatementImpl.Directive gin) -> {
					final IExpression lang_raw = gin.getExpression();
					if (lang_raw instanceof final StringExpression langRaw) {
						final String s = Helpers.remove_single_quotes_from_string(langRaw.getText());
						return Optional.of(s);
					} else {
						return Optional.<String>empty();
					}
				})
				.findFirst() // README here too
				.orElse(null);
		return genLang.get();
	}

	@Override
	public String getFilename() {
		return filename;
	}

	@Override
	public void setFilename(final String filename) {
		this.filename = filename;
	}

	@Override
	public Iterable<? extends LibraryStatementPart> getLibraryStatementParts() {
		return lsps;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setName(@NotNull Token name) {
		this.name = name.getText();
	}

	@Override
	public @NotNull CiIndexingStatement indexingStatement() {
		if (_idx == null)
			_idx = new CiIndexingStatementImpl(this);

		return _idx;
	}

	@Override
	public List<LibraryStatementPart> lsps() {
		return lsps;
	}

	@Override
	public String toString() {
		return "CompilerInstructionsImpl{name='%s', filename='%s'}".formatted(name, filename);
	}
}

//
//
//
