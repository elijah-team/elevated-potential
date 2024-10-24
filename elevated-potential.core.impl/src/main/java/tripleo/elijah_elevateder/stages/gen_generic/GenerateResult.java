package tripleo.elijah_elevateder.stages.gen_generic;

import io.reactivex.rxjava3.core.Observer;
import tripleo.elijah.ci.LibraryStatementPart;
import tripleo.elijah.g.GGenerateResult;
import tripleo.elijah_elevateder.stages.gen_c.OutputFileC;

import tripleo.elijah_elevateder.stages.gen_fn.*;
import tripleo.util.buffer.Buffer;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface GenerateResult extends GGenerateResult {

	public enum TY {
		HEADER, IMPL, PRIVATE_HEADER
	}

	void add(Buffer b, EvaNode n, TY ty, LibraryStatementPart aLsp, Dependency d);

	void addClass(TY ty, EvaClass aClass, Buffer aBuf, LibraryStatementPart aLsp);

	void addConstructor(EvaConstructor aEvaConstructor, Buffer aBuffer, TY aTY, LibraryStatementPart aLsp);

	void addFunction(BaseEvaFunction aGeneratedFunction, Buffer aBuffer, TY aTY, LibraryStatementPart aLsp);

	void additional(GenerateResult aGenerateResult);

	void addNamespace(TY ty, EvaNamespace aNamespace, Buffer aBuf, LibraryStatementPart aLsp);

	void addWatcher(IGenerateResultWatcher w);

	void close();

	void completeItem(GenerateResultItem aGenerateResultItem);

	void observe(Observer<GenerateResultItem> obs);

	void outputFiles(Consumer<Map<String, OutputFileC>> cmso);

	List<Old_GenerateResultItem> results();

	void signalDone();

	void signalDone(Map<String, OutputFileC> aOutputFiles);

	void subscribeCompletedItems(Observer<GenerateResultItem> aGenerateResultItemObserver);
}
