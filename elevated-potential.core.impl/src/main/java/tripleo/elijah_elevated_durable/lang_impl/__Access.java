package tripleo.elijah_elevated_durable.lang_impl;

import tripleo.elijah.lang.i.*;

public abstract class __Access implements ClassItem {
	private AccessNotation _an;

	@Override
	public AccessNotation getAccess() {
		return _an;
	}

	@Override
	public void serializeTo(final SmallWriter sw) {

	}

	@Override
	public void setAccess(AccessNotation an) {
		_an = an;
	}
}
