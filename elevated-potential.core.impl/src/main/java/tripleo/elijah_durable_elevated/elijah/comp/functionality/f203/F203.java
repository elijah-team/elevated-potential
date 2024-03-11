package tripleo.elijah_durable_elevated.elijah.comp.functionality.f203;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.i.ErrSink;
import tripleo.elijah_durable_elevated.elijah.comp.Compilation;
import tripleo.wrap.File;

import java.time.LocalDateTime;

public class F203 {
	public final @NotNull ChooseDirectoryNameBehavior cdn;
	private final ErrSink errSink;
	final LocalDateTime localDateTime = LocalDateTime.now();

	@Contract(pure = true)
	public F203(final ErrSink aErrSink, final Compilation c) {
		errSink = aErrSink;

		// cdn = new<c.cfg._ChooseDirectoryNameBehavior>();

//		cdn = new ChooseCompilationNameBehavior(c);
//		cdn = new ChooseHashDirectoryNameBehavior(c, localDateTime);
		cdn = new ChooseHashDirectoryNameBehaviorPaths(c, localDateTime);
	}

	public File chooseDirectory() {
		final File file = cdn.chooseDirectory();

		if (cdn instanceof final @NotNull ChooseHashDirectoryNameBehaviorPaths paths) {
			var p = paths.getPath();

			p.getPathPromise().then(pp -> {
				final File file1 = File.wrap(pp.toFile());
				tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("mkdirs 71b " + file1);
				file1.mkdirs();
			});

			return p.toFile(); // FIXME file1;
		} else {
			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4("mkdirs 71 " + file);
			file.mkdirs();
			final String fn1 = new File(file, "inputs.txt").toString();
			return file;
		}
	}
}
