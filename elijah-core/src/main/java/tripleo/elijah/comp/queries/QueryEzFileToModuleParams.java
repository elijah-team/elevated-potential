package tripleo.elijah.comp.queries;

import lombok.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.specs.*;

import java.io.*;

public class QueryEzFileToModuleParams {

	public final  Compilation.PCon pcon;
	public final  InputStream inputStream;
	@Getter
	private final Compilation compilation;
	public final  String      sourceFilename;

	public QueryEzFileToModuleParams(EzSpec spec, final Compilation aCompilation) {
		sourceFilename = spec.file_name();
		inputStream    = spec.s().get();
		compilation    = aCompilation;
		pcon           = new Compilation.PCon();
	}
}
