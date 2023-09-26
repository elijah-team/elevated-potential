package tripleo.elijah.comp.nextgen.impl;

import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;

import java.io.*;

public class CK_SourceFileFactory {
	public static CK_SourceFile get(final File aFile, final K aK) {
		return new CK_SourceFile__SpecifiedEzFile(aFile);
	}

	public static CK_SourceFile get(final File directory, final String file_name, final K aK) {
		assert aK == K.ElaboratedEzFile;
		return new CK_SourceFile__ElaboratedEzFile(directory, file_name);
	}

	public enum K {
		SpecifiedEzFile,
		ElaboratedEzFile;
	}
}
