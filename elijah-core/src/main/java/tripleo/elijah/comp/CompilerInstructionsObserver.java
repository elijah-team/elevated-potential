package tripleo.elijah.comp;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.util.*;

import java.util.*;

public class CompilerInstructionsObserver implements Observer<CompilerInstructions> {
	private final Compilation compilation;
	private final List<CompilerInstructions> l = new ArrayList<>();

	public CompilerInstructionsObserver(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	public @NotNull Operation<Ok> almostComplete() {
		return compilation.hasInstructions(l, compilation.pa());
	}

	@Override
	public void onComplete() {
		throw new UnintendedUseException();
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
	public void onSubscribe(@NonNull final Disposable d) {
		// Disposable x = d;
		// NotImplementedException.raise();
	}
}
