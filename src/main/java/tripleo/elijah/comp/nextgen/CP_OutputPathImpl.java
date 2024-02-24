package tripleo.elijah.comp.nextgen;

import com.google.common.base.MoreObjects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.i.CompProgress;
import tripleo.elijah.comp.local.CY_HashDeferredAction;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.i.CP_SubFile;
import tripleo.elijah.comp.nextgen.i._CP_RootPath;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.nextgen.outputstatement.EG_Statement;
import tripleo.elijah.util.Helpers;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.SimplePrintLoggerToRemoveSoon;
import tripleo.elijah.util.io.DisposableCharSink;
import tripleo.elijah.util2.Eventual;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah_elevated.gu.R;
import tripleo.wrap.File;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class CP_OutputPathImpl implements CP_OutputPath {
	private final Eventual<Path> _pathPromise = new Eventual<>();

	private final CY_HashDeferredAction hda;
	private final Compilation           c;
	private       File                  root; // COMP/...
	private       boolean               _testShim;

	public CP_OutputPathImpl(final Compilation cc) {
		c   = cc;
		hda = new CY_HashDeferredAction(c.getIO());

		c.signals()
		 .subscribeCalculateFinishParse(this);
	}

	@Override
	public String toString() {
		final String pathPromiseS;
		if (_pathPromise.isResolved()) {
			final Path[] x = new Path[1];
			_pathPromise.then(xx -> {
				x[0] = xx;
			});
			pathPromiseS = x[0].toString();
		} else {
			pathPromiseS = "<<UNRESOLVED>>";
		}

		final String           hdaS;
		final Optional<String> os = hda.getCompleted();
		if (os.isPresent()) {
			hdaS = os.get();
		} else {
			hdaS = "<<NO-HDA>>";
		}

		return MoreObjects.toStringHelper(this)
		                  .add("_pathPromise", pathPromiseS)
		                  .add("hda", hdaS)
		                  .add("root", root)
		                  .add("testing", _testShim)
		                  .toString();
	}

	@Override
	public void calculate_hda() {
		hda.calculate();
	}

	// TODO 12/28 latch or Uni? or promise/Eventual: nodes (??)
	@Override
	public void _renderNodes(final @NotNull List<ER_Node> nodes) {
		signalCalculateFinishParse();
		nodes.stream()
		     .forEach(this::renderNode);
	}

	@Override
	public CP_Path child(final String aSubPath) {
		return new CP_SubFileImpl(this, aSubPath).getPath();
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

	@Override
	public void logProgress(final int code, final String message) {
//		if (code == 117117)
//			return;
		SimplePrintLoggerToRemoveSoon.println_err_4(String.format("%d %s", code, message));
	}

	@Override
	public @NotNull Operation<Ok> renderNode(final @NotNull ER_Node node) {
		final Path         path = node.getPath().getPath(); // TODO 12/07
		final EG_Statement seq  = node.getStatement();

		c.getCompilationEnclosure().logProgress(CompProgress.__CP_OutputPath_renderNode, node);
		return renderNode(path, seq);
	}

	public @NotNull Operation<Ok> renderNode(final Path path, final EG_Statement seq) {
		SimplePrintLoggerToRemoveSoon.println_out_4("401b Writing path: " + path.toFile());
		path.getParent().toFile().mkdirs();

		try (final DisposableCharSink xx = c.getIO().openWrite(path)) {
			xx.accept(seq.getText());

			return Operation.success(Ok.instance());
		} catch (Exception aE) {
			return Operation.failure(aE);
		}
	}

	@Override
	public void signalCalculateFinishParse() {
		c.world()._completeModules();

		if (_pathPromise.isPending()) {
			final Eventual<String> promise = hda.promise();

			final CP_OutputPath outputPath = c.paths().outputRoot();

			promise.then(calc -> {
				final __PathPromiseCalculator ppc = new __PathPromiseCalculator();
				ppc.calc(calc);

				final String root   = outputPath.getRootFile().wrapped().toString();
				final String _hash  = ppc.c_name();
				final String _date0 = ppc.date();
				final String _date  = _testShim ? "<date>" : _date0;
				final Path   px     = Path.of(root, _hash, _date);
				R.asv(117117, "OutputPath = " + px, this);

				this._pathPromise.resolve(px);
				this.root = tripleo.wrap.File.wrap(px.toFile());

				final CP_Path      p       = ppc.getP(this);
				final List<Object> objects = Helpers.List_of(px, p);
				R.asv(117133, "" + objects, this);
//				for (final Object object : objects) {
//					R.asv(117133, "" + object, this);
//				}
				int y = 2;
			});
		}

		calculate_hda();
	}

	@Override
	public @NotNull CP_SubFile subFile(final String aFile) { // s ;)
		return new CP_SubFileImpl(this, aFile);
	}

	@Override
	public void testShim() {
		_testShim = true;
	}

	@Override
	public @NotNull tripleo.wrap.File toFile() {
		return tripleo.wrap.File.wrap(getPath().toFile());
	}

	@Override
	public void hook(final int code, final String message) {
		logProgress(code, message);
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

	public CP_Path paths_outputRoot() {
		return c.paths().outputRoot();
	}
}
