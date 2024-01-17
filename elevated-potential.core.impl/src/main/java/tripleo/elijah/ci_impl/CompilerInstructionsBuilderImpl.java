package tripleo.elijah.ci_impl;

import tripleo.elijah.ci.*;
import tripleo.elijah.compiler_model.*;
import tripleo.elijah.util.*;
import tripleo.elijah.xlang.*;

public class CompilerInstructionsBuilderImpl implements CompilerInstructions.CompilerInstructionsBuilder {

	private CompilerInstructionsImpl carrier = new CompilerInstructionsImpl();

	@Override
	public CompilerInstructions build() {
		return carrier;
	}

	@Override
	public void add(final GenerateStatement generateStatement) {
		carrier.add(generateStatement);
	}

	@Override
	public void add(final LibraryStatementPart libraryStatementPart) {
		carrier.add(libraryStatementPart);
	}

	@Override
	public void setGenLang(final String aGenLangString) {
//		carrier.s
		throw new ProgramMightBeWrongIfYouAreHere();
	}

	@Override
	public void setFilename(final CM_Filename filename) {
		carrier.setFilename(filename);
	}

	@Override
	public CiIndexingStatement createIndexingStatement() {
		return carrier.indexingStatement();
	}

	@Override
	public void setName(final LocatableString name) {
		carrier.setName(name);
	}
}
