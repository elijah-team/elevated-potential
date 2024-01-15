package tripleo.elijah.ci_impl;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CiIndexingStatement;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.ci.GenerateStatement;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.ci_impl.GenerateStatementImpl.Directive;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.StringExpression;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.xlang.LocatableString;
import tripleo.wrap.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created 9/6/20 11:20 AM
 */
public class CompilerInstructionsImpl implements CompilerInstructions {
	private CompilerInput advisedCompilerInput;

	@Override
	public String genLang() {
		final var genLang0 = gen.dirStream()
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
				});
		var genLang = genLang0
				.findFirst() // README here too
				.flatMap(x->x);

		// TODO 12/30 why not just return the Optional??
		return genLang.orElse(null);
	}


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

	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<>();
	private CiIndexingStatement _idx;
	private CM_Filename         filename;
	private GenerateStatement   gen;
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
	public List<LibraryStatementPart> getLibraryStatementParts() {
		return lsps;
	}

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
		this.name = name.getString();
//		throw new UnintendedUseException("??");
	}

	@Override
	public String toString() {
		return "CompilerInstructionsImpl{" +
				"name='" + name + '\'' +
				", filename='" + filename.getString() + '\'' +
				'}';
	}
}
