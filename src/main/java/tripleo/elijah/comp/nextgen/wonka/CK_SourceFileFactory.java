package tripleo.elijah.comp.nextgen.wonka;

import org.jetbrains.annotations.NotNull;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.comp.graph.i.CK_SourceFile;
import tripleo.elijah.comp.nextgen.i.CP_Path;

// TODO 24/01/04 CM_Factory??
@SuppressWarnings("rawtypes")
public enum CK_SourceFileFactory { ;
	public static CK_SourceFile get(final tripleo.wrap.File aFile, final K aK) {
		switch (aK) {
		case SpecifiedEzFile -> {return new CK_SourceFile__SpecifiedEzFile(aFile);}
		case SpecifiedElijahFile -> {return new CK_SourceFile__SpecifiedElijahFile(aFile);}
		default -> throw new IllegalStateException("Unexpected value: " + aK);
		}
	}

	public static CK_SourceFile get(final tripleo.wrap.@NotNull File directory, final String file_name, final K aK) {
		return switch (aK) {
		case ElaboratedEzFile -> new CK_SourceFile__ElaboratedEzFile(directory, file_name);
		case ElaboratedElijahFile ->  new CK_SourceFile__ElaboratedElijahFile(directory, file_name);
		default -> null;
		};
	}

	public static <T> CK_SourceFile<T> get(final CP_Path aPath, final K aK) {
		switch (aK) {
		case SpecifiedPathEzFile -> {return new CK_SourceFile__SpecifiedEzFile(aPath.toFile());}
		case SpecifiedPathElijahFile -> {assert false;}//return new CK_SourceFile__SpecifiedElijahFile(aPath.toFile());}
		default -> throw new IllegalStateException("Unexpected value: " + aK);
		}
		return null;
	}

	public enum K {
		SpecifiedEzFile,
		ElaboratedEzFile,
		ElaboratedElijahFile,
		SpecifiedElijahFile,
		SpecifiedPathEzFile,
		SpecifiedPathElijahFile
		;
	}

	@SuppressWarnings("unchecked")
	public static CK_SourceFile<CompilerInstructions> get(java.io.File f, K specifiedezfile) {
		// TODO Auto-generated method stub
		return get(tripleo.wrap.File.wrap(f), f.toString(), specifiedezfile);
	}
}
