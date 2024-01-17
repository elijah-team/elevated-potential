package tripleo.elijah.javac_model.tools;

import tripleo.elijah.javac_model.lang.model.SourceVersion;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

public interface Tool {
	default String name() {
		return "";
	}

	int run(InputStream in, OutputStream out, OutputStream err, String... arguments);

	Set<SourceVersion> getSourceVersions();

}
