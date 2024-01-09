package tripleo.elijah.util;

import org.apache.commons.codec.digest.*;
import org.jetbrains.annotations.*;

import java.io.*;
import tripleo.wrap.File;

public class CA_getHashForFile {
	public @NotNull Operation<String> apply(String s, File f) {
		try {
			final String hdigest = new DigestUtils(MessageDigestAlgorithms.SHA_256).digestAsHex(f.wrapped());

			if (hdigest != null) {
				return Operation.success(hdigest);
			} else {
				return Operation.failure(new Exception("apache digest returns null"));
			}
		} catch (IOException aE) {
			return Operation.failure(aE);
		}
	}
}
