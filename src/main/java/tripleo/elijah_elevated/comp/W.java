package tripleo.elijah_elevated.comp;

import java.io.FileNotFoundException;
import java.io.InputStream;

import tripleo.elijah.comp.IO;
import tripleo.elijah.util.Operation;
import tripleo.wrap.File;

public class W {
	public static Operation<InputStream> ezSpec_SIS(File file, IO io) {
		try {
			return Operation.success(file.readFile(io));
		} catch (FileNotFoundException aE) {
			return Operation.failure(aE);
		}
	}
}
