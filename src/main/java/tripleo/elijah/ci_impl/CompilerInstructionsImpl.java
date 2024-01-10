/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci_impl;

import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci_impl.GenerateStatementImpl.Directive;
import tripleo.elijah.comp.*;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.util.*;

import tripleo.elijah.xlang.LocatableString;

import java.util.*;
import tripleo.wrap.File;

/**
 * Created 9/6/20 11:20 AM
 */
public class CompilerInstructionsImpl implements CompilerInstructions {
//	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<>();
//	private         CiIndexingStatement        _idx;
//	private         String                     filename;
//	private         GenerateStatement          gen;
//	@Getter @Setter
//	private         String        name;
	private         CompilerInput advisedCompilerInput;
//
//	@Override
//	public void add(final GenerateStatement generateStatement) {
//		assert gen == null;
//		gen = generateStatement;
//	}
//
//	@Override
//	public void add(final @NotNull LibraryStatementPart libraryStatementPart) {
//		libraryStatementPart.setInstructions(this);
//		lsps.add(libraryStatementPart);
//	}

	@Override
	public String genLang() {
		final Optional<String> genLang = gen.dirStream()
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

		// TODO 12/30 why not just return the Optional??
		return genLang.orElse(null);
	}

//	@Override
//	public String getFilename() {
//		return filename;
//	}
//
//	@Override
//	public void setFilename(final String filename) {
//		this.filename = filename;
//	}
//
//	@Override
//	public void setName(final String name) {
//
//	}
//
//	@Override
//	public void setName(final Token name) {
//
//	}
//
//	@Override
//	public Iterable<? extends LibraryStatementPart> getLibraryStatementParts() {
//		return lsps;
//	}
//
//	//@Override
//	//public void setName(String name) {
//	//	throw new UnintendedUseException(/*"24/01/03 what is this??"*/);
//	//	this.name = name;
//	//}
//
//	//@Override
//	//public void setName(@NotNull Token name) {
//	//	this.name = name.getText();
//	//}
//
//	//@Override
//	//public String getName() {
//	//	// README "12/30" eclipse specific
//	//	return name;
//	//}

	@Override
	public void advise(final CompilerInput aCompilerInput) {
		advisedCompilerInput = aCompilerInput;
	}

	@Override
	public File makeFile() {
		if (advisedCompilerInput != null) {
			return File.wrap(new java.io.File(advisedCompilerInput.makeFile().wrapped(), getFilename().getString()));
		} else {
			// TODO 12/30 Shouldn't we always have something advised?
			return new File(getFilename().getString());
		}
	}

	@Override
	public CompilerInput profferCompilerInput() throws IllegalStateException {
		if (this.advisedCompilerInput == null)
			throw new IllegalStateException("CompilerInstructions >> called before join");
		return this.advisedCompilerInput;
	}

//	@Override
//	public @NotNull CiIndexingStatement indexingStatement() {
//		if (_idx == null)
//			_idx = new CiIndexingStatementImpl(this);
//
//		return _idx;
//	}
//
//	@Override
//	public String toString() {
//		return "CompilerInstructionsImpl{name='%s', filename='%s'}".formatted(name, filename);
//	}
//
//	@Override
//	public String getName() {
//		// 24/01/04 back and forth
//		return this.name;
//	}

	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<>();
	private CiIndexingStatement _idx;
	private CM_Filename         filename;
	private GenerateStatement   gen;
	private         String                     name;

//	@Override
//	public CompilerInput profferCompilerInput() throws IllegalStateException {
//		return null;
//	}

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
	public List<LibraryStatementPart> getLibraryStatementParts() {
		return lsps;
	}

//	@Override
//	public Optional<String> genLang() {
//		Collection<GenerateStatementImpl.Directive> gens = Collections2.filter(((GenerateStatementImpl) gen).dirs, (GenerateStatementImpl.Directive directive) -> {
//			return directive.sameName("gen");
//		});
//		Iterator<GenerateStatementImpl.Directive> gi = gens.iterator();
//		if (!gi.hasNext()) return null;
//		IExpression lang_raw = gi.next().getExpression();
//		assert lang_raw instanceof StringExpression;
//		String s = Helpers.remove_single_quotes_from_string(((StringExpression) lang_raw).getText());
//		return Optional.of(s);
//	}

	@Override
	public CM_Filename getFilename() {
		return filename;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocatableString getLocatableName() {
		return null;
	}

	@Override
	public void setFilename(final CM_Filename filename) {
		this.filename = filename;
	}

	@Override
	public @NotNull CiIndexingStatement indexingStatement() {
		if (_idx == null)
			_idx = new CiIndexingStatementImpl(this);

		return _idx;
	}

	@Override
	public void setName(LocatableString name) {

	}

//	@Override
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	@Override
//	public void setName(@NotNull Token name) {
//		this.name = name.getText();
//	}

	@Override
	public String toString() {
		return "CompilerInstructionsImpl{" +
				"name='" + name + '\'' +
				", filename='" + filename.getString() + '\'' +
				'}';
	}

}

//
//
//
