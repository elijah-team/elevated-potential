package tripleo.elijah.comp;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.NotNull;
import tripleo.elijah.UnintendedUseException;
import tripleo.elijah.ci.CompilerInstructions;
import tripleo.elijah.util.Ok;
import tripleo.elijah.util.Operation;

import java.util.ArrayList;
import java.util.List;

public class CompilerInstructionsObserver implements Observer<CompilerInstructions> {
	private final Compilation                compilation;
	private final List<CompilerInstructions> l = new ArrayList<>();

	public CompilerInstructionsObserver(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	public @NotNull Operation<Ok> almostComplete() {
		return compilation.hasInstructions(l);
	}

	@Override
	public void onSubscribe(@NonNull final Disposable d) {
		//Disposable x = d;
		//NotImplementedException.raise();
	}

	@Override
	public void onError(@NonNull final Throwable e) {
		throw new UnintendedUseException();
	}

	@Override
	public void onNext(@NonNull final CompilerInstructions aCompilerInstructions) {
		l.add(aCompilerInstructions);
	}

	@Override
	public void onComplete() {
		throw new UnintendedUseException();
	}
}
