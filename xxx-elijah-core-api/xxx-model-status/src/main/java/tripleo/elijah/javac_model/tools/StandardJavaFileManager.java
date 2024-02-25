package tripleo.elijah.javac_model.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public interface StandardJavaFileManager extends JavaFileManager {

	@Override
	boolean isSameFile(FileObject a, FileObject b);

	@Deprecated(since = "13")
	default Iterable<? extends JavaFileObject> getJavaFileObjectsFromPaths(
			Iterable<? extends Path> paths) {
		return getJavaFileObjectsFromPaths(asCollection(paths));
	}

	default Iterable<? extends JavaFileObject> getJavaFileObjectsFromPaths(
			Collection<? extends Path> paths) {
		return getJavaFileObjectsFromFiles(asFiles(paths));
	}

	private static <T> Collection<T> asCollection(Iterable<T> iterable) {
		if (iterable instanceof Collection) {
			return (Collection<T>) iterable;
		}
		List<T> result = new ArrayList<>();
		for (T item : iterable) result.add(item);
		return result;
	}

	Iterable<? extends JavaFileObject> getJavaFileObjectsFromFiles(
			Iterable<? extends File> files);

	private static Iterable<File> asFiles(final Iterable<? extends Path> paths) {
		return () -> new Iterator<>() {
			final Iterator<? extends Path> iter = paths.iterator();

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public File next() {
				Path p = iter.next();
				try {
					return p.toFile();
				} catch (UnsupportedOperationException e) {
					throw new IllegalArgumentException(p.toString(), e);
				}
			}
		};
	}

	Iterable<? extends JavaFileObject> getJavaFileObjects(File... files);

	default Iterable<? extends JavaFileObject> getJavaFileObjects(Path... paths) {
		return getJavaFileObjectsFromPaths(Arrays.asList(paths));
	}

	Iterable<? extends JavaFileObject> getJavaFileObjectsFromStrings(
			Iterable<String> names);

	Iterable<? extends JavaFileObject> getJavaFileObjects(String... names);

	default void setLocationFromPaths(Location location, Collection<? extends Path> paths)
	throws IOException {
		setLocation(location, asFiles(paths));
	}

	void setLocation(Location location, Iterable<? extends File> files)
	throws IOException;

	default void setLocationForModule(Location location, String moduleName,
									  Collection<? extends Path> paths) throws IOException {
		throw new UnsupportedOperationException();
	}

	default Iterable<? extends Path> getLocationAsPaths(Location location) {
		return asPaths(getLocation(location));
	}

	private static Iterable<Path> asPaths(final Iterable<? extends File> files) {
		return () -> new Iterator<>() {
			final Iterator<? extends File> iter = files.iterator();

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public Path next() {
				return iter.next().toPath();
			}
		};
	}

	Iterable<? extends File> getLocation(Location location);

	default Path asPath(FileObject file) {
		throw new UnsupportedOperationException();
	}

	default void setPathFactory(PathFactory f) {
	}

	interface PathFactory {
		Path getPath(String first, String... more);
	}
}
