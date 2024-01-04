/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci_impl;

import antlr.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.GenerateStatementImpl.Directive;
import tripleo.elijah.comp.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;

import tripleo.wrap.File;

import java.util.*;

/**
 * Created 9/6/20 11:20 AM
 */
public class CompilerInstructionsImpl implements CompilerInstructions {
	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<>();
	private         CiIndexingStatement        _idx;
	private         String                     filename;
	private         GenerateStatement          gen;
	@Getter @Setter
	private         String                     name;
	private         CompilerInput              advised;

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
		@SuppressWarnings("UnnecessaryLocalVariable") final Optional<String> genLang = gen.dirStream()
				.filter(input -> input.sameName("gen"))
				.findAny() // README if you need more than one, comment this out
				.stream().map((gin0) -> {
					final Directive   gin      = (Directive) gin0;
					final IExpression lang_raw = gin.getExpression();

					if (lang_raw instanceof final StringExpression langRaw) {
						final String s = Helpers.remove_single_quotes_from_string(langRaw.getText());
						return Optional.of(s);
					} else {
						return Optional.<String>empty();
					}
				})
				.findFirst() // README here too
//				.orElse(null);
				.flatMap(x->x)
				;
//		if (genLang == null) return null;
		if (genLang.isPresent())
			return genLang.get();
		else return null;
// README 12/30 ewhynot just return the Optional??
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
	public void setName(final String name) {

	}

	@Override
	public void setName(final Token name) {

	}

	@Override
	public Iterable<? extends LibraryStatementPart> getLibraryStatementParts() {
		return lsps;
	}

	//@Override
	//public void setName(String name) {
	//	throw new UnintendedUseException(/*"24/01/03 what is this??"*/);
	//	this.name = name;
	//}

	//@Override
	//public void setName(@NotNull Token name) {
	//	this.name = name.getText();
	//}

	//@Override
	//public String getName() {
	//	// README "12/30" eclipse specific
	//	return name;
	//}

	@Override
	public void advise(final CompilerInput aCompilerInput) {
		advised = aCompilerInput;
	}

	@Override
	public File makeFile() {
		if (advised != null) {
			return new File(advised.makeFile(), getFilename());
		} else {
			// TODO 12/30 Shouldn't we always have something advised?
			return new File(getFilename());
		}
	}

	@Override
	public @NotNull CiIndexingStatement indexingStatement() {
		if (_idx == null)
			_idx = new CiIndexingStatementImpl(this);

		return _idx;
	}

	@Override
	public String toString() {
		return "CompilerInstructionsImpl{name='%s', filename='%s'}".formatted(name, filename);
	}
}

//
//
//
