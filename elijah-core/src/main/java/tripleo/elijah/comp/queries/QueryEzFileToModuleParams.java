package tripleo.elijah.comp.queries;

import tripleo.elijah.comp.*;

import java.io.*;

public class QueryEzFileToModuleParams {

	public final Compilation.PCon pcon;
	public final InputStream inputStream;
	public final String sourceFilename;

	public QueryEzFileToModuleParams(final String aSourceFilename, final InputStream aInputStream,
			final Compilation aCompilation) {
		sourceFilename = aSourceFilename;
		inputStream = aInputStream;
		pcon = new Compilation.PCon();
	}
}
