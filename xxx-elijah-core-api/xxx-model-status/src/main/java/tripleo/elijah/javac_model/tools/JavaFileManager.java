package tripleo.elijah.javac_model.tools;

import java.io.*;
import java.util.*;

import static javax.tools.JavaFileObject.Kind;

public interface JavaFileManager extends Closeable, Flushable, OptionChecker {

	ClassLoader getClassLoader(Location location);

	Iterable<JavaFileObject> list(Location location,
								  String packageName,
								  Set<Kind> kinds,
								  boolean recurse)
	throws IOException;

	String inferBinaryName(Location location, JavaFileObject file);

	boolean isSameFile(FileObject a, FileObject b);

	boolean handleOption(String current, Iterator<String> remaining);

	boolean hasLocation(Location location);

	JavaFileObject getJavaFileForInput(Location location,
									   String className,
									   Kind kind)
	throws IOException;

	default JavaFileObject getJavaFileForOutputForOriginatingFiles(Location location,
																   String className,
																   Kind kind,
																   FileObject... originatingFiles)
	throws IOException {
		return getJavaFileForOutput(location, className, kind, siblingFrom(originatingFiles));
	}

	JavaFileObject getJavaFileForOutput(Location location,
										String className,
										Kind kind,
										FileObject sibling)
	throws IOException;

	private static FileObject siblingFrom(FileObject[] originatingFiles) {
		return originatingFiles != null && originatingFiles.length > 0 ? originatingFiles[0] : null;
	}

	FileObject getFileForInput(Location location,
							   String packageName,
							   String relativeName)
	throws IOException;

	default FileObject getFileForOutputForOriginatingFiles(Location location,
														   String packageName,
														   String relativeName,
														   FileObject... originatingFiles)
	throws IOException {
		return getFileForOutput(location, packageName, relativeName, siblingFrom(originatingFiles));
	}

	FileObject getFileForOutput(Location location,
								String packageName,
								String relativeName,
								FileObject sibling)
	throws IOException;

	@Override
	void flush() throws IOException;

	@Override
	void close() throws IOException;

	// TODO: describe failure modes
	default Location getLocationForModule(Location location, String moduleName) throws IOException {
		throw new UnsupportedOperationException();
	}

	default Location getLocationForModule(Location location, JavaFileObject fo) throws IOException {
		throw new UnsupportedOperationException();
	}

	// TODO: describe failure modes
	default <S> ServiceLoader<S> getServiceLoader(Location location, Class<S> service) throws IOException {
		throw new UnsupportedOperationException();
	}

	// TODO: describe failure modes
	default String inferModuleName(Location location) throws IOException {
		throw new UnsupportedOperationException();
	}

	// TODO: describe failure modes
	default Iterable<Set<Location>> listLocationsForModules(Location location) throws IOException {
		throw new UnsupportedOperationException();
	}

	default boolean contains(Location location, FileObject fo) throws IOException {
		throw new UnsupportedOperationException();
	}

	interface Location {
		boolean isOutputLocation();

		default boolean isModuleOrientedLocation() {
			return getName().matches("\\bMODULE\\b");
		}

		String getName();
	}

}
