package tripleo.elijah.comp.nextgen.impl;

import tripleo.elijah.comp.graph.i.*;

import java.io.*;

public class CK_SourceFileFactory {
	public static CK_SourceFile get(final File aFile, final K aK) {
		switch (aK) {
		case SpecifiedEzFile -> {return new CK_SourceFile__SpecifiedEzFile(aFile);}
		case SpecifiedElijahFile -> {return new CK_SourceFile__SpecifiedElijahFile(aFile);}
		default -> throw new IllegalStateException("Unexpected value: " + aK);
		}
	}

	public static CK_SourceFile get(final File directory, final String file_name, final K aK) {
		return switch (aK) {
		case ElaboratedEzFile -> new CK_SourceFile__ElaboratedEzFile(directory, file_name);
		case ElaboratedElijahFile ->  new CK_SourceFile__ElaboratedElijahFile(directory, file_name);
		default -> null;
		};
	}

	public enum K {
		SpecifiedEzFile,
		ElaboratedEzFile,
		ElaboratedElijahFile,
		SpecifiedElijahFile;
	}
}
