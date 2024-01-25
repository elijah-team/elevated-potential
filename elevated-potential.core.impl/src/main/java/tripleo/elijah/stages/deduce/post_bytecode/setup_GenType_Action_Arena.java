package tripleo.elijah.stages.deduce.post_bytecode;

import org.jetbrains.annotations.*;
import tripleo.elijah.stages.deduce.DeduceTypes2;

import java.util.*;
import java.util.function.Supplier;

public class setup_GenType_Action_Arena {
	private final Map<String, Object> arenaVars = new HashMap<>();
	private final DeduceTypes2 deduceTypes2;

	public setup_GenType_Action_Arena(final DeduceTypes2 aDeduceTypes2) {
		deduceTypes2 = aDeduceTypes2;
	}

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

	public DeduceTypes2 getDeduceTypes2() {
		return this.deduceTypes2;
	}
}
