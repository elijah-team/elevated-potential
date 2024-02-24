package tripleo.elijah.comp;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.i.extra.IPipelineAccess;
import tripleo.elijah.util.UnintendedUseException;
import tripleo.elijah.util2.Eventual;

import java.util.*;

public class CompilerInstructionsObserver implements Observer<CompilerInstructions>, ICompilerInstructionsObserver {
	private final Compilation               compilation;
	private final List<CompilerInstructions> l = new ArrayList<>();

	public CompilerInstructionsObserver(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public void almostComplete() {
		final Eventual<IPipelineAccess> pipelineAccessPromise = compilation.getCompilationEnclosure().getPipelineAccessPromise();
		pipelineAccessPromise.register(compilation.getFluffy());

		pipelineAccessPromise.then(pa0 -> {
			compilation.hasInstructions(l, pa0);
		});
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

	public Observer<CompilerInstructions> getObserver() {
		return this;
	}
}
