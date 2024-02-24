package tripleo.elijah.comp.nextgen;

import io.smallrye.mutiny.Uni;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.comp.Compilation;
import tripleo.elijah.comp.nextgen.i.CP_Path;
import tripleo.elijah.comp.nextgen.i.CP_Paths;
import tripleo.elijah.comp.nextgen.i.CP_RootType;
import tripleo.elijah.comp.nextgen.i.CP_StdlibPath;
import tripleo.elijah.comp.process.CPX_CalculateFinishParse;
import tripleo.elijah.nextgen.ER_Node;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.UnintendedUseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class CP_PathsImpl implements CP_Paths {
	private final Compilation _c;
	private final @NotNull List<ER_Node> outputNodes = new ArrayList<>();
	Uni<Ok> __CPX_CalculateFinishParse = Uni.createFrom().publisher(__CPX_CalculateFinishParse__publisher());//.item(Ok.instance());

	public CP_PathsImpl(final Compilation aC) {
		_c         = aC;
		outputRoot = new CP_OutputPathImpl(_c);
		stdlibRoot = new CP_StdlibPathImpl(_c);
	}

	@Override
	public void addNode(CP_RootType t, final ER_Node aNode) {
//		if (aNode.getPath().)
		if (t == CP_RootType.OUTPUT) {
			outputNodes.add(aNode);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void renderNodes() {
		outputRoot._renderNodes(outputNodes);
	}

	@Override
	public void signalCalculateFinishParse() {
		outputRoot.signalCalculateFinishParse();
	}

	// region paths

	private final @NotNull CP_StdlibPath stdlibRoot;
	private final @NotNull CP_OutputPath outputRoot;

	@Override
	public @NotNull CP_StdlibPath stdlibRoot() {
		return stdlibRoot;
	}

	@Override
	public CP_OutputPath outputRoot() {
		return outputRoot;
	}

	@Override
	public CP_Path preludeRoot() {
		throw new UnintendedUseException("TODO 12/07 implement me");
	}

	@Override
	public CP_Path sourcesRoot() {
		throw new UnintendedUseException("TODO 12/07 implement me");
	}

	// endregion paths

	@Override
	public void subscribeCalculateFinishParse(CPX_CalculateFinishParse cp_OutputPath) {
		__CPX_CalculateFinishParse
				.onItem()
				.invoke((x) -> {
					cp_OutputPath.notify_CPX_CalculateFinishParse(x);
				});
	}

	private Publisher<Ok> __CPX_CalculateFinishParse__publisher() {
		return new Publisher<Ok>() {
			@Override
			public void subscribe(Subscriber<? super Ok> subscriber) {
				throw new UnintendedUseException("TODO 12/28 dpys, implement me");
			}
		};
	}
}
