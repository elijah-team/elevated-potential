package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CP_SubFile__ implements CP_SubFile {

	private final           _CP_RootPath rootPath;
	private final @Nullable CP_Path      parentPath;
	private final           String       file;

	private final @NotNull CP_Path _path;

	public CP_SubFile__(final _CP_RootPath aCPOutputPath, final String aFile) {
		rootPath   = aCPOutputPath;
		parentPath = null;
		file       = aFile;
		_path      = new CP_Path1(rootPath, file);
	}

	public CP_SubFile__(final CP_Path aParentPath, final String aFile) {
		if (aParentPath instanceof _CP_RootPath)
			rootPath = (_CP_RootPath) aParentPath;
		else
			rootPath = aParentPath.getRootPath();

		parentPath = aParentPath;
		file       = aFile;
		_path      = new CP_Path1(rootPath, file);
	}

	@Override
	public CP_Path getPath() {
		return _path;
	}

	@Override
	public @NotNull File toFile() {
		return new File(file);
	}

	public static class CP_Path1 implements CP_Path {
		public final @Nullable CP_Path        parent;
		public final @Nullable _CP_RootPath   op;
		public final           String         childName;
		private final          Eventual<Path> _pathPromise = new Eventual<>();
		String x;

		public CP_Path1(final _CP_RootPath aParent, final String aFile) {
			parent    = null; // new CP_Path1(aParent, aFile);
			op        = aParent;
			childName = aFile;
			op.getPathPromise().then(p -> _pathPromise.resolve(Path.of(p.toString(), childName)));
		}

		public CP_Path1(final CP_Path1 aParent, final String aChildName) {
			parent    = aParent;
			childName = aChildName;
			op        = null;
			parent.getPathPromise().then(p -> _pathPromise.resolve(Path.of(p.toString(), childName)));
		}

		@Override
		public @NotNull CP_Path child(final String aPath0) {
			x = aPath0;
			return new CP_Path1(this, aPath0);
		}

		@Override
		public String getName() {
			return childName;
		}

		@Override
		public @Nullable CP_Path getParent() {
			return parent;
		}

		@Override
		public @NotNull Path getPath() {
			final String s;
			if (parent != null)
				s = parent.toFile().toString();
			else {
				s = op.getPath().toString();
			}
			var s1 = s;
			return Path.of(s, childName);
		}

		@Override
		public @NotNull Eventual<Path> getPathPromise() {
			return _pathPromise;
		}

		@Override
		public @Nullable File getRootFile() {
			return null;
		}

		@Override
		public _CP_RootPath getRootPath() {
			return getParent().getRootPath();
		}

		private String getString(final @NotNull String parentName) {
			String result;
			if (true || x == null) {
				result = Path.of(parentName, childName).toFile().toString();
			} else {
				result = Path.of(parentName, childName, x).toFile().toString();
			}
			return result;
		}

		@Override
		public @NotNull CP_SubFile subFile(final String aFile) {
			x = aFile;
			return new CP_SubFile__((_CP_RootPath) null /* this */, aFile);
		}

		@Override
		public @NotNull File toFile() {
			if (op != null) {
				return new File(op.toFile(), childName);
			}

			return new File(parent.toFile(), childName);
		}

		@Override
		public String toString() {
			String        result;
			CP_Path       p  = parent;
			List<CP_Path> ps = new ArrayList<>();

			while (p != null) {
				ps.add(p);
				p = p.getParent();
			}

			// for (CP_Path path : ps) {
			// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("122 " + path.getName());
			// }

			// if (ps.size() == 0) {
			// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("122xy " + op.getPath());
			// } else {
			// tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("122x " + ps);
			// }

			if (parent == null) {
				final String root_path = op.getPath().toFile().toString();
				result = getString(root_path);
			} else {
				final String parentName = parent.getName();
				result = getString(parentName);
			}
			// return result;
			return toFile().toString();
		}
	}
}