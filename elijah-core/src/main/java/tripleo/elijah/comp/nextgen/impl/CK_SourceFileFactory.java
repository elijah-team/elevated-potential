package tripleo.elijah.comp.nextgen.impl;

import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;

public class CK_SourceFileFactory {
	public static CK_SourceFile get(SourceFileParserParams p, CompilationRunner cr) {
		return new CK_SourceFile__SourceFileParserParams(p, cr);
	}

}
