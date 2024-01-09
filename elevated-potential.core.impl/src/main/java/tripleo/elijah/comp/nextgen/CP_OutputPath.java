package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.Eventual;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.io.DisposableCharSink;
import tripleo.wrap.File;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CP_OutputPath implements CP_Path, _CP_RootPath, CPX_CalculateFinishParse {
	private final Eventual<Path> _pathPromise = new Eventual<>();

	private final CY_HashDeferredAction hda;
	private final Compilation           c;
	private       File                  root; // COMP/...
	private       boolean               _testShim;

	public CP_OutputPath(final Compilation cc) {
		c   = cc;
		hda = new CY_HashDeferredAction(c.getIO());
		
		c.signals()
			.subscribeCalculateFinishParse(this);
	}

	// TODO 12/28 latch or Uni? or promise/Eventual: nodes (??)
	public void _renderNodes(final @NotNull List<ER_Node> nodes) {
		signalCalculateFinishParse();
		nodes.stream()
				.map(this::renderNode);
	}

	@Override
	public CP_Path child(final String aSubPath) {
		return new CP_SubFile__(this, aSubPath).getPath();
	}

	public static void append_sha_string_then_newline(StringBuilder sb1, String sha256) {
		sb1.append(sha256);
		sb1.append('\n');
	}

	@Override
	public @Nullable String getName() {
		return null;
	}

	@Override
	public @Nullable CP_Path getParent() {
		return null;
	}

	@Override
	public @NotNull Path getPath() {
		// assert (root != null);
		if (root == null)
			return Path.of("zero");
		return root.wrapped().toPath();
	}

	@Override
	public @NotNull Eventual<Path> getPathPromise() {
		return _pathPromise;
	}

//	public File getRoot() {
//		return root;
//	}

	@Override
	public @NotNull File getRootFile() {
		return new File("COMP");
	}

	@Override
	public @NotNull _CP_RootPath getRootPath() {
		return this;
	}

	private void logProgress(final int code, final String message) {
		if (code == 117117)
			return;
		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_err_4(String.format("%d %s", code, message));
	}

	public @NotNull Operation<Boolean> renderNode(final @NotNull ER_Node node) {
		final Path         path = node.getPath().getPath(); // TODO 12/07
		final EG_Statement seq  = node.getStatement();

		c.getCompilationEnclosure().logProgress(CompProgress.__CP_OutputPath_renderNode, node);

		tripleo.elijah.util.SimplePrintLoggerToRemoveSoon.println_out_4("401b Writing path: " + path.toFile());
		path.getParent().toFile().mkdirs();

		try (final DisposableCharSink xx = c.getIO().openWrite(path)) {
			xx.accept(seq.getText());

			return Operation.success(true);
		} catch (Exception aE) {
			return Operation.failure(aE);
		}
	}

	public void signalCalculateFinishParse() {
		c.world()._completeModules();

		if (_pathPromise.isPending()) {
			final Eventual<String> promise = hda.promise();

			promise.then(calc -> {
				__PathPromiseCalculator ppc = new __PathPromiseCalculator();
				ppc.calc(calc);
				CP_Path p = ppc.getP(this);

				final String root = c.paths().outputRoot().getRootFile().toString();
				final String one  = ppc.c_name();
				final String two  = ppc.date();

				Path px = Path.of(root, one, _testShim ? "<date>" : two);
				logProgress(117117, "OutputPath = " + px);

				assert p.samePath(px); // FIXME "just return COMP" instead of zero

				_pathPromise.resolve(px);

				CP_Path pp = ppc.getP(this);
				assert pp.samePath(px); // FIXME "just return COMP" instead of zero

				this.root = tripleo.wrap.File.wrap(px.toFile());

				CP_Path p3 = ppc.getP(this);
				assert p3.samePath(px); // FIXME "just return COMP" instead of zero

			    final List<Object> objects = Helpers.List_of(px, p, pp, p3);
			    for (Object object : objects) {
				    logProgress(117133, "" + object);
		    	}
			});
		}
	}

	@Override
	public @NotNull CP_SubFile subFile(final String aFile) { // s ;)
		return new CP_SubFile__(this, aFile);
	}

	public void testShim() {
		_testShim = true;
	}

	@Override
	public @NotNull File toFile() {
		return tripleo.wrap.File.wrap(getPath().toFile());
	}

	private static class __PathPromiseCalculator {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");

		private String date;
		private String c_name;

		public String c_name() {
			return c_name;
		}

		public void calc(String c_name) {
			final LocalDateTime localDateTime = LocalDateTime.now();
			final String        date          = formatter.format(localDateTime); // 15-02-2022 12:43

			this.c_name = c_name;
			this.date   = date;
		}

		public String date() {
			return date;
		}

		public CP_Path getP(final @NotNull CP_OutputPath aCPOutputPath) {
			final CP_Path outputRoot = aCPOutputPath.c.paths().outputRoot();

			return outputRoot.child(c_name).child(date);
		}
	}

	@Override
	public Path toPath() {
		return getPath(); // eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
	}

	@Override
	public DisposableCharSink newIOWriter(final IO io) {
		try {
			return io.openWrite(getPath());
		} catch (IOException aE) {
			throw new RuntimeException(aE); // FIXME 12/07
		}
	}

	@Override
	public String asString() {
		return this.toString();
	}

	@Override
	public boolean samePath(Path px) {
		throw new UnsupportedOperationException("TODO 12/28 implement me");
	}

	@Override
	public void notify_CPX_CalculateFinishParse(Ok ok) {
		throw new UnintendedUseException("TODO 12/28 dpys, implement me");
	}
}
