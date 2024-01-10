/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler,copyright Tripleo<oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci;

import tripleo.elijah.comp.CompilerInput;
import tripleo.elijah.compiler_model.CM_Filename;
import tripleo.elijah.xlang.LocatableString;
import tripleo.wrap.File;

import java.util.List;

public interface CompilerInstructions {
//	void add(GenerateStatement generateStatement);
//
//	void add(LibraryStatementPart libraryStatementPart);
//
//	@Nullable
//	String genLang();
//
//	String getFilename();
//
//	Iterable<? extends LibraryStatementPart> getLibraryStatementParts();
//
//	String getName();
//
//	CiIndexingStatement indexingStatement();
//
//	void setFilename(String filename);
//
//	void setName(String name);
//
//	void setName(Token name);
//
//	void advise(CompilerInput aCompilerInput);
//
//	File makeFile();

	File makeFile();

	/**
	 * @throws IllegalStateException if not advised
	 */
	CompilerInput profferCompilerInput() throws IllegalStateException;

	void add(GenerateStatement generateStatement);

	void add(LibraryStatementPart libraryStatementPart);

	List<LibraryStatementPart> getLibraryStatementParts();

	String genLang();  // not a promise? Calculated? C<O<S>>>??

	CM_Filename getFilename();

	void setFilename(CM_Filename filename);

	String getName();

	LocatableString getLocatableName();

	CiIndexingStatement indexingStatement();

	void setName(LocatableString name);

	void advise(CompilerInput aAdvisement);

	public interface CompilerInstructionsBuilder {
		CompilerInstructions build();

		void add(GenerateStatement generateStatement);

		void add(LibraryStatementPart libraryStatementPart);

		void setGenLang(String aGenLangString);  // ??

		void setFilename(CM_Filename filename);

		CiIndexingStatement createIndexingStatement();

		void setName(LocatableString name);
	}
}
