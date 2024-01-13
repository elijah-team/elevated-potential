package tripleo.elijah.comp.nextgen;

import io.smallrye.mutiny.Uni;

import org.jetbrains.annotations.*;

import tripleo.elijah.comp.process.CPX_CalculateFinishParse;
import tripleo.elijah.util2.UnintendedUseException;
import tripleo.elijah.comp.*;
import tripleo.elijah.comp.nextgen.i.*;
import tripleo.elijah.nextgen.*;
import tripleo.elijah.util.Ok;

import java.util.*;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class CP_Paths__ implements CP_Paths {
	private final          Compilation  _c;
	private final @NotNull CP_StdlibPath     stdlibRoot;
	private                CP_OutputPathImpl outputRoot;
	private @NotNull       List<ER_Node>     outputNodes = new ArrayList<>();

	public CP_Paths__(final Compilation aC) {
		_c = aC;
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
	public CP_Path outputRoot() {
		return outputRoot;
	}

	@Override
	public CP_Path preludeRoot() {
		throw new UnintendedUseException("TODO 12/07 implement me");
	}

	@Override
	public void renderNodes() {
		outputRoot._renderNodes(outputNodes);
	}

	@Override
	public void signalCalculateFinishParse() {
		outputRoot.signalCalculateFinishParse();
	}

	@Override
	public @NotNull CP_StdlibPath stdlibRoot() {
		return stdlibRoot;
	}

	@Override
	public CP_Path sourcesRoot() {
		throw new UnintendedUseException("TODO 12/07 implement me");
	}

	@Override
	public void subscribeCalculateFinishParse(CPX_CalculateFinishParse cp_OutputPath) {
		__CPX_CalculateFinishParse
			.onItem()
			.invoke((x)->{
				cp_OutputPath.notify_CPX_CalculateFinishParse(x);
			});
	}
	
	Uni<Ok> __CPX_CalculateFinishParse = Uni.createFrom().publisher(__CPX_CalculateFinishParse__publisher());//.item(Ok.instance());

	private Publisher<Ok> __CPX_CalculateFinishParse__publisher() {
		return new Publisher<Ok>() {
			@Override
			public void subscribe(Subscriber<? super Ok> subscriber) {
				throw new UnintendedUseException("TODO 12/28 dpys, implement me");
			}
		};
	}
}
