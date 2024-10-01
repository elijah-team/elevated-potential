package tripleo.elijah.ci_impl;

import com.google.common.collect.Collections2;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.ci.*;
import tripleo.elijah.ci.cii.StringExpression;
import tripleo.elijah.ci.cil.Helpers;
import tripleo.elijah.comp.CompilerInput;
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
		Collection<GenerateStatementImpl.Directive> gens = Collections2.filter(((GenerateStatementImpl) gen).dirs, (GenerateStatementImpl.Directive directive) -> {
            return directive.sameName("gen");
        });
		Iterator<GenerateStatementImpl.Directive> gi = gens.iterator();
		if (!gi.hasNext()) return null;
		CiExpression lang_raw = gi.next().getExpression();
		assert lang_raw instanceof StringExpression;
		String s = Helpers.remove_single_quotes_from_string(((StringExpression) lang_raw).getText());
		return Optional.of(s);
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
		this.name = name.asLocatableString();
	}

	@Override
	public List<LibraryStatementPart> _lsps() {
		return lsps;
	}

	@Override
	public void advise(final CompilerInput aAdvisement) {
		//throw new UnintendedUseException("copy paste");
		System.err.println("Ignoring CompilerInstructions::advise for " + aAdvisement.getInp());
	}

	@Override
	public File makeFile() {
		final String inp = getInp();
		return new tripleo.wrap.File(inp);
	}

	public String getInp() {
		return this.name;
	}

	@Override
	public String toString() {
		return "CompilerInstructionsImpl{" +
				"name='" + name + '\'' +
				", filename='" + filename + '\'' +
				'}';
	}
}
