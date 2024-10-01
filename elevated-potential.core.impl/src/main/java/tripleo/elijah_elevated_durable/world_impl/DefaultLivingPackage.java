package tripleo.elijah_elevated_durable.world_impl;

import tripleo.elijah.lang.i.OS_Package;
import tripleo.elijah_elevateder.world.i.LivingPackage;

public class DefaultLivingPackage implements LivingPackage {
	private final OS_Package _element;

	public DefaultLivingPackage(final OS_Package aElement) {
		_element = aElement;
	}

	@Override
	public int getCode() {
		return 0;
	}

	@Override
	public OS_Package getElement() {
		return _element;
	}
}
