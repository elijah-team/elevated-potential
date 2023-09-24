/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler,copyright Tripleo<oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 */
package tripleo.elijah.ci;

import antlr.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface CompilerInstructions {
	void add(GenerateStatement generateStatement);

	void add(LibraryStatementPart libraryStatementPart);

	@Nullable
	String genLang();

	String getFilename();

	Iterable<? extends LibraryStatementPart> getLibraryStatementParts();

	String getName();

	CiIndexingStatement indexingStatement();

	List<LibraryStatementPart> lsps();

	void setFilename(String filename);

	void setName(String name);

	void setName(Token name);
}
