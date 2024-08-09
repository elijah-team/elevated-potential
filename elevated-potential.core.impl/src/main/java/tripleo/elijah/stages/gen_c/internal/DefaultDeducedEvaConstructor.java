package tripleo.elijah.stages.gen_c.internal;

import lombok.experimental.*;
import tripleo.elijah.*;
import tripleo.elijah.stages.gen_c.*;
import tripleo.elijah.stages.gen_fn.*;

public class DefaultDeducedEvaConstructor implements DeducedEvaConstructor {
	@Delegate
	private final IEvaConstructor carrier;
	private       int             _code;

	public DefaultDeducedEvaConstructor(EvaConstructor aGf) {
        carrier = aGf;
    }

	@Override
	public IEvaConstructor.BaseEvaConstructor_Reactive reactive() {
		throw new UnintendedUseException("implement me");
	}

	@Override
	public IEvaConstructor getCarrier() {
		return this.carrier;
    }

	@Override
	public int getCode() {
		return _code;
	}

	@Override
	public Object garish() {
		throw new UnintendedUseException("implement me");
    }
}
