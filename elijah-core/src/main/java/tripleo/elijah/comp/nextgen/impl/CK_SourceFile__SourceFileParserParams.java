package tripleo.elijah.comp.nextgen.impl;

import tripleo.elijah.ci.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.graph.i.*;
import tripleo.elijah.comp.internal.*;
import tripleo.elijah.comp.queries.*;
import tripleo.elijah.nextgen.inputtree.*;
import tripleo.elijah.nextgen.outputtree.*;
import tripleo.elijah.util.*;

public class CK_SourceFile__SourceFileParserParams implements CK_SourceFile {

	private final SourceFileParserParams p;
	private final CompilationRunner      cr;
	private final QuerySourceFileParser  qsfp;

	public CK_SourceFile__SourceFileParserParams(SourceFileParserParams aP, CompilationRunner aCr) {
		p    = aP;
		cr   = aCr;
		qsfp = new QuerySourceFileParser(cr);
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
		//noinspection UnnecessaryLocalVariable
		Operation<CompilerInstructions> oci = qsfp.process(p);
		return oci;
	}
}
