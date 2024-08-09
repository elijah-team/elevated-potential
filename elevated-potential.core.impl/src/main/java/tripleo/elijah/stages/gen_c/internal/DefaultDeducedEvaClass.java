package tripleo.elijah.stages.gen_c.internal;

import tripleo.elijah.lang.i.*;
import tripleo.elijah.stages.gen_c.*;

public class DefaultDeducedEvaClass implements DeducedEvaClass {
	private final WhyNotGarish_Class _garish;
	private       int                _code;

	/**
	 * Need a big ass relation table/annotation
	 */
	public DefaultDeducedEvaClass(final WhyNotGarish_Class aGarishClass) {
		this._garish = aGarishClass;
	}

	/**
	 * Something about FileGen
	 */
	@Override
	public void setCode(final int aCode) {
		_code = aCode;
	}

	@Override
	public int getCode() {
		return this._code;
	}

	@Override
	public Object garish() {
		return this._garish;
	}

	@Override
	public String identityString() {
		return _garish.__evaClass().identityString();
	}

	@Override
	public OS_Module module() {
		return _garish.__evaClass().module();
	}
}
