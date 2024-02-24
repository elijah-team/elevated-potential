package tripleo.elijah.ci_impl;

import com.google.common.collect.Collections2;
import org.jetbrains.annotations.NotNull;
//import tripleo.elijah.comp.i.CompilerInput;
import tripleo.elijah.comp.inputs.CompilerInput;
import tripleo.elijah.lang.i.IExpression;
import tripleo.elijah.lang.i.StringExpression;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.*;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.xlang.LocatableString;
import tripleo.wrap.File;

import java.util.*;

/**
 * Created 9/6/20 11:20 AM
 */
public class CompilerInstructionsImpl implements CompilerInstructions {
	public @NotNull List<LibraryStatementPart> lsps = new ArrayList<>();
	private         CiIndexingStatement        _idx;
	private         CM_Filename                filename;
	private         GenerateStatement          gen;
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
	public Optional<String> genLang() {
		var s = genLang22();
		if (s==null)
		return Optional.empty();
		else return Optional.of(s);
	}

/*
	@Override
	public Optional<String> genLang() {
		Collection<GenerateStatementImpl.Directive> gens = Collections2.filter(((GenerateStatementImpl) gen).dirs, (GenerateStatementImpl.Directive directive) -> {
            return directive.sameName("gen");
        });
		Iterator<GenerateStatementImpl.Directive> gi = gens.iterator();
		if (!gi.hasNext()) return null;
		CiExpression lang_raw = gi.next().getExpression();
		assert lang_raw instanceof CiStringExpression;
		String s = Helpers.remove_single_quotes_from_string(((CiStringExpression) lang_raw).getText());
		return Optional.of(s);
	}
*/

//	@Override
	public String genLang22() {
		final var genLang0 = gen.dirStream()
				.filter(input -> input.sameName("gen"))
				.findAny() // README if you need more than one, comment this out
				.stream().map((gin0) -> {
					final GenerateStatementImpl.Directive gin      = (GenerateStatementImpl.Directive) gin0;
					final IExpression                     lang_raw = gin.expression();

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
	public CM_Filename getFilename() {
		return filename;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocatableString getLocatableName() {
		throw new UnintendedUseException("unsure");
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
		throw new UnintendedUseException("copy paste");
	}

	@Override
	public List<LibraryStatementPart> _lsps() {
		return lsps;
	}

	@Override
	public void advise(CompilerInput aAdvisement) {

	}

//	@Override
//	public void advise(final CompilerInput aAdvisement) {
//		throw new UnintendedUseException("copy paste");
//	}

	@Override
	public File makeFile() {
		return new tripleo.wrap.File(getInp());
	}

	public String getInp() {
		return null;
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
				", filename='" + filename + '\'' +
				'}';
	}
}
