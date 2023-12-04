package tripleo.elijah.comp.functionality.f203;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO_;
import tripleo.elijah.comp.nextgen.CP_OutputPath;
import tripleo.elijah.comp.nextgen.CP_Path;
import tripleo.elijah.comp.nextgen.CP_SubFile__;
import tripleo.wrap.File;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

public class ChooseHashDirectoryNameBehaviorPaths implements ChooseDirectoryNameBehavior {
	private final Compilation  c;
	private final LocalDateTime localDateTime;
	public        CP_Path       p;

	@Contract(pure = true)
	public ChooseHashDirectoryNameBehaviorPaths(final Compilation aC, final LocalDateTime aLocalDateTime) {
		c             = aC;
		localDateTime = aLocalDateTime;
	}

	@NotNull
	public File choose_dir_name() {
		final List<IO_._IO_ReadFile> recordedreads = c.getIO().recordedreads_io();

		final DigestUtils   digestUtils = new DigestUtils(SHA_256);
		final StringBuilder sb1         = new StringBuilder();

		// TODO similarity elsewhere
		recordedreads.stream()
				.map(IO_._IO_ReadFile::getFileName)
				.sorted()
				.map(digestUtils::digestAsHex)
				.forEach(sha256 -> {
					sb1.append(sha256);
					sb1.append('\n');
				});

		final String            c_name    = digestUtils.digestAsHex(sb1.toString());
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
		final String            date      = formatter.format(localDateTime); // 15-02-2022 12:43
		final File              fn00      = new File("COMP", c_name);
		final File              fn0       = new File(fn00, date);

		p = c.paths().outputRoot().child(c_name).child(date);

		Path px = Path.of(c.paths().outputRoot().getRootFile().toString(), c_name, date);

		// ((DeferredObject<Path, ?, ?>) p.getPathPromise()).resolve(px);

		return tripleo.wrap.File.wrap(px.toFile());
	}

	public CP_Path choose_dir_name2() {
		choose_dir_name();
		return p;
	}

	@Override
	public @NotNull File chooseDirectory() {
		final File file = choose_dir_name();

		// c.paths().outputRoot().set(p);

		final Path[] px = new Path[1];
		p.getPathPromise().then(pp -> px[0] = pp);

		CP_SubFile__.CP_Path1 pp   = (CP_SubFile__.CP_Path1) p;
		File                  root = null;

		if (pp.op == null) {
			CP_SubFile__.CP_Path1 pp2 = (CP_SubFile__.CP_Path1) pp.parent;

			if (pp2.op == null) {
				assert false;
			} else {
				root = ((CP_OutputPath) pp2.op).getRootFile();
			}

			var y = new File(root, pp2.childName);
			var x = new File(y, pp.childName);

			return x;
		}

		return tripleo.wrap.File.wrap(px[0].toFile());
	}

	public CP_Path getPath() {
		return p;
	}
}
