package tripleo.elijah.comp.nextgen.impl;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.specs.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;

import java.io.*;

public class CK_SourceFile__SourceFileParserParams implements CK_SourceFile {

	private final SourceFileParserParams p;
	private final CompilationRunner      cr;

	public CK_SourceFile__SourceFileParserParams(SourceFileParserParams aP, CompilationRunner aCr) {
		p    = aP;
		cr   = aCr;
	}

	@Override
	public CompilerInput compilerInput() {
		return null;
	}

	@Override
	public EIT_Input input() {
		return null;
	}

	@Override
	public EOT_OutputFile output() {
		return null;
	}

	@Override
	public Operation<CompilerInstructions> process_query() {
		var ezSpec = new EzSpec(p.file_name(), 
				() -> {
					try {
						return cr.getCrState().ca().getCompilation().getIO().readFile(p.f());
					} catch (FileNotFoundException aE) {
						throw new RuntimeException(aE);
					}
				},
				p.f());
		return cr.realParseEzFile(ezSpec, cr.ezCache());
	}
}
