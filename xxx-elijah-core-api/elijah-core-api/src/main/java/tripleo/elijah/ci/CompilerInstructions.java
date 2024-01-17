package tripleo.elijah.ci;

import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.xlang.LocatableString;

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

	LocatableString getLocatableName();

	CiIndexingStatement indexingStatement();

	void setName(LocatableString name);

	List<LibraryStatementPart> _lsps();

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
