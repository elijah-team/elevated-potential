/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: t; c-basic-offset: 4 -*- */
/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
package tripleo.elijah_elevateder.contexts;

import tripleo.elijah.contexts.ContextInfo;
import tripleo.elijah.lang.i.*;

/**
 * Created 11/28/21 11:44 PM
 */
public class ImportInfo implements ContextInfo {
	enum ImportType {
		DIRECT, MEMBER, PACKAGE
	}
	// something else (I think I got it)

	ImportStatement importStatement;
	Qualident importPart;
	int importPartIndex;

	ImportType importType;

	public ImportInfo(final ImportStatement aImportStatement, final Qualident aImportPart, final int aImportPartIndex,
			final ImportType aImportType) {
		importStatement = aImportStatement;
		importPart = aImportPart;
		importPartIndex = aImportPartIndex;
		importType = aImportType;
	}
}

//
// vim:set shiftwidth=4 softtabstop=0 noexpandtab:
//
