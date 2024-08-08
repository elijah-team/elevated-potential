package tripleo.elijah.stages.gen_generic;

import io.reactivex.rxjava3.core.Observer;
import lombok.experimental.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.util.buffer.*;

import java.util.*;
import java.util.function.*;

public class New_GenerateResultItem implements GenerateResultItem {
	@Delegate
	private final Old_GenerateResult ogri;

	public New_GenerateResultItem() {
		ogri = new Old_GenerateResult();
	}

	public void add(final @NotNull Buffer b, final @NotNull EvaNode n, final GenerateResult.@NotNull TY ty, final @Nullable LibraryStatementPart aLsp, @NotNull final Dependency d) {
		ogri.add(b, n, ty, aLsp, d);
	}

	public void addClass(final GenerateResult.@NotNull TY ty,
						 final @NotNull EvaClass aEvaClass,
						 final @NotNull Buffer aBuf,
						 final LibraryStatementPart aLsp) {
		assert aEvaClass inms
		ogri.addClass(ty, aEvaClass, aBuf, aLsp);
	}

	public void addConstructor(final @NotNull EvaConstructor aEvaConstructor,
							   final @NotNull Buffer aBuffer,
							   final GenerateResult.@NotNull TY aTY,
							   final LibraryStatementPart aLsp) {
		assert aEvaConstructor instanceof DeducedEvaConstructor;
		ogri.addConstructor(aEvaConstructor, aBuffer, aTY, aLsp);
	}

	public void addFunction(final @NotNull BaseEvaFunction aGeneratedFunction,
							final @NotNull Buffer aBuffer,
							final GenerateResult.@NotNull TY aTY,
							final LibraryStatementPart aLsp) {
		ogri.addFunction(aGeneratedFunction, aBuffer, aTY, aLsp);
	}

	public void additional(final @NotNull GenerateResult aGenerateResult) {
		ogri.additional(aGenerateResult);
	}

	public void addNamespace(final GenerateResult.@NotNull TY ty,
							 final @NotNull EvaNamespace aNamespace,
							 final @NotNull Buffer aBuf,
							 final LibraryStatementPart aLsp) {
		assert aNamespace instanceof DeducedEvaNamespace;
		ogri.addNamespace(ty, aNamespace, aBuf, aLsp);
	}

	public void addWatcher(final IGenerateResultWatcher w) {
		ogri.addWatcher(w);
	}

	public void close() {
		ogri.close();
	}

	public void completeItem(final GenerateResultItem aGenerateResultItem) {
		ogri.completeItem(aGenerateResultItem);
	}

	public void observe(final @NotNull Observer<GenerateResultItem> obs) {
		ogri.observe(obs);
	}

	public void outputFiles(final @NotNull Consumer<Map<String, OutputFileC>> cmso) {
		ogri.outputFiles(cmso);
	}

	public @NotNull List<Old_GenerateResultItem> results() {
		return ogri.results();
	}

	public void signalDone() {
		ogri.signalDone();
	}

	public void signalDone(final Map<String, OutputFileC> aOutputFiles) {
		ogri.signalDone(aOutputFiles);
	}

	public void subscribeCompletedItems(final @NotNull Observer<GenerateResultItem> aGenerateResultItemObserver) {
		ogri.subscribeCompletedItems(aGenerateResultItemObserver);
	}
}
