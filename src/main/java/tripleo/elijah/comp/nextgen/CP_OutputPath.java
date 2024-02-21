package tripleo.elijah.comp.nextgen;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.IO;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.i.CP_SubFile;
import tripleo.elijah.comp.nextgen.i._CP_RootPath;
import tripleo.elijah.comp.process.CPX_CalculateFinishParse;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;
import tripleo.elijah.util.io.DisposableCharSink;
import tripleo.elijah.util2.Eventual;
import tripleo.elijah_elevated.gu.R;
import tripleo.wrap.File;

import java.nio.file.Path;
import java.util.List;

public interface CP_OutputPath extends /*CP_Path,*/ _CP_RootPath, CPX_CalculateFinishParse, R.R_Hook {
	void calculate_hda();

	// TODO 12/28 latch or Uni? or promise/Eventual: nodes (??)
	void _renderNodes(@NotNull List<ER_Node> nodes);

	@Override
	CP_Path child(String aSubPath);

	@Override
	@Nullable String getName();

	@Override
	@Nullable CP_Path getParent();

	@Override
	@NotNull Path getPath();

	@Override
	@NotNull Eventual<Path> getPathPromise();

	@Override
	@NotNull File getRootFile();

	@Override
	@NotNull _CP_RootPath getRootPath();

	void logProgress(int code, String message);

	@NotNull Operation<Ok> renderNode(@NotNull ER_Node node);

	void signalCalculateFinishParse();

	@Override
	@NotNull CP_SubFile subFile(String aFile);

	void testShim();

	@Override
	@NotNull File toFile();

	@Override
	void hook(int code, String message);

	@Override
	Path toPath();

	@Override
	DisposableCharSink newIOWriter(IO io);

	@Override
	String asString();

	@Override
	boolean samePath(Path px);

	@Override
	void notify_CPX_CalculateFinishParse(Ok ok);
}
