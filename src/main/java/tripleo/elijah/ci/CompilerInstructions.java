package tripleo.elijah.ci;

import tripleo.elijah.comp.i.CompilerInput;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.xlang.LocatableString;
import tripleo.wrap.File;

import java.util.List;
import java.util.Optional;

public interface CompilerInstructions {
	void add(GenerateStatement generateStatement);

	void add(LibraryStatementPart libraryStatementPart);

	List<LibraryStatementPart> getLibraryStatementParts();

	Optional<String> genLang();  // not a promise? Calculated? C<O<S>>>??

	CM_Filename getFilename();

	void setFilename(CM_Filename filename);

	String getName();

	void setName(LocatableString name);

	LocatableString getLocatableName();

	CiIndexingStatement indexingStatement();

	List<LibraryStatementPart> _lsps();

	void advise(CompilerInput aAdvisement);

	File makeFile();

	interface CompilerInstructionsBuilder {
		CompilerInstructions build();

		void add(GenerateStatement generateStatement);

		void add(LibraryStatementPart libraryStatementPart);

		void setGenLang(String aGenLangString);  // ??

		void setFilename(CM_Filename filename);

		CiIndexingStatement createIndexingStatement();

		void setName(LocatableString name);
	}
}
