package tripleo.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.*;

import java.util.*;

public class setup_GenType_Action_Arena {
	private final Map<String, Object> arenaVars = new HashMap<>();

	public void clear() {
		arenaVars.clear();
	}

	public <T> @Nullable T get(String a) {
		if (arenaVars.containsKey(a)) {
			return (T) arenaVars.get(a);
		}
		return null;
	}

	public <T> void put(String k, T v) {
		arenaVars.put(k, v);
	}
}
