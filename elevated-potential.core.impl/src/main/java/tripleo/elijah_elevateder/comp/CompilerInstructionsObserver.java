package tripleo.elijah_elevateder.comp;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.jetbrains.annotations.*;
import tripleo.elijah.ci.*;
import tripleo.elijah.comp.ICompilerInstructionsObserver;
import tripleo.elijah_elevateder.comp.i.Compilation;
import tripleo.elijah_elevateder.comp.i.extra.IPipelineAccess;
import tripleo.elijah_fluffy.util.*;

import java.util.*;

public class CompilerInstructionsObserver implements Observer<CompilerInstructions>, ICompilerInstructionsObserver {
	private final Compilation                compilation;
	private final List<CompilerInstructions> l = new ArrayList<>();

	public CompilerInstructionsObserver(final Compilation aCompilation) {
		compilation = aCompilation;
	}

	@Override
	public @NotNull Operation<Ok> almostComplete() {
		final Eventual<IPipelineAccess> pipelineAccessPromise = compilation.getCompilationEnclosure().getPipelineAccessPromise();
		pipelineAccessPromise.register(compilation.getFluffy());

		pipelineAccessPromise.then(pa0 -> {
			compilation.hasInstructions(l, pa0);
		});

		// NOTE 11/26 this ok is "void" b/c we are using promise
		return Operation.success(Ok.instance());
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
