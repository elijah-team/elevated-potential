package tripleo.elijah.comp.nextgen;

public class CX_realParseEzFile2 {

	//@NotNull
	//public static Operation<CompilerInstructions> realParseEzFile(final @NotNull Compilation c,
	//                                                              final @NotNull EzSpec ezSpec,
	//                                                              final @NotNull EzCache ezCache) {
	//
	//	final CM_Ez cm = c.megaGrande(ezSpec);
	//
	//
	//	var file = ezSpec.file();
	//	assert ezSpec.sis() != null;
	//	var s    = ezSpec.sis().get();
	//
	//	final String absolutePath;
	//	try {
	//		absolutePath = file.getCanonicalFile().toString(); // TODO 04/10 hash this and "attach"
	//		// queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ??
	//	} catch (IOException aE) {
	//		return Operation.failure(aE);
	//	}
	//
	//	var opt_ci = ezCache.get(absolutePath);
	//	if (opt_ci.isPresent()) {
	//		CompilerInstructions rootCI = opt_ci.get();
	//
	//		// TODO 04/10
	//		// /*EzFileIdentity??*/(MAP/*??*/, resolver is try stmt)
	//		// ...queryDB.attach(compilerInput, new EzFileIdentity_Sha256($hash)); // ??
	//		c.getObjectTree().asseverate(rootCI, Asseverate.CI_CACHED);
	//
	//		return Operation.success(rootCI);
	//	}
	//
	//	try {
	//		final Operation<CompilerInstructions> cio = CX_ParseEzFile.parseEzFile(new File(ezSpec.file_name()), c);
	//
	//		switch (cio.mode()) {
	//		case FAILURE -> {
	//			final Exception e = cio.failure();
	//			assert e != null;
	//			tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_2(("parser exception: " + e));
	//			e.printStackTrace(System.err);
	//			return cio;
	//		}
	//		case SUCCESS -> {
	//			final CompilerInstructions R = cio.success();
	//			R.setFilename(file.toString());
	//			ezCache.put(ezSpec, absolutePath, R);
	//			c.getObjectTree().asseverate(Triple.of(ezSpec, cio, R), Asseverate.CI_SPECCED);
	//			c.getObjectTree().asseverate(R, Asseverate.CI_PARSED);
	//			return cio;
	//		}
	//		default -> throw new IllegalStateException("Unexpected value: " + cio.mode());
	//		}
	//	} finally {
	//		if (s != null) {
	//			try {
	//				s.close();
	//			} catch (IOException aE) {
	//				// TODO return inside finally: is this ok??
	//				return Operation.failure(aE);
	//			}
	//		}
	//	}
	//}
}
