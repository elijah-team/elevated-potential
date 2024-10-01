package tripleo.elijah_elevateder.comp.functionality.f203;

import org.apache.commons.codec.digest.*;
import org.jetbrains.annotations.*;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevated_durable.comp.EDL_IO;
import tripleo.elijah_fluffy.util.SimplePrintLoggerToRemoveSoon;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.*;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.*;

public class ChooseHashDirectoryNameBehavior implements ChooseDirectoryNameBehavior {
	private final Compilation   c;
	private final LocalDateTime localDateTime;

	@Contract(pure = true)
	public ChooseHashDirectoryNameBehavior(final Compilation aC, final LocalDateTime aLocalDateTime) {
		c = aC;
		localDateTime = aLocalDateTime;
	}

	private @NotNull File choose_dir_name() {
		final List<EDL_IO._IO_ReadFile> recordedreads = c.getIO().recordedreads_io();
		final List<String> recordedread_filenames = recordedreads.stream()
				.map((EDL_IO._IO_ReadFile t) -> t.getFile().toString()).collect(Collectors.toList());

		final DigestUtils digestUtils = new DigestUtils(SHA_256);

		final StringBuilder sb1 = new StringBuilder();

		recordedread_filenames.stream().sorted().map(digestUtils::digestAsHex).forEach(sha256 -> {
			sb1.append(sha256);
			sb1.append('\n');
		});

		final String c_name = digestUtils.digestAsHex(sb1.toString());

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");

		final String date = formatter.format(localDateTime); // 15-02-2022 12:43

		final File fn00 = new File("COMP", c_name);
		final File fn0 = new File(fn00, date);
		SimplePrintLoggerToRemoveSoon.println_err_4("mkdirs 71 " + fn0);
		fn0.mkdirs();

		final String fn1 = new File(fn0, "inputs.txt").toString();
		return fn0;
	}

	@Override
	public tripleo.wrap.@NotNull File chooseDirectory() {
		return tripleo.wrap.File.wrap(choose_dir_name());
	}

}
