package tripleo.elijah_elevated_durable.graph_impl;

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

	public static CK_SourceFile get(final @NotNull tripleo.wrap.File directory, final String file_name, final K aK) {
		return switch (aK) {
		case SpecifiedEzFile -> new CK_SourceFile__SpecifiedEzFile(new tripleo.wrap.File(directory, file_name));
		case ElaboratedEzFile -> new CK_SourceFile__ElaboratedEzFile(directory, file_name);
		case ElaboratedElijahFile ->  new CK_SourceFile__ElaboratedElijahFile(directory, file_name);
		default -> null;
		};
	}

	public static CK_SourceFile<CompilerInstructions> get(final CP_Path aPath, final K aK) {
		switch (aK) {
		case SpecifiedPathEzFile -> {return new CK_SourceFile__SpecifiedEzFile(aPath.toFile());}
		case SpecifiedPathElijahFile -> {return new CK_SourceFile__SpecifiedElijahFile(aPath.toFile());}
		default -> throw new IllegalStateException("Unexpected value: " + aK);
		}
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
		assert specifiedezfile == K.SpecifiedEzFile;
		// TODO Auto-generated method stub
		return get(tripleo.wrap.File.wrap(f), f.toString(), specifiedezfile);
	}
}
