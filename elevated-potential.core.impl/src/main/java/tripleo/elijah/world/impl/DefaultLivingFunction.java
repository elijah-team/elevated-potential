package tripleo.elijah.world.impl;

import org.jdeferred2.*;
import org.jetbrains.annotations.*;
import tripleo.elijah.*;
import tripleo.elijah.comp.*;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;
import tripleo.elijah.world.i.*;

public class DefaultLivingFunction implements LivingFunction {
	private final           FunctionDef       _element;
	private final @Nullable BaseEvaFunction   _gf;
	private                 Eventual<Integer> codeCallback;
	private                 boolean           __registered;

	public DefaultLivingFunction(final @NotNull BaseEvaFunction aFunction) {
		_element = aFunction.getFD();
		_gf      = aFunction;
	}

	@Override
	public int getCode() {
		return _gf.getCode();
	}

	@Override
	public FunctionDef getElement() {
		return _element;
	}

	@Override
	public BaseEvaFunction evaNode() {
		return _gf;
	}

	@Override
	public void codeRegistration(final LF_CodeRegistration acr) {
		if (codeCallback == null) {
			// 1. allocate
			codeCallback = new Eventual<>();

			// 2. initialize
			final Compilation compilation = (Compilation) _element.getContext().module().getCompilation();
			codeCallback.register(compilation.getFluffy());

			// 3. setup
			codeCallback.then(i -> {
				final IEvaFunctionBase evaFunction = evaNode();
				evaFunction.setCode(i);
				__registered = true;
			});
		}

		// 4. trigger
		ESwitch.flep(evaNode(), new DoneCallback<DeducedBaseEvaFunction>() {
			@Override
			public void onDone(final DeducedBaseEvaFunction aDeducedBaseEvaFunction) {
				if (aDeducedBaseEvaFunction.getCode() != 0) {
					//	return;
					throw new AssertionError("debug");
				}

				final EvaFunction evaFunction = (EvaFunction) evaNode();
				acr.accept(evaFunction, codeCallback);
			}
		});
	}

	@Override
	public boolean isRegistered() {
		return __registered;
	}

	@Override
	public void listenRegister(final DoneCallback<Integer> aCodeCallback) {
		codeCallback.then(aCodeCallback);
	}

	@Override
	public void waitDeduced(final DoneCallback<DeducedBaseEvaFunction> cb) {
		ESwitch.flep(evaNode(), cb::onDone);
	}
}
