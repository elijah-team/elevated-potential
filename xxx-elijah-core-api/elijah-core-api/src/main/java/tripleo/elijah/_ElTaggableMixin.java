package tripleo.elijah;

import java.util.HashMap;
import java.util.Map;

public abstract class _ElTaggableMixin implements _ElTaggable {
	private final Map<Object, Object> exts = new HashMap<>();

	@Override
	public Object getExt(Class<?> aClass) {
		if (exts.containsKey(aClass)) {
			return exts.get(aClass);
		}
		return null;
	}

	@Override
	public void putExt(Class<?> aClass, Object o) {
		exts.put(aClass, o);
	}
}
